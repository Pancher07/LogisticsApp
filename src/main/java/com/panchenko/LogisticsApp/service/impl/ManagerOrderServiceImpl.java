package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.model.enumeration.PresenceOfPumpOrCalibration;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;
import com.panchenko.LogisticsApp.model.enumeration.VehicleStatus;
import com.panchenko.LogisticsApp.repository.ManagerOrderRepository;
import com.panchenko.LogisticsApp.service.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManagerOrderServiceImpl implements ManagerOrderService {

    private final ManagerOrderRepository managerOrderRepository;
    private final ModelMapper modelMapper;
    private final ManagerService managerService;
    private final ContractorService contractorService;
    private final TaskListService taskListService;
    private final HitchService hitchService;

    public ManagerOrderServiceImpl(ManagerOrderRepository managerOrderRepository, ModelMapper modelMapper,
                                   ManagerService managerService, ContractorService contractorService,
                                   TaskListService taskListService, HitchService hitchService) {
        this.managerOrderRepository = managerOrderRepository;
        this.modelMapper = modelMapper;
        this.managerService = managerService;
        this.contractorService = contractorService;
        this.taskListService = taskListService;
        this.hitchService = hitchService;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    @Override
    public ManagerOrder create(ManagerOrder managerOrder, long managerId) {
        if (managerOrder == null) {
            throw new NullEntityReferenceException("Manager order cannot be 'null'");
        }
        enrichManagerOrder(managerOrder, managerId);
        return managerOrderRepository.save(managerOrder);
    }

    private void enrichManagerOrder(ManagerOrder managerOrder, long managerId) {
        managerOrder.setManager(managerService.readById(managerId));
        managerOrder.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public ManagerOrder readById(long id) {
        return managerOrderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Manager order with id " + id + " not found"));
    }

    @Override
    public ManagerOrder update(ManagerOrder updatedManagerOrder, ManagerOrderDTO managerOrderDTO) {
        if (updatedManagerOrder == null) {
            throw new NullEntityReferenceException("Manager order cannot be 'null'");
        }
        if (managerOrderDTO.getTypeOfLightProduct() != null) {
            updatedManagerOrder.setTypeOfLightProduct(managerOrderDTO.getTypeOfLightProduct());
        }
        if (managerOrderDTO.getVolume() != 0) {
            updatedManagerOrder.setVolume(managerOrderDTO.getVolume());
        }
        if (managerOrderDTO.getPump() != null) {
            updatedManagerOrder.setPump(managerOrderDTO.getPump());
        }
        if (managerOrderDTO.getCalibration() != null) {
            updatedManagerOrder.setCalibration(managerOrderDTO.getCalibration());
        }
        if (managerOrderDTO.getContact() != null) {
            updatedManagerOrder.setContact(managerOrderDTO.getContact());
        }
        if (managerOrderDTO.getUploadingDateTime() != null) {
            updatedManagerOrder.setUploadingDateTime(managerOrderDTO.getUploadingDateTime());
        }
        if (managerOrderDTO.getOrderStatus() != null) {
            updatedManagerOrder.setOrderStatus(managerOrderDTO.getOrderStatus());
        }
        if (managerOrderDTO.getContractorId() != null) {
            updatedManagerOrder.setContractor(contractorService.readById(managerOrderDTO.getContractorId()));
        }
        if (managerOrderDTO.getTaskListId() != null) {
            updatedManagerOrder.setTaskList(taskListService.readById(managerOrderDTO.getTaskListId()));
        }
        if (managerOrderDTO.getHitchId() != null) {
            updatedManagerOrder.setHitch(hitchService.readById(managerOrderDTO.getHitchId()));
        }
        return managerOrderRepository.save(updatedManagerOrder);
    }

    @Override
    public void delete(long id) {
        ManagerOrder managerOrder = readById(id);
        managerOrderRepository.delete(managerOrder);
    }

    @Override
    public List<ManagerOrder> getAll() {
        return managerOrderRepository.findAll();
    }

    @Override
    public List<ManagerOrder> getByManagerId(long managerId) {
        return managerOrderRepository.findByManagerId(managerId);
    }

    @Override
    public List<ManagerOrder> getByManager(Manager manager) {
        return managerOrderRepository.findByManager(manager);
    }

    @Override
    public List<ManagerOrder> getManagerOrdersByStatus(TaskListAndOrderStatus status) {
        return managerOrderRepository.findByOrderStatus(status);
    }

    @Override
    public ManagerOrder convertToManagerOrder(ManagerOrderDTO managerOrderDTO) {
        return modelMapper.map(managerOrderDTO, ManagerOrder.class);
    }

    @Override
    public ManagerOrderDTO convertToManagerOrderDTO(ManagerOrder managerOrder) {
        return modelMapper.map(managerOrder, ManagerOrderDTO.class);
    }

    @Override
    public Hitch selectHitch(ManagerOrder managerOrder/*, List<Hitch> skippedHitch*/) {
        List<Hitch> loadedHitches =
                switch (managerOrder.getTypeOfLightProduct()) {
                    case DIESEL_FUEL -> hitchService.getAllByVehicleStatus(VehicleStatus.LOADED_DIESEL);
                    case A95 -> hitchService.getAllByVehicleStatus(VehicleStatus.LOADED_A95);
                    case A92 -> hitchService.getAllByVehicleStatus(VehicleStatus.LOADED_A92);
                };
        if (loadedHitches.isEmpty()) {
            throw new NullEntityReferenceException("The program cannot select a hitch because there is no hitch that " +
                    "matches the selection criteria");
        }
        List<Hitch> hitches = loadedHitches.stream()
                .filter(hitch -> managerOrder.getVolume() == hitch.getTrailer().getVolume())
                .filter(hitch -> managerOrder.getCalibration().equals(PresenceOfPumpOrCalibration.YES) ?
                        managerOrder.getCalibration() == hitch.getTrailer().getCalibration() :
                        managerOrder.getCalibration() == hitch.getTrailer().getCalibration() ||
                                managerOrder.getCalibration() != hitch.getTrailer().getCalibration())
                .toList();

        if (hitches.isEmpty()) {
            throw new NullEntityReferenceException("The program cannot select a hitch because there is no hitch that " +
                    "matches the selection criteria");
        }
        List<Hitch> resultList = hitches.stream().sorted((hitch1, hitch2) -> hitch1.getDriver().getLastTimeWorked()
                        .compareTo(hitch2.getDriver().getLastTimeWorked()))
                .toList();

        return resultList.get(0);
    }

    @Override
    public ManagerOrder approveHitch(Long managerOrderId, Hitch hitch) {
        ManagerOrder managerOrder = readById(managerOrderId);
        if (managerOrder == null || hitch == null) {
            throw new NullEntityReferenceException("managerOrderId or hitch cannot be null");
        }
        if (managerOrder.getHitch() == hitch) {
            return managerOrder;
        }
        if (managerOrder.getHitch() != null) {
            managerOrder.getHitch().setVehicleStatus(
                    switch (hitch.getLoadedWithProduct()) {
                        case DIESEL_FUEL -> VehicleStatus.LOADED_DIESEL;
                        case A95 -> VehicleStatus.LOADED_A95;
                        case A92 -> VehicleStatus.LOADED_A92;
                    }
            );
            taskListService.delete(managerOrder.getTaskList().getId());
            managerOrder.setOrderStatus(TaskListAndOrderStatus.OPENED);
        }

        managerOrder.setHitch(hitch);
        managerOrder.setOrderStatus(TaskListAndOrderStatus.IN_WORK);
        hitch.setVehicleStatus(VehicleStatus.UNLOADING);

        TaskList taskList = new TaskList();
        taskList.setCreatedAt(LocalDateTime.now());
        taskList.setStatus(TaskListAndOrderStatus.IN_WORK);
        taskList.setLogistician(hitch.getLogistician());
        taskList.setManagerOrder(managerOrder);
        taskListService.create(taskList);
        managerOrder.setTaskList(taskList);

        managerOrderRepository.save(managerOrder);

        return managerOrder;
    }
}
