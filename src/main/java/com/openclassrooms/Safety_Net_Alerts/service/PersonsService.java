package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PersonsService {


    @Autowired
    private DataStore dataStore;

    public List<Persons> getPersons() {
        return dataStore.getData().getPersons();

    }

    public void addPersons(Persons persons) {
        dataStore.getData().getPersons().add(persons);
    }

    public void updatePersons() {
        dataStore.getData().getPersons().stream().filter(person -> person.getFirstName()
                .equals(person.getFirstName()));

    }
    public boolean deletePersons(String firstName, String lastName){
        Optional<Persons> optionalPerson = dataStore.getData().getPersons()
                .stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
        if (optionalPerson.isPresent()) {
            System.out.println("j'ai trouvé la personne a supprimer !");
            dataStore.getData().getPersons().remove(optionalPerson.get());
            return true;
        } else {
            System.err.println("je n'ai pas trouvé la personne a supprimer !");
            return false;
        }
    }

}
