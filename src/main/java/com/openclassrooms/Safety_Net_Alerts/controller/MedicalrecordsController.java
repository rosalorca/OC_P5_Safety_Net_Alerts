package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping
public class MedicalrecordsController {
    private final MedicalrecordsService medicalrecordsService;


    @GetMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> getMedicalrecords() {
        return new ResponseEntity<>(medicalrecordsService.getMedicalrecords(), HttpStatus.OK);
    }

    //cette methode ajoute la personne mais si le nom et prenom sont déjà existe il compare
    @PostMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> createMedicalrecords(@RequestBody Medicalrecords newMedicalrecords) {
        boolean isCreated = medicalrecordsService.createMedicalrecords(newMedicalrecords);
        if (isCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // cette methode cherche une persoone qui existe dans la liste et modifier la personne
    @PutMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> updateMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
        boolean isUpdated = medicalrecordsService.updateMedicalrecords(medicalrecords);
        if (isUpdated) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
    /*@DeleteMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> deleteMedicalrecords(@RequestBody Medicalrecords medicalrecords) {

        return null;
    }
}


    /* List<Medicalrecords> medicalrecordsList = dataStore.getData().getMedicalrecords();
     for(Medicalrecords medicalrecord : medicalrecordsList){
          if(medicalrecord.getFirstName().equals(medicalrecords.getFirstName())){

         }
     }
     }
    private DataStore dataStore;

    @DeleteMapping(value = "/Medicalrecords")
    public void deleteMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
        List<Medicalrecords> medicalrecordsList = dataStore.getData().getMedicalrecords();
        for (Medicalrecords medicalrecords2 : medicalrecordsList) {

        }
    }
}*/
