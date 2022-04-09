package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.Dao.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalrecordsService {

    @Autowired
    private DataStore dataStore;

    public List<Medicalrecords> getMedicalrecords() {
        return dataStore.getData().getMedicalrecords();
    }

    public boolean createMedicalrecords(Medicalrecords medicalrecord) {
        boolean alreadyExists = dataStore.getData().getMedicalrecords()
                .stream()
                .anyMatch(m -> m.getFirstName().equals(medicalrecord.getFirstName()) && m.getLastName().equals(medicalrecord.getLastName()));
        if (alreadyExists) {
            return false;
        } else {
            return dataStore.getData().getMedicalrecords().add(medicalrecord);
        }
    }

    public boolean updateMedicalrecords(Medicalrecords medicalrecords) {
        boolean alreadyExists = dataStore.getData().getMedicalrecords()
                .stream()
                .anyMatch(m -> m.getFirstName()
                        .equals(medicalrecords.getFirstName()) && m.getLastName().equals(medicalrecords.getLastName()));
        if (alreadyExists) {
            return true;
        } else {
            return false;
        }

    }
}