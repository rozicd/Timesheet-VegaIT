package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.SecurityContextExtractor;
import com.example.vegacalendar.application.dtos.*;
import com.example.vegacalendar.application.exceptionModels.ExceptionModel;
import com.example.vegacalendar.core.exceptions.JwtExpiredException;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.services.PrincipalService;
import com.example.vegacalendar.core.services.ProjectService;
import io.jsonwebtoken.ExpiredJwtException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityContextExtractor securityContextExtractor;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody CreateProjectRequestDTO createProjectRequestDTO) throws ResourceNotFoundException {
        ProjectModel projectModel = modelMapper.map(createProjectRequestDTO, ProjectModel.class);
        UserModel teamLead = new UserModel();
        teamLead.setUserId(createProjectRequestDTO.getTeamLeadId());
        projectModel.setTeamLead(teamLead);
        ProjectModel savedProject = projectService.save(projectModel);
        ProjectResponseDTO projectResponseDTO = modelMapper.map(savedProject, ProjectResponseDTO.class);
        return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateProjectRequestDTO updateProjectRequestDTO, @PathVariable UUID id) throws ResourceNotFoundException {
        ProjectModel projectModel = modelMapper.map(updateProjectRequestDTO, ProjectModel.class);
        ProjectModel newProject = projectService.update(projectModel, id);
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(ProjectRequestParamsDTO projectRequestParamsDTO) throws JwtExpiredException {
        LoggedUser loggedUser = securityContextExtractor.getLoggedUser();
        ProjectRequestParamsModel projectRequestParamsModel = modelMapper.map(projectRequestParamsDTO, ProjectRequestParamsModel.class);
        Paginated<ProjectModel> projectModelPaginated = projectService.getAll(projectRequestParamsModel, loggedUser);
        List<ProjectResponseDTO> projectResponseDTOS = projectModelPaginated.getItems().stream()
                .map(projectModel -> modelMapper.map(projectModel, ProjectResponseDTO.class)).collect(Collectors.toList());
        Paginated<ProjectResponseDTO> projectPaginatedResponse = new Paginated<>(projectResponseDTOS, projectRequestParamsModel.getPage(), projectModelPaginated.getTotalItems(), projectModelPaginated.getTotalPages());
        return new ResponseEntity<>(projectPaginatedResponse, HttpStatus.OK);
    }
    @GetMapping(path = "/list-all")
    public ResponseEntity<?>listAll(){
        List<ProjectModel> projectModels = projectService.listAll();
        List<ProjectResponseDTO> projectResponseDTOS = projectModels.stream()
                .map(projectModel -> modelMapper.map(projectModel, ProjectResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(projectResponseDTOS, HttpStatus.OK);
    }

}
