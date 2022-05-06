package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import com.openclassrooms.Safety_Net_Alerts.service.PersonsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class FirestationsController {

    private FirestationsService firestationsService;
    private PersonsService personsService;
    private MedicalrecordsService medicalrecordsService;

    //renvoie la liste de toutes les stations
    @GetMapping(value = "/Firestations")
    public ResponseEntity<List<Firestations>> getFirestations() {
        return new ResponseEntity<>(firestationsService.getFirestations(), HttpStatus.OK);

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

    @GetMapping(value = "/fire")
    public FirestationAndPersonsAtAddress fire(final String address) {
        final Integer station = firestationsService.getStation(address);

        return firestationsService.getPersonListe(station).stream()
                .map(Persons::getFirstName)
                .collect(Collectors.toList());
    }



    // ajoute une adresse dans la liste
    @PostMapping(value = "/Firestations")

    public ResponseEntity<Firestations> addFirestation(@RequestBody Firestations newFirestation) {
        firestationsService.addFirestation(newFirestation);
        return new ResponseEntity<Firestations>(newFirestation, HttpStatus.CREATED);
    }

    // modifier les adresses
    @PutMapping(value = "/Firestations/{address}")
    public ResponseEntity<Firestations> updateFirestation(@RequestBody Firestations updateFirestation, @PathVariable String address) {
        Firestations firestationUpdated = firestationsService.updateFirestation(address, updateFirestation);
        if (firestationUpdated != null) {
            return new ResponseEntity<>(firestationUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(firestationUpdated, HttpStatus.NOT_MODIFIED);
        }
    }
    //supprimer une adresse ou une station
    @DeleteMapping(value = "/Firestations/{address}/{station}")
    public ResponseEntity<Firestations> deleteFirestation (@PathVariable String address, @PathVariable Integer station) {
        boolean isDeleted = firestationsService.deleteFirestation(address,station);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
