package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.PersonsWithNumberOfAdultsAndChildren;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void fireStation() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        Persons lily = new Persons
                ("Lily", "Cooper", "908 73rd St", "Culver", 97451, "841-874-9845", "lily@email.com");
        listPersons.add(ozlem);
        listPersons.add(lily);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);

        Medicalrecords mrOzlem = new Medicalrecords("Ozlem", "Donder", "24/02/1988", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Ozlem"), eq("Donder"))).willReturn(mrOzlem);
        Medicalrecords mrLily = new Medicalrecords("Lily", "Cooper", "21/08/2015", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Lily"), eq("Cooper"))).willReturn(mrLily);

        mockMvc.perform(get("/fireStation?stationNumber=" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbAdults").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbChildren").value(1));

    }

    @Test
    void childAlert() throws Exception {
    }

    @Test
    void communityEmail() throws Exception {
    }

    @Test
    void phoneAlert() throws Exception {
    }

    @Test
    void fire() throws Exception {
    }

    @Test
    void personInfo() throws Exception {
    }

    @Test
    void floodAlert() throws Exception {
    }
}