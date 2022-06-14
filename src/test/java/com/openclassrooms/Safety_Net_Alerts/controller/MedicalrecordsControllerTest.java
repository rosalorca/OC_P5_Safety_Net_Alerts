package com.openclassrooms.Safety_Net_Alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalrecordsController.class)
class MedicalrecordsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalrecordsService medicalrecordsService;

    @Test
    void getMedicalrecords() throws Exception {
        Medicalrecords reginold = new Medicalrecords(
                "Reginold", "Walker", "08/30/1979",
                Collections.singletonList("thradox:700mg"), Collections.singletonList("illisoxian"));
        List<Medicalrecords> allMR = Arrays.asList(reginold);
        given(medicalrecordsService.getMedicalrecords()).willReturn(allMR);
        mockMvc.perform(get("/Medicalrecords"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].medications").value("thradox:700mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].allergies").value("illisoxian"));

    }

    @Test
    void testCreateMedicalrecordsPersonNonExist() throws Exception {
        Medicalrecords ozlem = new Medicalrecords(
                "Özlem", "Dönder", "24/02/1988",
                Collections.singletonList("dolipran:500mg"), Collections.singletonList("pollen"));
        given(medicalrecordsService.createMedicalrecords(any())).willReturn(true);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ozlem);
        mockMvc.perform(post("/Medicalrecords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
              /*  .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Özlem"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dönder"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("24/02/1988"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].allergies").value("pollen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].medications").value("dolipran:500mg"))*/
                .andReturn();


    }
    @Test
    void testCreateMedicalrecordsPersonExist() throws Exception{
        given(medicalrecordsService.createMedicalrecords(any())).willReturn(false);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(null);
        mockMvc.perform(post("/Medicalrecords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }


    @Test
    void testUpdateMedicalrecords() throws Exception {
    }

    @Test
    void testDeleteMedicalrecords() throws Exception {
    }
}