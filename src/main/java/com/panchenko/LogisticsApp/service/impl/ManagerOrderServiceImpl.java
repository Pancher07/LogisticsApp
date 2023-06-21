package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.exception.ManagerOrderException.ManagerOrderNotFoundException;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.repository.ManagerOrderRepository;
import com.panchenko.LogisticsApp.service.ManagerOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManagerOrderServiceImpl implements ManagerOrderService {

    private final ManagerOrderRepository managerOrderRepository;
    private final ModelMapper modelMapper;

    public ManagerOrderServiceImpl(ManagerOrderRepository managerOrderRepository, ModelMapper modelMapper) {
        this.managerOrderRepository = managerOrderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ManagerOrder create(ManagerOrder managerOrder) {
        if (managerOrder == null) {
            throw new NullEntityReferenceException("Manager order cannot be 'null'");
        }
        enrichManagerOrder(managerOrder);
        return managerOrderRepository.save(managerOrder);
    }

    @Override
    public ManagerOrder readById(long id) {
        return managerOrderRepository.findById(id).orElseThrow(
                () -> new ManagerOrderNotFoundException("Manager order with id " + id + " not found"));
    }

    @Override
    public ManagerOrder update(ManagerOrder managerOrder) {
        if (managerOrder == null) {
            throw new NullEntityReferenceException("Manager order cannot be 'null'");
        }
        readById(managerOrder.getId());
        return managerOrderRepository.save(managerOrder);
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

    private void enrichManagerOrder(ManagerOrder managerOrder) {
        managerOrder.setCreatedAt(LocalDateTime.now());
    }
}
