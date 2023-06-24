package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;

import java.util.List;

public interface ManagerService {
    Manager readById(long id);
    List<Manager> getAll();
}
