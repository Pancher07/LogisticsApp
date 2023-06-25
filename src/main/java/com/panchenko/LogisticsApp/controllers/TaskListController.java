package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TaskListDTO;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.service.LogisticianService;
import com.panchenko.LogisticsApp.service.TaskListService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
        List<TaskListDTO> taskListDTOList = taskListService.getAll().stream()
                .map(taskListService::convertToTaskListDTO).toList();

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list");
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TaskListDTO taskListDTO = taskListService.convertToTaskListDTO(taskListService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        TaskList taskList = taskListService.create(taskListService.convertToTaskList(taskListDTO));

        TaskListDTO taskListDTOResponse = taskListService.convertToTaskListDTO(taskList);

        Map<String, Object> map = new HashMap<>();

        map.put("header", "URL: /api/task-list");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", taskListDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        TaskList updatedTaskList = taskListService.readById(id);

        taskListService.update(updatedTaskList, taskListDTO);

        TaskListDTO taskListDTOResponse = taskListService.convertToTaskListDTO(updatedTaskList);

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/task-list/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", taskListDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        taskListService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
