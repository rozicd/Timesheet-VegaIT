package com.example.vegacalendar.activities.controller;

import com.example.vegacalendar.application.Constants;
import com.example.vegacalendar.application.dtos.*;
import com.example.vegacalendar.core.model.ActivityPerDayModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActivityControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    public HttpHeaders headers;

    @BeforeEach
    public void setUpHeaders(){
        headers = new HttpHeaders();
        headers.set("content-type", "application/json");
    }

    public String login(String email, String password){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        HttpEntity<LoginDTO> entity = new HttpEntity<LoginDTO>(loginDTO,headers);

        ResponseEntity<JwtResponseDTO> responseEntity = restTemplate.exchange("/api/user/generateToken", HttpMethod.POST, entity, new ParameterizedTypeReference<JwtResponseDTO>() {});
        return  Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
    }

    @DisplayName("Should save activity - POST /api/activity")
    @Test
    public void testSave_ok_workerJWT(){
        headers.set("Authorization", "Bearer "+login("zatest2@email.com","password"));
        CreateActivityRequestDTO createActivityRequestDTO = new CreateActivityRequestDTO();
        createActivityRequestDTO.setCategoryId(UUID.fromString(Constants.TEST_CATEGORY));
        createActivityRequestDTO.setClientId(UUID.fromString(Constants.TEST_CLIENT));
        createActivityRequestDTO.setProjectId(UUID.fromString(Constants.TEST_PROJECT));

        HttpEntity<CreateActivityRequestDTO> entity = new HttpEntity<>(createActivityRequestDTO, headers);
        ResponseEntity<ActivityResponseDTO> responseEntity = restTemplate.exchange("/api/activity", HttpMethod.POST, entity, new ParameterizedTypeReference<ActivityResponseDTO>() {});
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @DisplayName("Should be unauthorized - POST /api/activity")
    @Test
    public void testSave_unauthorized(){
        CreateActivityRequestDTO createActivityRequestDTO = new CreateActivityRequestDTO();
        createActivityRequestDTO.setCategoryId(UUID.fromString(Constants.TEST_CATEGORY));
        createActivityRequestDTO.setClientId(UUID.fromString(Constants.TEST_CLIENT));
        createActivityRequestDTO.setProjectId(UUID.fromString(Constants.TEST_PROJECT));

        HttpEntity<CreateActivityRequestDTO> entity = new HttpEntity<>(createActivityRequestDTO, headers);
        try {
            ResponseEntity<Exception> exception = restTemplate.exchange("/api/activity", HttpMethod.POST, entity, new ParameterizedTypeReference<Exception>() {
            });
        }catch(ResourceAccessException resourceAccessException){
            assertNotNull(resourceAccessException);
        }
    }

    @DisplayName("Should get report - GET /api/activity/all")
    @Test
    public void testGetReport_adminJWT(){
        headers.set("Authorization", "Bearer "+login("admin@email.com","password"));
        ReportRequestParamsDTO reportRequestParamsDTO = new ReportRequestParamsDTO(null, null, null, null, null, null);

        HttpEntity<ReportRequestParamsDTO> entity = new HttpEntity<>(reportRequestParamsDTO, headers);

        ResponseEntity<ReportResponseDTO> responseEntity = restTemplate.exchange("/api/activity/all", HttpMethod.GET, entity, new ParameterizedTypeReference<ReportResponseDTO>() {});

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @DisplayName("Should be forbidden - GET /api/activity/all")
    @Test
    public void testGetReport_forbidden_workerJWT(){
        headers.set("Authorization", "Bearer "+login("zatest2@email.com","password"));
        ReportRequestParamsDTO reportRequestParamsDTO = new ReportRequestParamsDTO(null, null, null, null, null, null);

        HttpEntity<ReportRequestParamsDTO> entity = new HttpEntity<>(reportRequestParamsDTO, headers);

        ResponseEntity<Exception> responseEntity = restTemplate.exchange("/api/activity/all", HttpMethod.GET, entity, new ParameterizedTypeReference<Exception>() {});

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @DisplayName("Should get activity per day - GET /api/activity/per-day")
    @Test
    public void testGetActivityPerDay_ok_workerJWT(){
        headers.set("Authorization", "Bearer "+login("zatest2@email.com","password"));
        DateRequestDTO dateRequestDTO = new DateRequestDTO();

        HttpEntity<DateRequestDTO> entity = new HttpEntity<>(dateRequestDTO, headers);
        String url = "/api/activity/per-day?startDate=2023-09-20&endDate=2023-09-25";
        ResponseEntity<ActivityPerDayModel> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<ActivityPerDayModel>() {});

        assertNotNull(responseEntity.getBody());
        System.out.println(responseEntity.getBody().toString());
        assertNotEquals(0, responseEntity.getBody().getHoursPerDay().size());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

}
