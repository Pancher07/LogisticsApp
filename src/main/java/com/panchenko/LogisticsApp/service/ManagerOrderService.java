package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.ManagerOrderDTO;
import com.panchenko.LogisticsApp.dto.SelectNextDTO;
import com.panchenko.LogisticsApp.model.Hitch;
import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;

import java.util.List;
import java.util.Optional;

public interface ManagerOrderService {
    ManagerOrder create(ManagerOrder managerOrder, long managerId);

    ManagerOrder readById(long id);

    ManagerOrder update(ManagerOrder managerOrder, ManagerOrderDTO managerOrderDTO);

    void delete(long id);

    List<ManagerOrder> getAll();

    List<ManagerOrder> getByManagerId(long managerId);

    List<ManagerOrder> getByManager(Manager manager);

    ManagerOrder convertToManagerOrder(ManagerOrderDTO managerOrderDTO);

    ManagerOrderDTO convertToManagerOrderDTO(ManagerOrder managerOrder);

    Hitch selectHitch(ManagerOrder managerOrder);

    SelectNextDTO selectNextHitch(ManagerOrder managerOrder, List<Long> skippedHitchesId, Hitch skippedHitch);

    ManagerOrder approveHitch(ManagerOrder managerOrder, Hitch hitch);

    List<ManagerOrder> getManagerOrdersByStatus(TaskListAndOrderStatus status);
}
