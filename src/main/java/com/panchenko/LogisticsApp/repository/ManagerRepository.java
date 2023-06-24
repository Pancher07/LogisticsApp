package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
