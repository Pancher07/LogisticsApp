package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TaskListDTO;
import com.panchenko.LogisticsApp.dto.TaskListResponse;
import com.panchenko.LogisticsApp.exception.TaskListExceptions.TaskListNotCreatedException;
import com.panchenko.LogisticsApp.exception.TaskListExceptions.TaskListNotUpdatedException;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.service.TaskListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task-lists")
public class TaskListController {
    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<TaskListResponse> taskListResponseList = taskListService.getAll().stream()
                .map(TaskListResponse::new).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list");
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListResponseList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TaskListResponse taskListResponse = new TaskListResponse(taskListService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListResponse);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new TaskListNotCreatedException(errorMessage.toString());
        }
        TaskList taskList = taskListService.create(taskListService.convertToTaskList(taskListDTO));

        TaskListResponse taskListResponse = new TaskListResponse(taskList);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", taskListResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new TaskListNotUpdatedException(errorMessage.toString());
        }
        TaskList updatedTaskList = taskListService.readById(id);

        if (taskListDTO.getStatus() != null) {
            updatedTaskList.setStatus(taskListDTO.getStatus());
        }
        if (taskListDTO.getLogistician() != null) {
            updatedTaskList.setLogistician(taskListDTO.getLogistician());
        }

        taskListService.update(updatedTaskList);

        TaskListResponse taskListResponse = new TaskListResponse(updatedTaskList);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        taskListService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
