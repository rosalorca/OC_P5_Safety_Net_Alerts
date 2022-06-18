package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.repository.WorkData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PersonsServiceTest {
    @MockBean
    private DataStore dataStore;

    private WorkData data;

    private PersonsService personsService = new PersonsService();

    @BeforeEach
    void setup() {
        personsService.setDataStore(dataStore);
        data = Mockito.mock(WorkData.class);
        given(dataStore.getData()).willReturn(data);
    }

    @Test
    void testGetPersonsInCity() throws Exception {
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        List<Persons> allPersons = Arrays.asList(john, ozlem);
        given(data.getPersons()).willReturn(allPersons);

        final List<Persons> result = personsService.getPersonsInCity("Culver");
        assertEquals(1, result.size());
    }

    @Test
    void testGetPersonsAtAddresses() throws Exception {
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "1509 Culver St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com");
        List<Persons> allPersons = Arrays.asList(john, ozlem, lily);
        given(data.getPersons()).willReturn(allPersons);
        final List<Persons> result = personsService.getPersonsAtAddresses(Collections.singletonList("1509 Culver St"));
        assertEquals(2, result.size());
    }

    @Test
    void testGetPersonInfo() throws Exception {
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        List<Persons> allPersons = Arrays.asList(john, ozlem);
        given(data.getPersons()).willReturn(allPersons);

        final List<Persons> result = personsService.getPersonInfo("Donder");
        assertEquals(1, result.size());
    }

    @Test
    void testAddPersons() throws Exception {
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Istanbul", 34075, "123-456-7890", "ozlem-paris@email.com");
        List<Persons> allPersons = new ArrayList<>();
        given(data.getPersons()).willReturn(allPersons);
        personsService.addPersons(ozlem);
        assertEquals(1, allPersons.size());
        assertTrue(allPersons.contains(ozlem));


    }

    @Test
    void testUpdatePersons() throws Exception {


    }

    @Test
    void testDeletePersons() throws Exception {


    }
}