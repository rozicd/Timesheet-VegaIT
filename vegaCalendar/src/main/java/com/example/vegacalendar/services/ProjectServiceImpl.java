package com.example.vegacalendar.services;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.ProjectRepository;
import com.example.vegacalendar.core.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectModel save(ProjectModel projectModel) throws ResourceNotFoundException {
        return projectRepository.save(projectModel);
    }

    @Override
    public Paginated<ProjectModel> getAll(ProjectRequestParamsModel projectRequestParamsModel, LoggedUser loggedUser) {

        Paginated<ProjectModel> projectModelPaginated = projectRepository.getAll(projectRequestParamsModel);
        if(UserType.ADMIN == loggedUser.getRole()){
            return projectModelPaginated;
        }
        List<ProjectModel> teamLeadsProjects = projectModelPaginated.getItems().stream()
                .filter(projectModel -> projectModel.getTeamLeadId().equals(loggedUser.getId())).collect(Collectors.toList());
        projectModelPaginated.setItems(teamLeadsProjects);
        projectModelPaginated.setTotalItems(teamLeadsProjects.size());
        projectModelPaginated.setTotalPages((int) Math.ceil((double) teamLeadsProjects.size() /projectRequestParamsModel.getPageSize()));
        return projectModelPaginated;
    }

    @Override
    public ProjectModel update(ProjectModel projectModel, UUID id) throws ResourceNotFoundException {
        return projectRepository.update(projectModel, id);
    }

    @Override
    public ProjectModel getById(UUID id) throws ResourceNotFoundException {
        return projectRepository.getById(id);
    }

    @Override
    public List<ProjectModel> listAll() {
        return projectRepository.listAll();
    }
}
