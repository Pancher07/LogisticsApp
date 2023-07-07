package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.ManagerOrder;
import com.panchenko.LogisticsApp.model.enumeration.TaskListAndOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerOrderRepository extends JpaRepository<ManagerOrder, Long> {
    List<ManagerOrder> findByManager(Manager manager);
    List<ManagerOrder> findByManagerId(long managerId);

    List<ManagerOrder> findByOrderStatus(TaskListAndOrderStatus status);
}
