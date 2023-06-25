package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.DriverDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Driver;
import com.panchenko.LogisticsApp.repository.DriverRepository;
import com.panchenko.LogisticsApp.service.DriverService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;

    public DriverServiceImpl(DriverRepository driverRepository, ModelMapper modelMapper) {
        this.driverRepository = driverRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Driver create(Driver driver) {
        if (driver == null) {
            throw new NullEntityReferenceException("Driver cannot be 'null'");
        }
        return driverRepository.save(driver);
    }

    @Override
    public Driver readById(long id) {
        return driverRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Driver with id " + id + " not found"));
    }

    @Override
    public Driver update(Driver updatedDriver, DriverDTO driverDTO) {
        if (updatedDriver == null) {
            throw new NullEntityReferenceException("Driver cannot be 'null'");
        }
        if (driverDTO.getName() != null) {
            updatedDriver.setName(driverDTO.getName());
        }
        if (driverDTO.getMiddleName() != null) {
            updatedDriver.setMiddleName(driverDTO.getMiddleName());
        }
        if (driverDTO.getSurname() != null) {
            updatedDriver.setSurname(driverDTO.getSurname());
        }
        if (driverDTO.getPhone() != null) {
            updatedDriver.setPhone(driverDTO.getPhone());
        }
        if (driverDTO.getLastTimeWorked() != null) {
            updatedDriver.setLastTimeWorked(driverDTO.getLastTimeWorked());
        }
        return driverRepository.save(updatedDriver);
    }

    @Override
    public void delete(long id) {
        Driver driver = readById(id);
        driverRepository.delete(driver);
    }

    @Override
    public List<Driver> getAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver convertToDriver(DriverDTO driverDTO) {
        return modelMapper.map(driverDTO, Driver.class);
    }

    @Override
    public DriverDTO convertToDriverDTO(Driver driver) {
        return modelMapper.map(driver, DriverDTO.class);
    }
}
