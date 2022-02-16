package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.core.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SafetyNetAlertsController {

    @Autowired
    private DataStore dataStore;

    @GetMapping(value = "/communityEmail")
    // Je peux accéder les mails des habitants
    public List<String> communityEmail(final String city) {
        return dataStore.getData().getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .map(Persons::getEmail)
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert(final String firestation) {
        // je cherche les adresses des stations qui ont ce numéro
        List<String> streets = dataStore.getData().getFirestations()
                .stream().filter(firestations -> firestations.getStation().equals(firestation))
                .map(Firestations::getAddress).collect(Collectors.toList());
        // je cherche les personnes qui habitent à ces adresses
        return dataStore.getData().getPersons().stream()
                .filter(persons -> streets.contains(persons.getAddress()))
                .map(Persons::getPhone).collect(Collectors.toList());
    }

    @GetMapping(value = "/fire")
    public FirestationAndPersonsAtAddress fire(final String address) {
        // je cherche le numéro de la station qui est à cette adresse
        String station = dataStore.getData().getFirestations() // on récupère toutes les stations
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address)) // parmi toutes les stations, on sélectionne toutes celles à cette adresse
                .map(Firestations::getStation) // ici on ne garde que le numéro des stations
                .findAny() // on prend la première station qu'on trouve
                .get();

        FirestationAndPersonsAtAddress result = new FirestationAndPersonsAtAddress();
        result.setStation(station);
        List<PersonMedicalRecords> personMedicalRecords = new ArrayList<>();
        dataStore.getData().getPersons().stream()
                .filter(persons -> persons.getAddress().equals(address))
                .forEach(persons -> {
                    final PersonMedicalRecords person = new PersonMedicalRecords();
                    person.setFirstName(persons.getFirstName());
                    person.setLastName(persons.getLastName());
                    person.setPhone(persons.getPhone());
                    dataStore.getData().getMedicalrecords().stream()
                            .filter(medicalrecord -> medicalrecord.getFirstName()
                                    .equals(persons.getFirstName()) && medicalrecord.getLastName().equals(persons.getLastName()))
                            .findAny()
                            .ifPresent(medicalrecord -> {
                                person.setMedications(medicalrecord.getMedications());
                                person.setAllergies(medicalrecord.getAllergies());
                                try {
                                    person.setAge(calculateAge(medicalrecord.getBirthdate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            });
                    personMedicalRecords.add(person);
                });
        result.setPersons(personMedicalRecords);
        return result;
    }

    public static int calculateAge(String strBirthdate) throws ParseException {

        Date dateBirth = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy"); // pour lire la date string
        dateBirth = simpleDateFormat.parse(strBirthdate);// pour convertir de string à date
        Calendar calendarBirth = Calendar.getInstance(); // pour lire la date actuelle
        calendarBirth.setTime(dateBirth); // pour mettre la date à la valeur de la date de naissance
        int birthDay = calendarBirth.get(Calendar.DAY_OF_MONTH);
        int birthMonth = calendarBirth.get(Calendar.MONTH);
        int birthYear = calendarBirth.get(Calendar.YEAR);
        Calendar calendarActual = Calendar.getInstance(); // pour lire la date actuelle

        int actualDay = calendarActual.get(Calendar.DAY_OF_MONTH);
        int actualMonth = calendarActual.get(Calendar.MONTH);
        int actualYear = calendarActual.get(Calendar.YEAR);
        int age = actualYear - birthYear;
        if (age < 0) {
            throw new IllegalArgumentException("Birthdate is in the future");
        }
        if ((birthMonth > actualMonth) || (birthMonth == actualMonth) && (birthDay > actualDay)) {
            --age;

        }
        return age;
    }

    @GetMapping(value = "/personInfo")
    public PersonMedicalRecords personInfo(final String firstName, String lastName) {

        final PersonMedicalRecords person = new PersonMedicalRecords();
        person.setFirstName(firstName);
        dataStore.getData().getPersons().stream()
                .filter(persons -> persons.getFirstName().equals(firstName) && persons.getLastName().equals(lastName))
                .forEach(persons -> {
                    person.setFirstName(persons.getFirstName());
                    person.setLastName(persons.getLastName());
                    person.setPhone(persons.getPhone());
                    dataStore.getData().getMedicalrecords().stream()
                            .filter(medicalrecord -> medicalrecord.getFirstName()
                                    .equals(persons.getFirstName()) && medicalrecord.getLastName().equals(persons.getLastName()))
                            .findAny()
                            .ifPresent(medicalrecord -> {
                                person.setMedications(medicalrecord.getMedications());
                                person.setAllergies(medicalrecord.getAllergies());
                                try {
                                    person.setAge(calculateAge(medicalrecord.getBirthdate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            });
                });
        person.setLastName(lastName);
        return person;


    }
    //@GetMapping(value = "/fireStation")
    //public






   /* @GetMapping(value = "/childAlert")
    @GetMapping(value = "/flood/stations")*/
}

