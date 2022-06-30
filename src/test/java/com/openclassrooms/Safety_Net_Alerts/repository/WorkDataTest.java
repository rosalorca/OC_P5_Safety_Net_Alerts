package com.openclassrooms.Safety_Net_Alerts.repository;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WorkDataTest {

    @Test
    void getPersons() throws Exception {
        WorkData wd = new WorkData();
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        List<Persons> allPersons = Arrays.asList(john, ozlem);
        wd.setPersons(allPersons);
        final java.util.List<Persons> result = wd.getPersons();
        assertEquals(allPersons, result);

    }

    @Test
    void setPersons() throws Exception {
        WorkData wd = new WorkData();
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");
       Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
       List<Persons> allPersons = Arrays.asList(john, ozlem);
        wd.setPersons(allPersons);
        final List<Persons> result = wd.getPersons();
        assertEquals(allPersons, result);
    }

    @Test
    void getFirestations() throws Exception {
        WorkData wd = new WorkData();
        Firestations station1 = new Firestations(5, "address1");
        Firestations station2 = new Firestations(5, "addresse2");
        List<Firestations> allStations = Arrays.asList(station1,station2);
        wd.setFirestations(allStations);
        final List<Firestations> result = wd.getFirestations();
        assertEquals(allStations, result);
    }

    @Test
    void getMedicalrecords() throws Exception {
        WorkData wd = new WorkData();
        Medicalrecords mr = new Medicalrecords
                ("John", "Boyd", "03/06/1984", java.util.Collections.singletonList("aznol:350mg"), java.util.Collections.singletonList("nillacilan"));
        List<Medicalrecords> allMR = Arrays.asList(mr);
        wd.setMedicalrecords(allMR);
        final List<Medicalrecords> result = wd.getMedicalrecords();
        assertEquals(allMR,result);
    }

    @Test
    void setFirestations() throws Exception {
        WorkData wd = new WorkData();
        Firestations station1 = new Firestations(4, "112 Steppes Pl");
        List<Firestations> allStations = Arrays.asList(station1);
        wd.setFirestations(allStations);
        final List<Firestations> result = wd.getFirestations();
        assertEquals(allStations, result);
    }

    @Test
    void setMedicalrecords() throws Exception {
        WorkData wd = new WorkData();
        Medicalrecords mr = new Medicalrecords
                ("John", "Boyd", "03/06/1984", java.util.Collections.singletonList("aznol:350mg"), java.util.Collections.singletonList("nillacilan"));
        List<Medicalrecords> allMR = Arrays.asList(mr);
        wd.setMedicalrecords(allMR);
        final List<Medicalrecords> result = wd.getMedicalrecords();
        assertEquals(allMR,result);
    }


}
