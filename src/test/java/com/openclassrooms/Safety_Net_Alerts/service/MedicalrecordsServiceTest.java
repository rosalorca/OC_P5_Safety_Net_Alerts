package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
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
       Medicalrecords mr = new Medicalrecords
               ("John","Boyd", "03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));
       List<Medicalrecords> allMR = Arrays.asList(mr);
       given(data.getMedicalrecords()).willReturn(allMR);
      /* final List<Medicalrecords> result = medicalrecordsService.
               getMedicalrecords
                       ("John","Boyd","03/06/1984", Collections.singletonList("aznol:350mg"), Collections.singletonList("nillacilan"));*/
    }

    @Test
    void createMedicalrecords() throws Exception {
    }

    @Test
    void updateMedicalrecords() throws Exception {
    }

    @Test
    void deleteMedicalrecords() throws Exception {
    }

    @Test
    void calculateAge() throws Exception {
    }
}