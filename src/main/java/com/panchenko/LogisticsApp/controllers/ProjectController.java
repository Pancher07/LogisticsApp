package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ProjectDTO;
import com.panchenko.LogisticsApp.exception.ProjectException.ProjectNotCreatedException;
import com.panchenko.LogisticsApp.exception.ProjectException.ProjectNotUpdatedException;
import com.panchenko.LogisticsApp.model.Project;
import com.panchenko.LogisticsApp.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDTO> getAll() {
        return projectService.getAll().stream()
                .map(this.projectService::convertToProjectDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectDTO getById(@PathVariable long id) {
        return projectService.convertToProjectDTO(projectService.readById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ProjectDTO projectDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ProjectNotCreatedException(errorMessage.toString());
        }
        projectService.create(projectService.convertToProject(projectDTO));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable long id, @RequestBody @Valid ProjectDTO projectDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ProjectNotUpdatedException(errorMessage.toString());
        }
        Project updatedProject = projectService.readById(id);

        if (projectDTO.getPetroleumType() != null) {
            updatedProject.setPetroleumType(projectDTO.getPetroleumType());
        }
        if (projectDTO.getProjectCountry() != null) {
            updatedProject.setProjectCountry(projectDTO.getProjectCountry());
        }

        projectService.update(updatedProject);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        projectService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
