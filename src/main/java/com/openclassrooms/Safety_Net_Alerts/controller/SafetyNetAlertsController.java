package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.ChildrenListeAndResident;
import com.openclassrooms.Safety_Net_Alerts.repository.FirestationAndPersonsAtAddress;
import com.openclassrooms.Safety_Net_Alerts.repository.PersonMedicalRecords;
import com.openclassrooms.Safety_Net_Alerts.repository.PersonsWithNumberOfAdultsAndChildren;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SafetyNetAlertsController {

    @Autowired
    private FirestationsService firestationsService;
    @Autowired
    private PersonsService personsService;
    @Autowired
    private MedicalrecordsService medicalrecordsService;

    /**
     * @param stationNumber
     * @return retourner une liste persons par rapport au numéro de caserne et compter les enfants et les adults
     */
    @GetMapping(value = "/fireStation")
    public PersonsWithNumberOfAdultsAndChildren fireStation(final Integer stationNumber) {
        final PersonsWithNumberOfAdultsAndChildren result = new PersonsWithNumberOfAdultsAndChildren();
        final List<PersonMedicalRecords> pmrs = new ArrayList<>();
        result.setPersons(pmrs);
        final List<String> streets = firestationsService.getAddressesCoveredByStation(stationNumber);
        final List<Persons> persons = personsService.getPersonsAtAddresses(streets);
        persons.forEach(p -> {
            final PersonMedicalRecords pmr = new PersonMedicalRecords();
            pmr.setFirstName(p.getFirstName());
            pmr.setLastName(p.getLastName());
            pmr.setPhone(p.getPhone());
            pmr.setAddress(p.getAddress());
            final Medicalrecords record = medicalrecordsService.getMedicalrecords(p.getFirstName(), p.getLastName());
            try {
                int age = MedicalrecordsService.calculateAge(record.getBirthdate());
                if (age >= 18) {
                    result.setNbAdults(result.getNbAdults() + 1);
                } else {
                    result.setNbChildren(result.getNbChildren() + 1);
                }
            } catch (final ParseException e) {
                log.error("", e);
            }
            pmrs.add(pmr);

        });

        return result;
    }

    /**
     * @param address
     * @return retourner une liste des enfants par rapport à l'adresse
     */
    @GetMapping(value = "/childAlert")
    public ChildrenListeAndResident childAlert(final String address) {
        final ChildrenListeAndResident result = new ChildrenListeAndResident();
        final List<PersonMedicalRecords> pmrsChildren = new ArrayList<>();
        final List<PersonMedicalRecords> pmrsResidents = new ArrayList<>();
        result.setChildListe(pmrsChildren);
        result.setResidentListe(pmrsResidents);
        final List<Persons> resident = personsService.getPersonsAtAddresses(Collections.singletonList(address));
        resident.forEach(r -> {
            final PersonMedicalRecords pmr = new PersonMedicalRecords();
            pmr.setFirstName(r.getFirstName());
            pmr.setLastName(r.getLastName());
            final Medicalrecords record = medicalrecordsService.getMedicalrecords(r.getFirstName(), r.getLastName());
            try {
                int age = MedicalrecordsService.calculateAge(record.getBirthdate());
                pmr.setAge(age);
                if (age < 18) {
                    pmrsChildren.add(pmr);

                } else {
                    pmrsResidents.add(pmr);
                }
            } catch (final ParseException e) {
                log.error("", e);
            }
        });

        if (pmrsChildren.isEmpty()) {
            log.info("There isn't a child!");
            return null;
        } else {
            return result;
        }

    }
    /**
     * @param firestation
     * @return retourner les numéros de téléphone par rapport à la station
     */
    @GetMapping(value = "/phoneAlert")
    public List<String> phoneAlert(final Integer firestation) {
        final List<String> addresses = firestationsService.getAddressesCoveredByStation(firestation);

        if (!addresses.isEmpty()) {
            return personsService.getPersonsAtAddresses(addresses).stream()
                    .map(Persons::getPhone)
                    .collect(Collectors.toList());
        } else {
            log.error("je n'ai pas trouvé la station!");
            return null;
        }
    }

    /**
     * @param address
     * @return retourner une liste persons par rapport à l'adresse
     */
    @GetMapping(value = "/fire")
    public FirestationAndPersonsAtAddress fire(final String address) {
        final FirestationAndPersonsAtAddress result = new FirestationAndPersonsAtAddress();
        final List<PersonMedicalRecords> pmrs = new ArrayList<>();
        result.setPersons(pmrs);

        final Integer station = firestationsService.getStationCoveredByAddress(address);
        result.setStation(station);
        final List<Persons> habitants = personsService.getPersonsAtAddresses(Collections.singletonList(address));
        habitants.forEach(p -> {

            final PersonMedicalRecords pmr = new PersonMedicalRecords();
            pmr.setFirstName(p.getFirstName());
            pmr.setLastName(p.getLastName());
            pmr.setPhone(p.getPhone());
            final Medicalrecords record = medicalrecordsService.getMedicalrecords(p.getFirstName(), p.getLastName());
            if (record != null) {
                pmr.setAllergies(record.getAllergies());
                pmr.setMedications(record.getMedications());
                try {
                    pmr.setAge(MedicalrecordsService.calculateAge(record.getBirthdate()));
                } catch (final ParseException e) {
                    log.error("", e);
                }
            }
            pmrs.add(pmr);
        });
        return result;
    }
    /**
     * @param station
     * @return retourner une liste persons par rapport à la station
     */
    @GetMapping(value = "/flood/stations")
    public FirestationAndPersonsAtAddress floodAlert (final Integer station) {
        final FirestationAndPersonsAtAddress result = new FirestationAndPersonsAtAddress();
        result.setStation(station);
        final List<PersonMedicalRecords> pmrs = new ArrayList<>();
        result.setPersons(pmrs);

        final List<String> address = firestationsService.getAddressesCoveredByStation(station);

        final List<Persons> habitants = personsService.getPersonsAtAddresses(address);
        habitants.forEach(p -> {

            final PersonMedicalRecords pmr = new PersonMedicalRecords();
            pmr.setFirstName(p.getFirstName());
            pmr.setLastName(p.getLastName());
            pmr.setPhone(p.getPhone());
            final Medicalrecords record = medicalrecordsService.getMedicalrecords(p.getFirstName(), p.getLastName());
            if (record != null) {
                pmr.setAllergies(record.getAllergies());
                pmr.setMedications(record.getMedications());
                try {
                    pmr.setAge(MedicalrecordsService.calculateAge(record.getBirthdate()));
                } catch (final ParseException e) {
                    log.error("", e);
                }
            }
            pmrs.add(pmr);
        });
        return result;
    }
    /**
     * @param firstName
     * @param lastName
     * @return retourner une liste persons par rapport au nom et au prénom
     */
    @GetMapping(value = "/personInfo")
    public List<PersonMedicalRecords> personInfo(final String firstName, final String lastName) {
        final List<PersonMedicalRecords> result = new ArrayList<>();
        personsService.getPersonInfo(lastName).forEach(person -> {
            final PersonMedicalRecords pmr = new PersonMedicalRecords();
            pmr.setFirstName(person.getFirstName());
            pmr.setLastName(person.getLastName());
            pmr.setAddress(person.getAddress());
            pmr.setEmail(person.getEmail());
            final Medicalrecords record = medicalrecordsService.getMedicalrecords(person.getFirstName(), person.getLastName());
            if (record != null) {
                pmr.setAllergies(record.getAllergies());
                pmr.setMedications(record.getMedications());
                try {
                    pmr.setAge(MedicalrecordsService.calculateAge(record.getBirthdate()));
                } catch (final ParseException e) {
                    log.error("", e);
                }
            }
            result.add(pmr);
        });
        return result;
    }

    /**
     * @param city
     * @return récuperer les addresses mails par rapport à la ville
     */
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<List<String>> communityEmail(final String city) {
        return new ResponseEntity<>(personsService.getPersonsInCity(city).stream()
                .map(Persons::getEmail)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

}




