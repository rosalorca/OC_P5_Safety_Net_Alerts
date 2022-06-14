package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(SafetyNetAlertsController.class)
class SafetyNetAlertsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonsService personsService;

    @MockBean
    private MedicalrecordsService medicalrecordsService;

    @Test
     void fireStation() {
    }

    @Test
    void childAlert() {
    }

    @Test
    void communityEmail() {
    }

    @Test
    void phoneAlert() {
    }

    @Test
    void fire() {
    }

    @Test
    void personInfo() {
    }

    @Test
    void floodAlert() {
    }
}