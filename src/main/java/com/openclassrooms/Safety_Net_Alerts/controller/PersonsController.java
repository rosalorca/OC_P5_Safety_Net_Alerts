package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
class PersonsController {

    private PersonsService personsService;

    @GetMapping(value = "/Persons")
    public ResponseEntity<List<Persons>> getPersons() {
        return new ResponseEntity<>(personsService.getPersons(), HttpStatus.OK);
    }


    @PostMapping(value = "/Persons")
    public ResponseEntity<Persons> addPersons() {
        this.personsService = personsService;
        return new ResponseEntity<Persons>(personsService.addPersons(),HttpStatus.CREATED );
            }

    @PutMapping(value = "/Persons")
    public ResponseEntity<Persons> updatePersons() {
        return new ResponseEntity<>(personsService.updatePersons(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/Persons")
    public ResponseEntity<Persons> deletePersons() {
        return new ResponseEntity<>(personsService.deletePersons(), HttpStatus.GONE);
    }


}
