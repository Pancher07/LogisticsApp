package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
