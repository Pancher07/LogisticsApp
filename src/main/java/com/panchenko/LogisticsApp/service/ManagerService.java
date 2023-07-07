package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.ManagerDTO;
import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;

import java.util.List;

public interface ManagerService {
    Manager readById(long id);

    List<Manager> getAll();

    Manager convertToManager(ManagerDTO managerDTO);

    ManagerDTO convertToManagerDTO(Manager manager);
}
