package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
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

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalrecordsServiceTest {

    @MockBean
    private DataStore dataStore;

    private WorkData data;

    private MedicalrecordsService medicalrecordsService = new MedicalrecordsService();

    @BeforeEach
    void setup() {
        medicalrecordsService.setDataStore(dataStore);
        data = Mockito.mock(WorkData.class);
        given(dataStore.getData()).willReturn(data);
    }

    @Test
    void testGetMedicalrecords() throws Exception {

        Medicalrecords mr1 = new Medicalrecords
                ("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
       // Medicalrecords mr2 = new Medicalrecords
           //     ("Ozlem", "Donder", "24/02/1984", null, Collections.singletonList(null));
        List<Medicalrecords> allMR = Arrays.asList(mr1);
        given(data.getMedicalrecords()).willReturn(allMR);
        Medicalrecords result1 = medicalrecordsService.getMedicalrecords(mr1.getFirstName(), mr1.getLastName());
        assertEquals("John", result1.getFirstName());
        assertEquals("Boyd", result1.getLastName());
        assertEquals("aznol:350mg", result1.getMedications().get(0));
        assertNull(medicalrecordsService.getMedicalrecords("Ozlem", "Donder"));

    }

    @Test
    void createMedicalrecords() throws Exception {
        Medicalrecords mr = new Medicalrecords("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
        List<Medicalrecords> allMR = Arrays.asList(mr);
        given(data.getMedicalrecords()).willReturn(allMR);
        medicalrecordsService.createMedicalrecords(mr);
        assertEquals(1, allMR.size());
        assertTrue(allMR.contains(mr));
    }

    @Test
    void updateMedicalrecords() throws Exception {
        List<Medicalrecords> allMR = new ArrayList<>();
        given(data.getMedicalrecords()).willReturn(allMR);
        Medicalrecords mr = new Medicalrecords("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
        Medicalrecords updateMr = new Medicalrecords("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:100mg"), Collections.singletonList("pollen"));
        allMR.add(mr);
        Medicalrecords result = medicalrecordsService.updateMedicalrecords("John", "Boyd", updateMr);
        assertNotNull(result);
        assertEquals("aznol:100mg", result.getMedications().get(0));
        //assertTrue(result.getMedications().contains("aznol:100mg"));
        assertEquals("pollen", result.getAllergies().get(0));

    }

    @Test
    void updateMedicalrecordsNotExist() throws Exception {
        List<Medicalrecords> allMR = new ArrayList<>();
        given(data.getMedicalrecords()).willReturn(allMR);
        Medicalrecords mr = new Medicalrecords("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
        Medicalrecords updateMr = new Medicalrecords("Ozlem", "Donder", "24/02/1984", Collections.singletonList("aznol:100mg"), Collections.singletonList("pollen"));
        allMR.add(mr);
        assertNull(medicalrecordsService.updateMedicalrecords("Ozlem", "Donder", updateMr));
    }

    @Test
    void deleteMedicalrecords() throws Exception {
        List<Medicalrecords> allMR = new ArrayList<>();
        given(data.getMedicalrecords()).willReturn(allMR);
        Medicalrecords mr = new Medicalrecords("Ozlem", "Donder", "24/02/1984", Collections.singletonList("aznol:100mg"), Collections.singletonList("pollen"));
        allMR.add(mr);
        assertEquals(1, allMR.size());
        assertTrue(allMR.contains(mr));
        assertTrue(medicalrecordsService.deleteMedicalrecords("Ozlem", "Donder"));
        assertFalse(allMR.contains(mr));
    }

    @Test
    void deleteMedicalrecordsNotExit() throws Exception {
        List<Medicalrecords> allMR = new ArrayList<>();
        given(data.getMedicalrecords()).willReturn(allMR);
        Medicalrecords mr = new Medicalrecords("John", "Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
        allMR.add(mr);
        assertEquals(1, allMR.size());
        assertTrue(allMR.contains(mr));
        assertFalse(medicalrecordsService.deleteMedicalrecords("Ozlem", "Donder"));
        assertTrue(allMR.contains(mr));
    }

    @Test
    void calculateAge() throws Exception {
        int result1 = MedicalrecordsService.calculateAge("24/02/1988");
        assertEquals(34, result1);
        int result2 = MedicalrecordsService.calculateAge("24/02/2022");
        assertEquals(0, result2);
        int result3 = MedicalrecordsService.calculateAge("24/11/1986");
        assertEquals(35, result3);
        assertThrows(IllegalArgumentException.class, () -> {
            MedicalrecordsService.calculateAge("01/01/2024");
        });
    }
}