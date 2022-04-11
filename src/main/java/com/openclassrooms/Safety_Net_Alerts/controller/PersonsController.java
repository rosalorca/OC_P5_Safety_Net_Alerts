package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
public class PersonsController {

   private final PersonsService personsService;
    private DataStore dataStore;
    @GetMapping(value = "/Persons")
    public List<Persons> listPersons() {

        return PersonsService.getData().getPersons();
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
