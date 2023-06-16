package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.DriverDTO;
import com.panchenko.LogisticsApp.exception.DriverException.DriverNotFoundException;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.Driver;
import com.panchenko.LogisticsApp.repository.DriverRepository;
import com.panchenko.LogisticsApp.service.DriverService;
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
                () -> new DriverNotFoundException("Driver with id " + id + " not found"));
    }

    @Override
    public Driver update(Driver driver) {
        if (driver == null) {
            throw new NullEntityReferenceException("Driver cannot be 'null'");
        }
        readById(driver.getId());
        return driverRepository.save(driver);
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
