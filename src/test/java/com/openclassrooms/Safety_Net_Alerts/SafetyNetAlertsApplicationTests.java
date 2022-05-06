package com.openclassrooms.Safety_Net_Alerts;

import com.openclassrooms.Safety_Net_Alerts.controller.SafetyNetAlertsController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SafetyNetAlertsApplicationTests {

    @Test
    void contextLoads() {
    }

   /* @Test
    void testCalculateAge() throws ParseException {
        assertEquals(1, SafetyNetAlertsController.calculateAge("01/01/2021"));
        assertEquals(0, SafetyNetAlertsController.calculateAge("01/01/2022"));
        assertEquals(34, SafetyNetAlertsController.calculateAge("24/02/1988"));
        assertEquals(35, SafetyNetAlertsController.calculateAge("24/11/1986"));
    }

    @Test
    void shouldThrowExceptionIfDateInFuture() {
        assertThrows(IllegalArgumentException.class, () -> {
            SafetyNetAlertsController.calculateAge("24/11/2986");
        });
    }*/

}
