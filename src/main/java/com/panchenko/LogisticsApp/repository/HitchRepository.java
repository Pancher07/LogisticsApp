package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitchRepository extends JpaRepository<User, Long> {
}
