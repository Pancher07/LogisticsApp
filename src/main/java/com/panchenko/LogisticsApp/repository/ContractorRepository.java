package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
}
