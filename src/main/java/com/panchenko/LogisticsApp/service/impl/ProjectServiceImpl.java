package com.panchenko.LogisticsApp.service.impl;

import com.panchenko.LogisticsApp.dto.ProjectDTO;
import com.panchenko.LogisticsApp.exception.NullEntityReferenceException;
import com.panchenko.LogisticsApp.exception.ProjectException.ProjectNotFoundException;
import com.panchenko.LogisticsApp.model.Project;
import com.panchenko.LogisticsApp.repository.ProjectRepository;
import com.panchenko.LogisticsApp.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Project create(Project project) {
        if (project == null) {
            throw new NullPointerException("Project cannot be 'null'");
        }
        return projectRepository.save(project);
    }

    @Override
    public Project readById(long id) {
        return projectRepository.findById(id).orElseThrow(
                () -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    @Override
    public Project update(Project project) {
        if (project == null) {
            throw new NullEntityReferenceException("Project cannot be 'null'");
        }
        readById(project.getId());
        return projectRepository.save(project);
    }

    @Override
    public void delete(long id) {
        Project project = readById(id);
        projectRepository.delete(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    @Override
    public ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}
