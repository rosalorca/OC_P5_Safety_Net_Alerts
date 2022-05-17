package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.repository.PersonMedicalRecords;
import com.openclassrooms.Safety_Net_Alerts.repository.PersonsWithNumberOfAdultsAndChildren;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService.calculateAge;

@AllArgsConstructor
@RestController
@Slf4j
public class SafetyNetAlertsController {

    @Autowired
    private DataStore dataStore;
    @Autowired
    private FirestationsService firestationsService;
    @Autowired
    private PersonsService personsService;
    @Autowired
    private MedicalrecordsService medicalrecordsService;

    // récuperer les addresses mails par rapport à la ville
    @GetMapping(value = "/communityEmail")
    public ResponseEntity<List<String>> communityEmail(final String city) {
        return new ResponseEntity<>(personsService.getPersonsInCity(city).stream()
                .map(Persons::getEmail)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

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

    @GetMapping(value = "/personInfo") // personne service
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


    @GetMapping(value = "/fireStation") // firestation service
    public PersonsWithNumberOfAdultsAndChildren fireStation(final Integer stationNumber) {
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

}


