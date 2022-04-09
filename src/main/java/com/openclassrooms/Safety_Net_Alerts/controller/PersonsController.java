package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController {

    @Autowired
    private DataStore dataStore;

    @GetMapping(value = "/Persons")
    public List<Persons> listPersons() {
        return dataStore.getData().getPersons();
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
