package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.model.ManagerOrder;

import java.util.List;

public interface ManagerOrderService {
    ManagerOrder create(ManagerOrder managerOrder);

    ManagerOrder readById(long id);

    ManagerOrder update(ManagerOrder managerOrder);

    void delete(long id);

    List<ManagerOrder> getAll();
    List<ManagerOrder> getByManagerId(long managerId);

    ManagerOrder convertToManagerOrder(ManagerOrderDTO managerOrderDTO);

    ManagerOrderDTO convertToManagerOrderDTO(ManagerOrder managerOrder);
}
