package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Manager;
import com.panchenko.LogisticsApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findById(long id);
}
