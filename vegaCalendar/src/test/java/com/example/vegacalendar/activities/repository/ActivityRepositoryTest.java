package com.example.vegacalendar.activities.repository;

import com.example.vegacalendar.application.Constants;
import com.example.vegacalendar.core.exceptions.ResourceNotFoundException;
import com.example.vegacalendar.core.model.*;
import com.example.vegacalendar.core.repositories.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "com.example.vegacalendar.data.repositories")
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testSaveActivity_CorrectData_returnsActivityModel() throws SQLIntegrityConstraintViolationException, ResourceNotFoundException {
        ActivityModel activityModel = getActivityModelCorrectData();

        ActivityModel savedActivity = activityRepository.save(activityModel);
        ActivityModel foundActivity = activityRepository.getById(savedActivity.getActivitysId());

        assertEquals(savedActivity.getActivitysId(), foundActivity.getActivitysId());
        assertEquals(savedActivity.getDate(), foundActivity.getDate());
    }

    @Test
    void testGetAll_CorrectData_returnsReport(){
        ReportRequestParamsModel reportRequestParamsModel = getReportRequestParamsModelCorrectData();

        ReportModel reportModel = activityRepository.getReport(reportRequestParamsModel);

        assertNotNull(reportModel);
    }
    @Test
    void testGetAll_nonExistingTeamMember_returnsEmptyReport(){
        ReportRequestParamsModel reportRequestParamsModel = getReportRequestParamsModelCorrectData();
        reportRequestParamsModel.setTeamMemberId(UUID.randomUUID());

        ReportModel reportModel = activityRepository.getReport(reportRequestParamsModel);

        assertEquals(reportModel.getActivityModels().size(), 0);
    }

    @Test
    void testGetAll_nonExistingProject_returnsEmptyReport(){
        ReportRequestParamsModel reportRequestParamsModel = getReportRequestParamsModelCorrectData();
        reportRequestParamsModel.setProjectId(UUID.randomUUID());

        ReportModel reportModel = activityRepository.getReport(reportRequestParamsModel);

        assertEquals(reportModel.getActivityModels().size(), 0);
    }

    @Test
    void testGetAll_nonExistingClient_returnsEmptyReport(){
        ReportRequestParamsModel reportRequestParamsModel = getReportRequestParamsModelCorrectData();
        reportRequestParamsModel.setClientId(UUID.randomUUID());

        ReportModel reportModel = activityRepository.getReport(reportRequestParamsModel);

        assertEquals(reportModel.getActivityModels().size(), 0);
    }

    @Test
    void testGetActivityPerDay_CorrectData_returnsHoursPerDay(){
        DateRequestModel dateRequestModel = getDateRequestModel();
        UUID userId = UUID.fromString(Constants.TEST_TEAM_MEMBER);

        ActivityPerDayModel activityPerDayModel = activityRepository.getActivityPerDay(userId, dateRequestModel);

        assertNotNull(activityPerDayModel);

    }

    private DateRequestModel getDateRequestModel(){
        DateRequestModel dateRequestModel = new DateRequestModel();
        dateRequestModel.setStartDate(LocalDate.now().minusDays(10));
        dateRequestModel.setEndDate(LocalDate.now());
        return dateRequestModel;
    }
    private ActivityModel getActivityModelCorrectData(){
        ActivityModel activityModel = new ActivityModel();
        UserModel teamMember = new UserModel();
        ClientModel clientModel = new ClientModel();
        ProjectModel projectModel = new ProjectModel();
        CategoryModel categoryModel = new CategoryModel();
        teamMember.setUserId(UUID.fromString(Constants.TEST_TEAM_MEMBER));
        clientModel.setClientId(UUID.fromString(Constants.TEST_CLIENT));
        projectModel.setProjectId(UUID.fromString(Constants.TEST_PROJECT));
        categoryModel.setCategoryId(UUID.fromString(Constants.TEST_CATEGORY));
        activityModel.setUser(teamMember);
        activityModel.setClient(clientModel);
        activityModel.setProject(projectModel);
        activityModel.setCategory(categoryModel);
        activityModel.setDescription("description");
        activityModel.setTime(5.5);
        activityModel.setOvertime(1.5);
        return activityModel;
    }

    private ReportRequestParamsModel getReportRequestParamsModelCorrectData(){
        return new ReportRequestParamsModel();
    }

}
