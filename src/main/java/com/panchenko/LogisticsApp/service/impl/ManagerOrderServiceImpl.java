package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.ManagerOrder;
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
    public ManagerOrder convertToManagerOrder(ManagerOrderDTO managerOrderDTO) {
        return modelMapper.map(managerOrderDTO, ManagerOrder.class);
    }

    @Override
    public ManagerOrderDTO convertToManagerOrderDTO(ManagerOrder managerOrder) {
        return modelMapper.map(managerOrder, ManagerOrderDTO.class);
    }
}
