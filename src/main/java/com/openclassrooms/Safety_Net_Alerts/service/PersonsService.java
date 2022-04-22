package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PersonsService {


    @Autowired
    private DataStore dataStore;

    public List<Persons> getPersons() {
        return dataStore.getData().getPersons();

    }

    public void addPersons() {

        dataStore.getData().getPersons().add(new Persons(String ));
    }

    public void updatePersons() {
        dataStore.getData().getPersons().stream().filter(person -> person.getFirstName()
                .equals(person.getFirstName()));
    }

    public void deletePersons(@RequestBody Persons persons) {
        dataStore.getData().getPersons().remove(persons);
    }

    public Object deletePersons() {
    }
}
