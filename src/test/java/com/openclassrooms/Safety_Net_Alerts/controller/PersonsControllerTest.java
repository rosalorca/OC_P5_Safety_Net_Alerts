package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.controller.PersonsController;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonsController.class)
class PersonsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsService personsService;

    @Test
    void testGetPersons() throws Exception {
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com" );
        List<Persons> allPersons = Arrays.asList(john);
        given(personsService.getPersons()).willReturn(allPersons);
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"));
    }
}

      /*  mockMvc.perform(get("/Persons"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].firstname", is("John")));

    }
}

        /*Persons person = new Persons
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

