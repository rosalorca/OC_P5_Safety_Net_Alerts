package com.openclassrooms.Safety_Net_Alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonsController.class)
class PersonsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsService personsService;


    @Test
    void testGetPersons() throws Exception {
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        List<Persons> allPersons = Arrays.asList(john);
        given(personsService.getPersons()).willReturn(allPersons);
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"));
    }


    @Test
    void testAddPersons() throws Exception {
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Istanbul", 34075, "123-456-7890", "ozlem@email.com");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ozlem);
        mockMvc.perform(post("/Persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..firstName").value("Ozlem"))
                .andExpect(jsonPath("$..lastName").value("Donder"))
                .andReturn();

    }


    @Test
    void testUpdatePersonsNotExists() throws Exception {
        given(personsService.updatePersons(anyString(), anyString(), any())).willReturn(null);
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(ozlem);
        mockMvc.perform(put("/Persons/Ozlem/Donder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    void testUpdatePersonsExists() throws Exception {
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com");
        given(personsService.updatePersons(anyString(), anyString(), any())).willReturn(lily);
        Persons update = Persons.builder().phone("841-874-9845").email("lily@email.com").build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(update);
        mockMvc.perform(put("/Persons/Lily/Cooper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cooper"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("841-874-9845"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("lily@email.com"));
    }

    @Test
    void testDeletePersonsExist() throws Exception {
        boolean isDeleted = true;
        given(personsService.deletePersons(anyString(), anyString())).willReturn(isDeleted);
        mockMvc.perform(delete("/Persons/Lily/Cooper"))
                .andExpect(status().isGone());

    }

    @Test
    void testDeletePersonsNotExist() throws Exception {

        mockMvc.perform(delete("/Persons/Ozlem/Donder"))
                .andExpect(status().isNotModified());

    }
}


