package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class  FirestationsController {

    //private FirestationsService firestationsService;
    private DataStore dataStore;
    @GetMapping(value = "/Firestations")

    public List<Firestations> listFirestation() {

        return dataStore.getData().getFirestations();

    }

    @PostMapping(value = "/Firestations")
    public void addfirestation(@RequestBody Firestations firestations) {
        dataStore.getData().getFirestations().add(firestations);
    }

    @PutMapping(value = "/Firestations")
    public void updateFirestations(@RequestBody Firestations firestation) {
        List<Firestations> firestations = dataStore.getData().getFirestations();
        for (Firestations firestations1 : firestations) {
            if (firestations1.getAddress().equals(firestation.getAddress())) {
                firestations1.setStation(firestation.getStation());
            }
        }


    }

    @DeleteMapping(value = "/Firestations")
    public void deleteFirestations(@RequestBody Firestations firestations) {


    }

}
