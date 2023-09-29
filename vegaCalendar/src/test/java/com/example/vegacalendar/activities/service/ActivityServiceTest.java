package com.example.vegacalendar.activities.service;

import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.ActivityRepository;
import com.example.vegacalendar.services.ActivityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityServiceImpl activityService;


    @Test
    void testSave_correctData_ActivityModel() throws ResourceNotFoundException, SQLIntegrityConstraintViolationException {
        ActivityModel activityModel = mockActivity();

        when(activityRepository.save(activityModel)).thenReturn(activityModel);
        ActivityModel savedActivity = activityService.save(activityModel);

        verify(activityRepository, times(1)).save(activityModel);
        assertEquals(activityModel, savedActivity);
    }

    @Test
    void testGetReport_correctData_ReportModel() {
        ReportRequestParamsModel reportRequestParamsModel = mockReportRequestParams();

        ReportModel mockReport = mockReportModel();
        when(activityRepository.getReport(reportRequestParamsModel)).thenReturn(mockReport);

        ReportModel report = activityService.getReport(reportRequestParamsModel);

        verify(activityRepository, times(1)).getReport(reportRequestParamsModel);
        assertEquals(mockReport, report);
    }

    @Test
    void testGetReport_nonExistingUser_emptyReportModel() {
        ReportRequestParamsModel reportRequestParamsModel = mockReportRequestParams();
        reportRequestParamsModel.setTeamMemberId(UUID.randomUUID());

        ReportModel mockReport = mockEmptyReport();
        when(activityRepository.getReport(reportRequestParamsModel)).thenReturn(mockReport);

        ReportModel report = activityService.getReport(reportRequestParamsModel);

        verify(activityRepository, times(1)).getReport(reportRequestParamsModel);
        assertEquals(mockReport, report);
    }

    @Test
    void testGetReport_nonExistingProject_emptyReportModel() {
        ReportRequestParamsModel reportRequestParamsModel = mockReportRequestParams();
        reportRequestParamsModel.setProjectId(UUID.randomUUID());

        ReportModel mockReport = mockEmptyReport();
        when(activityRepository.getReport(reportRequestParamsModel)).thenReturn(mockReport);

        ReportModel report = activityService.getReport(reportRequestParamsModel);

        verify(activityRepository, times(1)).getReport(reportRequestParamsModel);
        assertEquals(mockReport, report);
    }

    @Test
    void testGetReport_nonExistingClient_emptyReportModel() {
        ReportRequestParamsModel reportRequestParamsModel = mockReportRequestParams();
        reportRequestParamsModel.setClientId(UUID.randomUUID());

        ReportModel mockReport = mockEmptyReport();
        when(activityRepository.getReport(reportRequestParamsModel)).thenReturn(mockReport);

        ReportModel report = activityService.getReport(reportRequestParamsModel);

        verify(activityRepository, times(1)).getReport(reportRequestParamsModel);
        assertEquals(mockReport, report);
    }
    @Test
    void testGetActivityPerDay_CorrectDate_ActivityPerDayModel() {
        UUID id = UUID.randomUUID();
        DateRequestModel dateRequestModel = mockDateRequestModel();


        ActivityPerDayModel mockActivityPerDayModel = mockActivityPerDay();
        when(activityRepository.getActivityPerDay(id, dateRequestModel)).thenReturn(mockActivityPerDayModel);

        ActivityPerDayModel activityPerDayModel = activityService.getActivityPerDay(id, dateRequestModel);
        verify(activityRepository, times(1)).getActivityPerDay(id, dateRequestModel);

        assertEquals(mockActivityPerDayModel, activityPerDayModel);
    }

    @Test
    void testGetActivityPerDay_DateWithNoActivity_emptyActivityPerDayModel() {
        UUID id = UUID.randomUUID();
        DateRequestModel dateRequestModel = mockDateRequestModel();
        dateRequestModel.setStartDate(LocalDate.now());
        dateRequestModel.setEndDate(LocalDate.now().plusDays(2));

        ActivityPerDayModel mockActivityPerDayModel = mockEmptyActivityPerDay();
        when(activityRepository.getActivityPerDay(id, dateRequestModel)).thenReturn(mockActivityPerDayModel);

        ActivityPerDayModel activityPerDayModel = activityService.getActivityPerDay(id, dateRequestModel);
        verify(activityRepository, times(1)).getActivityPerDay(id, dateRequestModel);

        assertEquals(mockActivityPerDayModel, activityPerDayModel);
    }



    private ReportRequestParamsModel mockReportRequestParams(){
        ReportRequestParamsModel reportRequestParamsModel = new ReportRequestParamsModel();
        reportRequestParamsModel.setStartDate(LocalDate.now().minusDays(2));
        reportRequestParamsModel.setEndDate(LocalDate.now());
        return reportRequestParamsModel;
    }

    private ActivityPerDayModel mockActivityPerDay(){
        ActivityPerDayModel activityPerDayModel = new ActivityPerDayModel();
        DailyActivity dailyActivity1 = mockDailyActivity(0);
        dailyActivity1.setDailyHours(3.5, 1.5);
        DailyActivity dailyActivity2 = mockDailyActivity(1);
        dailyActivity1.setDailyHours(3.5, 1.5);
        DailyActivity dailyActivity3 = mockDailyActivity(2);
        dailyActivity1.setDailyHours(3.5, 1.5);

        List<DailyActivity> dailyActivities = new ArrayList<>();
        dailyActivities.add(dailyActivity1);
        activityPerDayModel.incrementTotalHours(dailyActivity1.getDailyHours(), (double) 0);
        dailyActivities.add(dailyActivity2);
        activityPerDayModel.incrementTotalHours(dailyActivity2.getDailyHours(), (double) 0);
        dailyActivities.add(dailyActivity3);
        activityPerDayModel.incrementTotalHours(dailyActivity3.getDailyHours(), (double) 0);

        activityPerDayModel.setHoursPerDay(dailyActivities);
        return activityPerDayModel;
    }

    private DateRequestModel mockDateRequestModel(){
        DateRequestModel dateRequestModel = new DateRequestModel();
        dateRequestModel.setStartDate(LocalDate.now().minusDays(2));
        dateRequestModel.setEndDate(LocalDate.now());
        return dateRequestModel;
    }

    private ActivityPerDayModel mockEmptyActivityPerDay(){
        ActivityPerDayModel activityPerDayModel = new ActivityPerDayModel();
        DailyActivity dailyActivity1 = mockDailyActivity(-1);
        DailyActivity dailyActivity2 = mockDailyActivity(-2);
        DailyActivity dailyActivity3 = mockDailyActivity(-3);


        List<DailyActivity> dailyActivities = new ArrayList<>();
        dailyActivities.add(dailyActivity1);
        dailyActivities.add(dailyActivity2);
        dailyActivities.add(dailyActivity3);

        activityPerDayModel.setHoursPerDay(dailyActivities);
        return activityPerDayModel;
    }

    private DailyActivity mockDailyActivity(int days){
        return new DailyActivity(LocalDate.now().minusDays(days));
    }

    private ReportModel mockEmptyReport(){
        ReportModel reportModel = new ReportModel();
        List<ActivityModel> activityModels = new ArrayList<>();
        reportModel.setActivityModels(activityModels);
        reportModel.setTotalHours(0);
        return reportModel;
    }
    private ActivityModel mockActivity(){
        ActivityModel activityModel = new ActivityModel();
        activityModel.setActivitysId(UUID.randomUUID());
        activityModel.setDate(LocalDate.now());
        activityModel.setProject(mockProject()); // You need to create a Project instance
        activityModel.setUser(mockUser()); // You need to create a User instance
        activityModel.setCategory(mockCategory()); // You need to create a Category instance
        activityModel.setDescription("Sample activity description");
        activityModel.setTime(2.5);
        activityModel.setOvertime(1.0);
        return activityModel;
    }

    private ReportModel mockReportModel(){
        ReportModel reportModel = new ReportModel();
        ActivityModel activityModel1 = mockActivity();
        ActivityModel activityModel2 = mockActivity();
        activityModel2.setDate(LocalDate.now().minusDays(1));
        List<ActivityModel> activityModels = new ArrayList<>();
        activityModels.add(activityModel1);
        activityModels.add(activityModel2);
        reportModel.setActivityModels(activityModels);
        reportModel.setTotalHours(10);
        return reportModel;
    }
    private UserModel mockUser(){
        UserModel user = new UserModel();
        user.setUserId(UUID.randomUUID());
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("johndoe@example.com");
        user.setUserType(UserType.WORKER);
        user.setPassword("password123");
        user.setStatus(Status.ACTIVE);
        return user;
    }

    private CategoryModel mockCategory(){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setCategoryId(UUID.randomUUID());
        categoryModel.setName("name");

        return   categoryModel;
    }

    private ClientModel mockClient(){
        ClientModel clientModel = new ClientModel();
        CountryModel countryModel = new CountryModel();
        countryModel.setCountryId(UUID.randomUUID());
        countryModel.setName("Country");
        clientModel.setCountry(countryModel);
        clientModel.setName("Name");
        clientModel.setCity("City");
        clientModel.setAddress("Address");
        clientModel.setZip("Zip");
        return clientModel;
    }

    private ProjectModel mockProject(){
        ProjectModel project = new ProjectModel();
        UserModel teamLead = mockUser();
        ClientModel client = mockClient();
        project.setProjectId(UUID.randomUUID());
        project.setName("Sample Project");
        project.setDescription("This is a sample project description.");
        project.setClientId(client.getClientId()); // Set the client ID
        project.setTeamLeadId(teamLead.getUserId()); // Set the team lead ID
        project.setClient(client);
        project.setTeamLead(teamLead);

        List<UUID> teamMemberIds = new ArrayList<>();
        project.setTeamMembersIDs(teamMemberIds);

        List<UserModel> teamMembers = new ArrayList<>();
        project.setTeamMembers(teamMembers);

        project.setStatus(Status.ACTIVE);

        return project;
    }

}
