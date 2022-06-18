package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.repository.WorkData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class FirestationsServiceTest {
    @MockBean
    private DataStore dataStore;

    private WorkData data;
    private FirestationsService firestationsService = new FirestationsService();

    @BeforeEach
    void setup() {
        firestationsService.setDataStore(dataStore);
        data = Mockito.mock(WorkData.class);
        given(dataStore.getData()).willReturn(data);
    }

    @Test
    void getAddressesCoveredByStation() throws Exception {
        Firestations station1 = new Firestations(4, "112 Steppes Pl");
        Firestations station2 = new Firestations(4, "489 Manchester St");
        Firestations station3 = new Firestations(3, "26 istanbul St");
        List<Firestations> allStations = Arrays.asList(station1, station2, station3);
        given(data.getFirestations()).willReturn(allStations);
        final List<String> result = firestationsService.getAddressesCoveredByStation(4);
        assertEquals(2, result.size());
        assertTrue(result.contains("112 Steppes Pl"));
        assertTrue(result.contains("489 Manchester St"));
        assertFalse(result.contains("26 istanbul St"));

    }

    @Test
    void getStationCoveredByAddress() throws Exception {
        Firestations station1 = new Firestations(4, "112 Steppes Pl");
        Firestations station2 = new Firestations(2, "892 Downing Ct");
        List<Firestations> allStations = Arrays.asList(station1, station2);
        given(data.getFirestations()).willReturn(allStations);
        final Integer result1 = firestationsService.getStationCoveredByAddress("112 Steppes Pl");
        final Integer result2 = firestationsService.getStationCoveredByAddress("892 Downing Ct");
        assertTrue(result1 == 4);
        assertTrue(result2 == 2);
    }


    @Test
    void addFirestation() throws Exception {
        Firestations station = new Firestations(24,"26 istanbul St.");
        List<Firestations> allStations = new ArrayList<>();
        given(data.getFirestations()).willReturn(allStations);
        firestationsService.addFirestation(station);
        assertEquals(1,allStations.size());
        assertTrue(allStations.contains(station));
    }

    @Test
    void updateFirestation() throws Exception {
    }

    @Test
    void deleteFirestation() throws Exception {
    }
}