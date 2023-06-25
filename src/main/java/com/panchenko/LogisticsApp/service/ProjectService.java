package com.panchenko.LogisticsApp.service;

import com.panchenko.LogisticsApp.dto.ProjectDTO;
import com.panchenko.LogisticsApp.model.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project project);

    Project readById(long id);

    Project update(Project project, ProjectDTO projectDTO);

    void delete(long id);

    List<Project> getAll();

    Project convertToProject(ProjectDTO projectDTO);

    ProjectDTO convertToProjectDTO(Project project);
}
