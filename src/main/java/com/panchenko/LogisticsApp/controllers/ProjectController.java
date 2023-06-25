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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/projects");
        map.put("status code", HttpStatus.OK);
        map.put("body", projectDTOList);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        ProjectDTO projectDTO = projectService.convertToProjectDTO(projectService.readById(id));
        Map<String, Object> map = new HashMap<>();
        map.put("header", "URL: /api/projects/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", projectDTO);
        return ResponseEntity.ok(map);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProjectDTO projectDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForCreate(bindingResult);

        Project project = projectService.create(projectService.convertToProject(projectDTO));

        ProjectDTO projectDTOResponse = projectService.convertToProjectDTO(project);

        Map<String, Object> map = new HashMap<>();

        map.put("header", "URL: /api/projects");
        map.put("status code", HttpStatus.CREATED);
        map.put("body", projectDTOResponse);
        return ResponseEntity.ok(map);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid ProjectDTO projectDTO,
                                    BindingResult bindingResult) {
        CheckErrors.checkErrorsForUpdate(bindingResult);

        Project updatedProject = projectService.readById(id);

        projectService.update(updatedProject, projectDTO);

        ProjectDTO projectDTOResponse = projectService.convertToProjectDTO(updatedProject);

        Map<String, Object> map = new HashMap<>();

        map.put("header", "URL: /api/projects/" + id);
        map.put("status code", HttpStatus.OK);
        map.put("body", projectDTOResponse);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id) {
        projectService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
