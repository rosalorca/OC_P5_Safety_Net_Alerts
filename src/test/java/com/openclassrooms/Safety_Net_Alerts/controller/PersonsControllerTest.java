package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonsController.class)
class PersonsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsService personsService;

    @Test
    void getPersons() throws Exception {
        Persons person = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        String result = String.valueOf(personsService.getPersons());
        assertEquals(person, result);

    }

        /*Persons John = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com" );
        List<Persons> allPersons = Arrays.asList(John);
        given(personsService.getPersons()).willReturn(allPersons);
        mockMvc.perform(get("/Persons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$ [0].firstName", is(John.getFirstName())));

    }


  /*  @Test
    void addPersons() {
    }

    @Test
    void updatePersons() {
    }

    @Test
    void deletePersons() {
    }*/
}