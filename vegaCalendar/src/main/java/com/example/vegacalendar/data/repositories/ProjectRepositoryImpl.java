package com.example.vegacalendar.data.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.ProjectRepository;
import com.example.vegacalendar.data.entities.Project;
import com.example.vegacalendar.data.jpas.ProjectJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    @Autowired
    private ProjectJPARepository projectJPARepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProjectModel save(ProjectModel projectModel) throws ResourceNotFoundException {
        projectModel.setProjectId(null);
        Project project = modelMapper.map(projectModel, Project.class);
        project.setTeamMembers(new ArrayList<>());

        projectJPARepository.save(project);
        ProjectModel savedProject = modelMapper.map(project, ProjectModel.class);
        savedProject.setTeamMembers(new ArrayList<>());
        return savedProject;
    }

    @Override
    public Paginated<ProjectModel> getAll(ProjectRequestParamsModel projectRequestParamsModel) {
        Pageable pageable = (Pageable) PageRequest.of(projectRequestParamsModel.getPage()-1, projectRequestParamsModel.getPageSize());
        Page<Project> projectPage = projectJPARepository.findByNameStartingWithAndNameStartingWithAndDeletedFalse(projectRequestParamsModel.getLetter(), projectRequestParamsModel.getSearch(),pageable);

        List<ProjectModel> projectModels = projectPage.getContent().stream().map(project -> modelMapper.map(project, ProjectModel.class)
        ).collect(Collectors.toList());

        return new Paginated<ProjectModel>(projectModels, projectRequestParamsModel.getPage(), (int) projectPage.getTotalElements(), projectPage.getTotalPages());
    }

    @Override
    public ProjectModel update(ProjectModel projectModel, UUID id) throws ResourceNotFoundException {
        Optional<Project> optionalProject = projectJPARepository.findById(id);
        if(optionalProject.isEmpty()){
            throw new ResourceNotFoundException("Project with Id: " + id + " doesn't exist");
        }
        Project project = modelMapper.map(projectModel, Project.class);
        project.setProjectId(id);
        projectJPARepository.save(project);
        return modelMapper.map(project, ProjectModel.class);
    }

    @Override
    public ProjectModel getById(UUID id) throws ResourceNotFoundException {
        Optional<Project> project = projectJPARepository.findById(id);
        if(project.isEmpty()){
            throw new ResourceNotFoundException("Project with Id: " + id + " doesn't exist");
        }
        return modelMapper.map(project, ProjectModel.class);
    }

    @Override
    public List<ProjectModel> listAll() {
        Iterable<Project> projects = projectJPARepository.findAll();
        List<ProjectModel> projectModels = StreamSupport.stream(projects.spliterator(), false)
                .map(project -> modelMapper.map(project, ProjectModel.class))
                .collect(Collectors.toList());
        return projectModels;
    }

}
