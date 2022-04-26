package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
class PersonsController {

    private PersonsService personsService;

    // renvoie la liste de toutes les personnes
    @GetMapping(value = "/Persons")
    public ResponseEntity<List<Persons>> getPersons() {
        return new ResponseEntity<>(personsService.getPersons(), HttpStatus.OK);
    }


    @PostMapping(value = "/Persons")
    public ResponseEntity<Persons> addPersons(@RequestBody Persons newPerson) {
        personsService.addPersons(newPerson);
        return new ResponseEntity<Persons>(newPerson, HttpStatus.CREATED );
    }

    @PutMapping(value = "/Persons/{firstName}/{lastName}")
    public ResponseEntity<Persons> updatePersons(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Persons updatePerson) {
        personsService.updatePersons();
        return new ResponseEntity<Persons>(updatePerson, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/Persons/{firstName}/{lastName}")
    public ResponseEntity<Persons> deletePersons(@PathVariable String firstName,@PathVariable String lastName) {
        boolean isDeleted = personsService.deletePersons(firstName, lastName);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
