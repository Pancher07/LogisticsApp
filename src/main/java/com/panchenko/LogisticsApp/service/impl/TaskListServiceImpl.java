package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.TaskListDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.repository.TaskListRepository;
import com.panchenko.LogisticsApp.service.LogisticianService;
import com.panchenko.LogisticsApp.service.TaskListService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;
    private final ModelMapper modelMapper;
    private final LogisticianService logisticianService;

    public TaskListServiceImpl(TaskListRepository taskListRepository, ModelMapper modelMapper,
                               LogisticianService logisticianService) {
        this.taskListRepository = taskListRepository;
        this.modelMapper = modelMapper;
        this.logisticianService = logisticianService;
    }

    @Override
    public TaskList create(TaskList taskList) {
        if (taskList == null) {
            throw new NullEntityReferenceException("Task list cannot be 'null'");
        }
        enrichTaskList(taskList);
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList readById(long id) {
        return taskListRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Task list with id " + id + " not found"));
    }

    @Override
    public List<TaskList> getByLogisticianId(long id) {
        return taskListRepository.findByLogistician(logisticianService.readById(id));
    }

    @Override
    public TaskList update(TaskList updatedTaskList, TaskListDTO taskListDTO) {
        if (updatedTaskList == null) {
            throw new NullEntityReferenceException("Task list cannot be 'null'");
        }
        if (taskListDTO.getStatus() != null) {
            updatedTaskList.setStatus(taskListDTO.getStatus());
        }
        if (taskListDTO.getLogisticianId() != null) {
            updatedTaskList.setLogistician(logisticianService.readById(taskListDTO.getLogisticianId()));
        }
        return taskListRepository.save(updatedTaskList);
    }

    @Override
    public void delete(long id) {
        TaskList taskList = readById(id);
        taskListRepository.delete(taskList);
    }

    @Override
    public List<TaskList> getAll() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList convertToTaskList(TaskListDTO taskListDTO) {
        return modelMapper.map(taskListDTO, TaskList.class);
    }

    @Override
    public TaskListDTO convertToTaskListDTO(TaskList taskList) {
        return modelMapper.map(taskList, TaskListDTO.class);
    }

    private void enrichTaskList(TaskList taskList) {
        taskList.setCreatedAt(LocalDateTime.now());
    }
}
