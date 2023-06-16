package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
