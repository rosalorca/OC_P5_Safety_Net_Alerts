package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.core.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalrecordsController {
    @Autowired
    private DataStore dataStore;

    @RequestMapping(value = "/Medicalrecords")
    public List<Medicalrecords> listMedicalrecords() {
        return dataStore.getData().getMedicalrecords();

    }

    @PostMapping(value = "/Medicalrecords")
    public void addMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
        dataStore.getData().getMedicalrecords().add(medicalrecords);
        return;
    }


    @PutMapping(value = "/Medicalrecords")
    public void updateMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
    List<Medicalrecords> medicalrecordsList = dataStore.getData().getMedicalrecords();
    for(Medicalrecords medicalrecord : medicalrecordsList){
        if(medicalrecord.getFirstName().equals(medicalrecords.getFirstName())){

        }
    }
    }

    @DeleteMapping(value = "/Medicalrecords")
    public void deleteMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
        List<Medicalrecords> medicalrecordsList = dataStore.getData().getMedicalrecords();
        for(Medicalrecords medicalrecords2: medicalrecordsList ){

        }
    }
}
