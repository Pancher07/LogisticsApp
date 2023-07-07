package com.panchenko.LogisticsApp.repository;

import com.panchenko.LogisticsApp.model.Logistician;
import com.panchenko.LogisticsApp.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByLogistician(Logistician logistician);
}
