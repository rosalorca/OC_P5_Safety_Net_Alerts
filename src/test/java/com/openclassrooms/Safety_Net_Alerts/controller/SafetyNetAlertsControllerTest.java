package com.openclassrooms.Safety_Net_Alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SafetyNetAlertsController.class)
class SafetyNetAlertsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationsService firestationsService;

    @MockBean
    private PersonsService personsService;

    @MockBean
    private MedicalrecordsService medicalrecordsService;

    @Test
    void fireStation() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        listPersons.add(ozlem);
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com");
        listPersons.add(lily);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);

        Medicalrecords mrOzlem = new Medicalrecords("Ozlem", "Donder", "24/02/1988", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Ozlem"), eq("Donder"))).willReturn(mrOzlem);
        Medicalrecords mrLily = new Medicalrecords("Lily", "Cooper", "21/08/2015", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Lily"), eq("Cooper"))).willReturn(mrLily);

        mockMvc.perform(get("/fireStation?stationNumber=" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbAdults").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbChildren").value(1));

    }

    @Test
    void childAlert() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Paris", 34075, "841-874-9845", "lily@email.com");
        listPersons.add(lily);
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        listPersons.add(ozlem);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);
        Medicalrecords mrOzlem = new Medicalrecords("Ozlem", "Donder", "24/02/1988", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Ozlem"), eq("Donder"))).willReturn(mrOzlem);
        Medicalrecords mrLily = new Medicalrecords("Lily", "Cooper", "21/08/2015", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Lily"), eq("Cooper"))).willReturn(mrLily);

        mockMvc.perform(get("/childAlert?address=26%20Bosphore%20St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.childListe.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.childListe[0].firstName").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentListe.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.residentListe[0].firstName").value("Ozlem"));
    }

    @Test
    void childAlertNotExistChild() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons ozlem = new Persons
                ("Ozlem", "Donder", "26 Bosphore St", "Paris", 34075, "123-456-7890", "ozlem-paris@email.com");
        listPersons.add(ozlem);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);
        Medicalrecords mrOzlem = new Medicalrecords("Ozlem", "Donder", "24/02/1988", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Ozlem"), eq("Donder"))).willReturn(mrOzlem);
        mockMvc.perform(get("/childAlert?address=26%20Bosphore%20St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    void phoneAlert() throws Exception {
        given(firestationsService.getAddressesCoveredByStation(anyInt())).willReturn(List.of("489 Manchester St"));
        List<Persons> listPersons = new ArrayList<>();
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com");
        listPersons.add(lily);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);
        mockMvc.perform(get("/phoneAlert?firestation=" + 4)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("841-874-9845"));
    }

    @Test
    void phoneAlertNotExistStation() throws Exception {
        given(firestationsService.getFirestations()).willReturn(null);
        Firestations fireStation = new Firestations(5, "1871 Özlem St");
        mockMvc.perform(get("/phoneAlert?stationNumber=" + 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    void fire() throws Exception {
        given(firestationsService.getAddressesCoveredByStation(anyInt())).willReturn(List.of("489 Manchester St"));
        List<Persons> listPersons = new ArrayList<>();
        Persons lily = new Persons
                ("Lily", "Cooper", "489 Manchester St", "Culver", 97451, "841-874-9845", "lily@email.com");
        listPersons.add(lily);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);
        Medicalrecords mrLily = new Medicalrecords("Lily", "Cooper", "21/08/2015", null, null);
        given(medicalrecordsService.getMedicalrecords(eq("Lily"), eq("Cooper"))).willReturn(mrLily);
        mockMvc.perform(get("/fire?address=489 Manchester St")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$..firstName").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..lastName").value("Cooper"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..age").value(6));
    }

    @Test
    void floodAlert() throws Exception {
        given(firestationsService.getAddressesCoveredByStation(anyInt())).willReturn(List.of("951 LoneTree Rd"));
        List<Persons> listPersons = new ArrayList<>();
        Persons eric = new Persons
                ("Eric", "Cadigan", "951 LoneTree Rd", "Culver", 97451, "841-874-7458", "gramps@email.com");
        listPersons.add(eric);
        given(personsService.getPersonsAtAddresses(anyList())).willReturn(listPersons);
        Medicalrecords mrEric = new Medicalrecords("Eric", "Cadigan", "08/06/1945",
                Collections.singletonList("tradoxidine:400mg"), Collections.singletonList(""));
        given(medicalrecordsService.getMedicalrecords(eq("Eric"), eq("Cadigan"))).willReturn(mrEric);
        mockMvc.perform(get("/flood/stations?station=" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$..firstName").value("Eric"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..lastName").value("Cadigan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$..age").value(77));
    }

    @Test
    void personInfo() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons brian = new Persons
                ("Brian", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7458", "gramps@email.com");
        listPersons.add(brian);
        Persons kendric = new Persons
                ("Kendric", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7458", "bstel@email.com");
        listPersons.add(kendric);
        given(personsService.getPersonInfo(anyString())).willReturn(listPersons);
        Medicalrecords mrBrian = new Medicalrecords("Brian", "Stelzer", "12/06/1975",
                Collections.singletonList("ibupurin:200mg"), Collections.singletonList("nillacilan"));
        given(medicalrecordsService.getMedicalrecords(eq("Brian"), eq("Stelzer"))).willReturn(mrBrian);
        Medicalrecords mrKendric = new Medicalrecords("Kendric", "Stelzer", "03/06/2014",
                Collections.singletonList("noxidian:100mg"), Collections.singletonList(""));
        given(medicalrecordsService.getMedicalrecords(eq("Kendric"), eq("Stelzer"))).willReturn(mrKendric);
        mockMvc.perform(get("/personInfo?firstName=Brian&lastName=Stelzer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Brian"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Stelzer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(47))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Kendric"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Stelzer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(8));
    }

    @Test
    void communityEmail() throws Exception {
        List<Persons> listPersons = new ArrayList<>();
        Persons brian = new Persons
                ("Brian", "Stelzer", "947 E. Rose Dr", "Culver", 97451, "841-874-7458", "gramps@email.com");
        listPersons.add(brian);
        Persons john = new Persons
                ("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboy@email.com");
        listPersons.add(john);
        given(personsService.getPersonsInCity(anyString())).willReturn(listPersons);
        mockMvc.perform(get("/communityEmail?city=Culver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("gramps@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("jaboy@email.com"));

    }

    @Test
    void communityEmailNotExistCity() throws Exception {
        given(personsService.getPersonsInCity(anyString())).willReturn(new ArrayList<>());
        mockMvc.perform(get("/communityEmail?city=Paris")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

}