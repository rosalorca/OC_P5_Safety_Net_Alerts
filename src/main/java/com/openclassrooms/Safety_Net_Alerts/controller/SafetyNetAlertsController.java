package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;

import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@AllArgsConstructor
@RestController
public class SafetyNetAlertsController {

    @Autowired
    private DataStore dataStore;
    @Autowired
    private FirestationsService firestationsService;
    @Autowired
    private PersonsService personsService;
    @Autowired
    private MedicalrecordsService medicalrecordsService;

    @GetMapping(value = "/communityEmail")
    public List<String> communityEmail(final String city) {
        return personsService.getPersonsInCity(city).stream()
                .map(Persons::getEmail)
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert (final String firestation) {
        final List<String> addresses = firestationsService.getAddressesCoveredByStation(firestation);
        return personsService.getPersonsAtAddresses(addresses).stream()
                .map(Persons::getPhone)
                .collect(Collectors.toList());
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

    @GetMapping(value = "/fireStation")
    public PersonsWithNumberOfAdultsAndChildren fireStation(final String stationNumber) {
        final List<String> addressesCoveredByStation = firestationsService.getAddressesCoveredByStation(stationNumber);

        final PersonsWithNumberOfAdultsAndChildren result = new PersonsWithNumberOfAdultsAndChildren();
        result.setPersons(new ArrayList<>());
        dataStore.getData().getPersons()
                .stream()
                .filter(person -> addressesCoveredByStation.contains(person.getAddress()))
                .forEach(person -> {
                    final PersonMedicalRecords pmr = new PersonMedicalRecords();
                    pmr.setFirstName(person.getFirstName());
                    pmr.setLastName(person.getLastName());
                    pmr.setAddress(person.getAddress());
                    pmr.setPhone(person.getPhone());
                    result.getPersons().add(pmr);
                    final AtomicInteger age = new AtomicInteger(0);
                    dataStore.getData().getMedicalrecords()
                            .stream()
                            .filter(medicalrecord -> medicalrecord.getFirstName()
                                    .equals(pmr.getFirstName()) && medicalrecord.getLastName().equals(pmr.getLastName()))
                            .findAny()
                            .ifPresent(medicalrecord -> {
                                try {
                                    age.set(calculateAge(medicalrecord.getBirthdate()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            });

                    if (age.get() > 18) {
                        result.setNbAdults(result.getNbAdults() + 1);
                    } else {
                        result.setNbChildren(result.getNbChildren() + 1);
                    }
                });


        return result;
    }

   /* @GetMapping(value = "/childAlert")
    public void childAlert(final String address) {

        final List<PersonMedicalRecords> memberHomeListe = dataStore.getData().getPersons()
                .stream()
                .filter(inhabitant -> inhabitant.getAddress().equals(address))
                .forEach(inhabitants-> {
                    PersonMedicalRecords membres = new PersonMedicalRecords();
                    membres.setFirstName(membres.getFirstName());
                    membres.setLastName(membres.getLastName());
                    membres.setAge(membres.getAge());
                    //memberHomeListe.add(membres);

                });
                return ;
        //memberHomeListe;

}





      /*  final List<PersonMedicalRecords> memberHomeListe = new ArrayList<>();
        for (Persons inhabitant : dataStore.getData().getPersons()) {
            if (inhabitant.getAddress().equals(address)) {
                PersonMedicalRecords member = new PersonMedicalRecords();
                member.setFirstName(inhabitant.getFirstName());
                member.setLastName(inhabitant.getLastName());
                memberHomeListe.add(member);
            }
        }*/



        /*final ChildrenAndMembersHome result = new ChildrenAndMembersHome();
        result.setMemberHomeListe(memberHomeListe);

        final AtomicInteger age = new AtomicInteger();
        final List<PersonMedicalRecords> childListe = new ArrayList<>();
        for (Medicalrecords members : dataStore.getData().getMedicalrecords()) {
            if (members.getFirstName().equals(members)) {
                PersonMedicalRecords children = new PersonMedicalRecords();
                children.setFirstName(members.getFirstName());
                children.setLastName(members.getLastName());
                /*children.setAge(age.set(calculateAge(medicalrecord.getBirthdate()));
                if (19 > age) {
                    childListe.add(children);
                }

            }
*/

            }

/*
        } return null;

    }
*/

    /* @GetMapping(value = "/flood/station")
        public PersonMedicalRecords floodAlert ( final String fireStation) {
            final List<String> habitantList = dataStore.getData().getFirestations()
                    .stream()
                    .filter(station -> station.getStation().equals(fireStation))
                    .map(Firestations::getAddress)
                    .collect(Collectors.toList());
            PersonMedicalRecords habitants = new PersonMedicalRecords();
            habitants.setFirstName(habitants.getFirstName());
            habitants.setLastName(habitants.getLastName());
            habitants.setPhone(habitants.getPhone());
            // habitants.setAge(calculateAge(getBirthdate()));

            return null;
        }
    }
*/




