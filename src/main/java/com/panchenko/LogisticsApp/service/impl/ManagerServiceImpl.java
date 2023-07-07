package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ManagerDTO;
import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.repository.ManagerRepository;
import com.panchenko.LogisticsApp.service.ManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;

    public ManagerServiceImpl(ManagerRepository managerRepository, ModelMapper modelMapper) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Manager readById(long id) {
        return managerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Manager with id " + id + " not found"));
    }

    @Override
    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager convertToManager(ManagerDTO managerDTO) {
        return modelMapper.map(managerDTO, Manager.class);
    }

    @Override
    public ManagerDTO convertToManagerDTO(Manager manager) {
        return modelMapper.map(manager, ManagerDTO.class);
    }
}
