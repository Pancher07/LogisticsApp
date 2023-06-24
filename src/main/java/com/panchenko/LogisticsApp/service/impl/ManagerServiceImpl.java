package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.repository.ManagerRepository;
import com.panchenko.LogisticsApp.service.ManagerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
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
}
