package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class FirestationsController {

    private FirestationsService firestationsService;

    /**
     * @return renvoie la liste de toutes les stations
     */
    @GetMapping(value = "/Firestations")
    public ResponseEntity<List<Firestations>> getFirestations() {
        return new ResponseEntity<>(firestationsService.getFirestations(), HttpStatus.OK);

    }

    /**
     * @param newFirestation
     * @return ajoute une adresse dans la liste
     */
    @PostMapping(value = "/Firestations")

    public ResponseEntity<Firestations> addFirestation(@RequestBody Firestations newFirestation) {
        firestationsService.addFirestation(newFirestation);
        return new ResponseEntity<Firestations>(newFirestation, HttpStatus.CREATED);
    }

    /**
     * @param updateFirestation
     * @param address
     * @return modifier les adresses
     */
    @PutMapping(value = "/Firestations/{address}")
    public ResponseEntity<Firestations> updateFirestation(@RequestBody Firestations updateFirestation, @PathVariable String address) {
        Firestations firestationUpdated = firestationsService.updateFirestation(address, updateFirestation);
        if (firestationUpdated != null) {
            return new ResponseEntity<>(firestationUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(firestationUpdated, HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * @param address
     * @param station
     * @return supprimer une adresse ou une station
     */
    @DeleteMapping(value = "/Firestations/{address}/{station}")
    public ResponseEntity<Firestations> deleteFirestation(@PathVariable String address, @PathVariable Integer station) {
        boolean isDeleted = firestationsService.deleteFirestation(address, station);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }


}
