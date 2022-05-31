package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
/*@WebMvcTest(PersonsController.class)
@AutoConfigureMockMvc*/
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { ApplicationConfig.class })
@ContextConfiguration(locations={""})
@WebAppConfiguration(value = "")
class PersonsControllerTest {
    //@Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private PersonsService personsService;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Test
    void testGetPersons() throws Exception {
        mockMvc.perform(get("/Persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) view().name("John"));
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

