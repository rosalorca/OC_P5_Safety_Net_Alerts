package com.openclassrooms.Safety_Net_Alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FirestationsController.class)
class FirestationsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationsService firestationsService;

    @Test
    void testGetFirestations() throws Exception {
        Firestations fireStation = new Firestations(3, "1509 Culver St");
        given(firestationsService.getFirestations()).willReturn(Collections.singletonList(fireStation));
        mockMvc.perform(get("/Firestations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].station").value("3"));
    }

    @Test
    void getAddFirestation() throws Exception {
        Firestations fireStation = new Firestations(2, "1871 Paris St");
        given(firestationsService.getFirestations()).willReturn(Collections.singletonList(fireStation));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fireStation);
        mockMvc.perform(post("/Firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1871 Paris St"))
                .andReturn();
    }


    @Test
    void testUpdateFirestationNotExist() throws Exception {
        given(firestationsService.updateFirestation(anyString(), any())).willReturn(null);
        Firestations fireStation = new Firestations(5, "1871 Özlem St");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fireStation);
        mockMvc.perform(put("/Firestations/1871%20Özlem%20St/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    void testUpdateFirestationExist() throws Exception {
        Firestations update = Firestations.builder().station(3).address("1509 Culver St").build();
        given(firestationsService.updateFirestation(anyString(), any())).willReturn(update);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(update);
        mockMvc.perform(put("/Firestations/1509%20Culver%20St/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1509 Culver St"));
    }

    @Test
    void testDeleteFirestationExist() throws Exception {
        given(firestationsService.deleteFirestation(anyString(), any())).willReturn(true);
        mockMvc.perform(delete("/Firestations/1509%20Culver%20St/3"))
                .andExpect(status().isGone());
    }

    @Test
    void testDeleteFirestationNotExist() throws Exception {
        given(firestationsService.deleteFirestation(anyString(), any())).willReturn(false);
        mockMvc.perform(delete("/Firestations/1871%20Özlem%20St/3"))
                .andExpect(status().isNotModified());
    }
}