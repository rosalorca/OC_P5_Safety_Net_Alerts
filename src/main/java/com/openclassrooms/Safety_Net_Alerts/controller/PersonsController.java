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
public class PersonsController {
    private final PersonsService personsService;



    @GetMapping(value = "/Persons")
      public ResponseEntity<List<Persons>> getPersons() {
        return new ResponseEntity<>(personsService.getPersons, HttpStatus.OK);
    }

    @PostMapping(value = "/Persons")
    public void addPersons(@RequestBody Persons persons) {
        dataStore.getData().getPersons().add(persons);
    }

    @PutMapping(value = "/Persons")
    public void updatePersons(@RequestBody Persons persons) {
        dataStore.getData().getPersons().stream().filter(person -> persons.getFirstName()
                .equals(person.getFirstName()));
    }

    @DeleteMapping(value = "/Persons")
    public void deletePersons(@RequestBody Persons persons) {
        dataStore.getData().getPersons().remove(persons);
    }


}
