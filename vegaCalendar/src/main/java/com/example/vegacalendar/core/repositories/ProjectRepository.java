package com.example.vegacalendar.core.repositories;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.Paginated;
import com.example.vegacalendar.core.model.ProjectModel;
import com.example.vegacalendar.core.model.ProjectRequestParamsModel;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {
    ProjectModel save(ProjectModel projectModel) throws ResourceNotFoundException;
    Paginated<ProjectModel> getAll(ProjectRequestParamsModel projectRequestParamsModel);
    ProjectModel update(ProjectModel projectModel, UUID id) throws ResourceNotFoundException;
    ProjectModel getById(UUID id) throws ResourceNotFoundException;

    List<ProjectModel> listAll();
}
