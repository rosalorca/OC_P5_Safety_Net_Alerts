package com.openclassrooms.Safety_Net_Alerts.controller;

import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.service.MedicalrecordsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping
@Slf4j
public class MedicalrecordsController {
    private MedicalrecordsService medicalrecordsService;

    /**
     * @return
     */
    @GetMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> getMedicalrecords() {
        return new ResponseEntity<>(medicalrecordsService.getMedicalrecords(), HttpStatus.OK);
    }

    /**
     * @param newMedicalrecords
     * @return cette methode ajoute la personne mais si le nom et prenom sont déjà existe il compare
     */

    @PostMapping(value = "/Medicalrecords")
    public ResponseEntity<List<Medicalrecords>> createMedicalrecords(@RequestBody Medicalrecords newMedicalrecords) {
        boolean isCreated = medicalrecordsService.createMedicalrecords(newMedicalrecords);
        if (isCreated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param updateMedicalrecord
     * @param firstName
     * @param lastName
     * @return cette methode cherche une persoone qui existe dans la liste et modifier la personne
     */

    @PutMapping(value = "/Medicalrecords/{firstName}/{lastName}")
    public ResponseEntity<Medicalrecords> updateMedicalrecords
            (@RequestBody Medicalrecords updateMedicalrecord, @PathVariable String firstName, @PathVariable String lastName) {
        Medicalrecords medicalrecordUpdated = medicalrecordsService.updateMedicalrecords(firstName, lastName, updateMedicalrecord);
        if (medicalrecordUpdated != null) {
            return new ResponseEntity<>(medicalrecordUpdated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(medicalrecordUpdated, HttpStatus.NOT_MODIFIED);
        }
    }

    /**
     * @param firstName
     * @param lastName
     * @return supprimer les personnes
     */
    @DeleteMapping(value = "/Medicalrecords/{firstName}/{lastName}")
    public ResponseEntity<Persons> deleteMedicalrecords(@PathVariable String firstName, @PathVariable String lastName) {
        boolean isDeleted = medicalrecordsService.deleteMedicalrecords(firstName, lastName);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.GONE);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
