package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.ManagerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerOrderRepository extends JpaRepository<ManagerOrder, Long> {
    List<ManagerOrder> findByManagerId(long managerId);
}
