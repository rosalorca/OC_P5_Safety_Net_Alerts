package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api")
@RestController
@Slf4j
class PersonsController {

    private PersonsService personsService;

    // renvoie la liste de toutes les personnes
    @GetMapping(value = "/Persons")
    public ResponseEntity<List<Persons>> getPersons() {
        return new ResponseEntity<>(personsService.getPersons(), HttpStatus.OK);
    }

    // ajoute une personne dans la liste
    @PostMapping(value = "/Persons")
    public ResponseEntity<Persons> addPersons(@RequestBody Persons newPerson) {
        personsService.addPersons(newPerson);
        return new ResponseEntity<Persons>(newPerson, HttpStatus.CREATED);
    }

    // modifier les informations des personnes
    @PutMapping(value = "/Persons/{firstName}/{lastName}")
    public ResponseEntity<Persons> updatePersons(@RequestBody Persons updatePerson, @PathVariable String firstName, @PathVariable String lastName) {
        Persons personUpdated = personsService.updatePersons(firstName, lastName, updatePerson);
        if (personUpdated != null) {
            return new ResponseEntity<>(personUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(personUpdated, HttpStatus.NOT_MODIFIED);
        }
    }

    //supprimer les personnes
    @DeleteMapping(value = "/Persons/{firstName}/{lastName}")
    public ResponseEntity<Persons> deletePersons(@PathVariable String firstName, @PathVariable String lastName) {
        boolean isDeleted = personsService.deletePersons(firstName, lastName);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
