package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Logistician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticianRepository extends JpaRepository<Logistician, Long> {
}
