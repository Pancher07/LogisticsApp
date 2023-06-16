package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.LoadingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadingOrderRepository extends JpaRepository<LoadingOrder, Long> {
}
