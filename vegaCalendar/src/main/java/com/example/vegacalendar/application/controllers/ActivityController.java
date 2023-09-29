package com.example.vegacalendar.application.controllers;

import com.example.vegacalendar.application.SecurityContextExtractor;
import com.example.vegacalendar.application.dtos.*;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.services.ActivityService;
import com.example.vegacalendar.core.services.PrincipalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "api/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SecurityContextExtractor securityContextExtractor;

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody CreateActivityRequestDTO createActivityRequestDTO) throws ResourceNotFoundException, SQLIntegrityConstraintViolationException {
        ActivityModel activityModel = modelMapper.map(createActivityRequestDTO, ActivityModel.class);
        LoggedUser loggedUser = securityContextExtractor.getLoggedUser();
        UUID userId = loggedUser.getId();
        UserModel userForSave = new UserModel();
        userForSave.setUserId(userId);
        activityModel.setUser(userForSave);
        ActivityModel savedActivity = activityService.save(activityModel);
        ActivityResponseDTO activityResponseDTO = modelMapper.map(savedActivity, ActivityResponseDTO.class);
        return new ResponseEntity<>(activityResponseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getReport(ReportRequestParamsDTO reportRequestParamsDTO){
        ReportRequestParamsModel reportRequestParamsModel = modelMapper.map(reportRequestParamsDTO, ReportRequestParamsModel.class);
        ReportModel reportModel = activityService.getReport(reportRequestParamsModel);
        List<ActivityResponseDTO> activityResponseDTOS  = reportModel.getActivityModels().stream().map(activityModel -> modelMapper
                .map(activityModel, ActivityResponseDTO.class))
                .collect(Collectors.toList());
        ReportResponseDTO reportResponseDTO = modelMapper.map(reportModel, ReportResponseDTO.class);
        reportResponseDTO.setActivityResponseDTOS(activityResponseDTOS);
        return new ResponseEntity<>(reportResponseDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/per-day")
    public ResponseEntity<?> getActivityPerDay(DateRequestDTO dateRequestDTO){
        LoggedUser loggedUser = securityContextExtractor.getLoggedUser();
        UUID userId = loggedUser.getId();
        DateRequestModel dateRequestModel = modelMapper.map(dateRequestDTO, DateRequestModel.class);
        ActivityPerDayModel activityPerDayModel = activityService.getActivityPerDay(userId, dateRequestModel);
        return new ResponseEntity<>(activityPerDayModel, HttpStatus.OK);
    }

    @GetMapping(path = "/daily-activities")
    public ResponseEntity<?> getDailyActivities(@RequestParam LocalDate date){
        LoggedUser loggedUser = securityContextExtractor.getLoggedUser();
        DailyActivitiesModel dailyActivitiesModel = activityService.getDailyActivities(loggedUser.getId(), date);
        List<ActivityResponseDTO> activityResponseDTOS = dailyActivitiesModel.getActivities().stream()
                .map(activityModel -> modelMapper.map(activityModel, ActivityResponseDTO.class))
                .collect(Collectors.toList());
        DailyActivitiesResponseDTO dailyActivitiesResponseDTO = new DailyActivitiesResponseDTO(activityResponseDTOS, dailyActivitiesModel.getTotalHours());
        return new ResponseEntity<>(dailyActivitiesResponseDTO, HttpStatus.OK);
    }



}
