package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.DriverDTO;
import com.panchenko.LogisticsApp.dto.UserDTO;
import com.panchenko.LogisticsApp.model.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DriverService {
    Driver create(Driver driver);

    Driver readById(long id);

    Driver update(Driver driver, DriverDTO driverDTO);

    void delete(long id);

    List<Driver> getAll();

    Driver convertToDriver(DriverDTO driverDTO);

    DriverDTO convertToDriverDTO(Driver driver);
}
