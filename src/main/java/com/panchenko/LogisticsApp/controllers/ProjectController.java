package com.panchenko.LogisticsApp.controllers;

import com.panchenko.LogisticsApp.dto.ProjectDTO;
import com.panchenko.LogisticsApp.model.Project;
import com.panchenko.LogisticsApp.service.ProjectService;
import com.panchenko.LogisticsApp.util.CheckErrors;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProjectDTO> projectDTOList = projectService.getAll().stream()
                .map(projectService::convertToProjectDTO).toList();
        return ResponseEntity.ok(projectDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ProjectDTO projectDTO = projectService.convertToProjectDTO(projectService.readById(id));
        return ResponseEntity.ok(projectDTO);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProjectDTO projectDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);
        Project project = projectService.create(projectService.convertToProject(projectDTO));
        ProjectDTO projectDTOResponse = projectService.convertToProjectDTO(project);
        return ResponseEntity.ok(projectDTOResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid ProjectDTO projectDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);
        Project updatedProject = projectService.readById(id);
        projectService.update(updatedProject, projectDTO);
        ProjectDTO projectDTOResponse = projectService.convertToProjectDTO(updatedProject);
        return ResponseEntity.ok(projectDTOResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        projectService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
