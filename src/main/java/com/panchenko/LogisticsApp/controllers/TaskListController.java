package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.TaskListDTO;
import com.panchenko.LogisticsApp.model.TaskList;
import com.panchenko.LogisticsApp.service.TaskListService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskListController {
    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<TaskListDTO> taskListDTOList = taskListService.getAll().stream()
                .map(taskListService::convertToTaskListDTO).toList();
        return ResponseEntity.ok(taskListDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        TaskListDTO taskListDTO = taskListService.convertToTaskListDTO(taskListService.readById(id));
        return ResponseEntity.ok(taskListDTO);
    }

    @GetMapping("/logist/{id}")
    public ResponseEntity<?> getByLogistician(@PathVariable long id) {
        TaskListDTO taskListDTO = taskListService.convertToTaskListDTO(taskListService.readById(id));
        return ResponseEntity.ok(taskListDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        TaskList taskList = taskListService.create(taskListService.convertToTaskList(taskListDTO));
        TaskListDTO taskListDTOResponse = taskListService.convertToTaskListDTO(taskList);
        return ResponseEntity.ok(taskListDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid TaskListDTO taskListDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        TaskList updatedTaskList = taskListService.readById(id);
        taskListService.update(updatedTaskList, taskListDTO);
        TaskListDTO taskListDTOResponse = taskListService.convertToTaskListDTO(updatedTaskList);
        return ResponseEntity.ok(taskListDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        taskListService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
