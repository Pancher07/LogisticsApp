package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Hitch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitchRepository extends JpaRepository<Hitch, Long> {
}
