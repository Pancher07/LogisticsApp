package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
