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
import static org.mockito.ArgumentMatchers.anyString;
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
    void testCreateMedicalrecordsPersonNotExist() throws Exception {
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

                .andReturn();


    }

    @Test
    void testCreateMedicalrecordsPersonExist() throws Exception {
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
    void testUpdateMedicalrecordsPersonExist() throws Exception {
        Medicalrecords update = Medicalrecords.builder()
                .medications(Collections.singletonList("thradox:700mg")).allergies(Collections.singletonList("illisoxian")).build();
        given(medicalrecordsService.updateMedicalrecords(anyString(), anyString(), any())).willReturn(update);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(update);
        mockMvc.perform(put("/Medicalrecords/Reginold/Walker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications").value("thradox:700mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies").value("illisoxian"));


    }

    @Test
    void testUpdateMedicalrecordsPersonNotExist() throws Exception {
        given(medicalrecordsService.updateMedicalrecords(anyString(), anyString(), any())).willReturn(null);
        Medicalrecords ozlem = new Medicalrecords(
                "Ozlem", "Donder", "24/02/1988",
                Collections.singletonList("dolipran:500"), Collections.singletonList("pollen"));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ozlem);
        mockMvc.perform(put("/Medicalrecords/Ozlem/Donder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotModified())
                .andReturn();

    }


    @Test
    void testDeleteMedicalrecordsPersonExist() throws Exception {
        given(medicalrecordsService.deleteMedicalrecords(anyString(),anyString())).willReturn(true);
        mockMvc.perform(delete("/Medicalrecords/Reginold/Walker"))
                .andExpect(status().isGone());
    }

    @Test
    void testDeleteMedicalrecordsPersonNotExist() throws Exception {
        given(medicalrecordsService.deleteMedicalrecords(anyString(),anyString())).willReturn(false);
        mockMvc.perform(delete("/Medicalrecords/Ozlem/Donder"))
                .andExpect(status().isNotModified());
    }
}