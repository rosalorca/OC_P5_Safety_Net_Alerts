package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SafetyNetAlertsController.class, SafetyNetAlertsControllerTest.ContextConfiguration.class})
class SafetyNetAlertsControllerTest {

    @Configuration
    protected static class ContextConfiguration {
        @Bean
        public FirestationsService firestationsService() {
            return Mockito.mock(FirestationsService.class);
        }

        @Bean
        public PersonsService personsService() {
            return Mockito.mock(PersonsService.class);
        }

        @Bean
        public MedicalrecordsService medicalrecordsService() {
            return Mockito.mock(MedicalrecordsService.class);
        }
    }

    SafetyNetAlertsController controller = new SafetyNetAlertsController();

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
        List<String> phones = controller.phoneAlert(1);
        assertEquals(6, phones.size());
        assertTrue(phones.contains("841-874-6512"));
    }

    @Test
    void fire() {
    }

    @Test
    void personInfo() {
    }
}