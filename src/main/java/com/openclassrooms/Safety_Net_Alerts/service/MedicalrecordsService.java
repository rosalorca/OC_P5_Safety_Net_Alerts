package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Persons;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import com.openclassrooms.Safety_Net_Alerts.model.Medicalrecords;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MedicalrecordsService {

    @Autowired
    private DataStore dataStore;

    public List<Medicalrecords> getMedicalrecords() {
        return dataStore.getData().getMedicalrecords();
    }

    public List<Medicalrecords> getMedicalrecords(final String firstName, final String lastName) {
        return dataStore.getData().getMedicalrecords().stream().filter(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)).collect(Collectors.toList());
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

    public Medicalrecords updateMedicalrecords(String firstName, String lastName, Medicalrecords updateMedicalrecord) {
        Optional<Medicalrecords> optionalMedicalrecords = dataStore.getData().getMedicalrecords()
                .stream()
                .filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName))
                .findFirst();
        if (optionalMedicalrecords.isPresent()) {

            Medicalrecords medRec = optionalMedicalrecords.get();
            if (updateMedicalrecord.getFirstName() != null) {
                medRec.setFirstName(updateMedicalrecord.getFirstName());
            }
            if (updateMedicalrecord.getLastName() != null) {
                medRec.setLastName(updateMedicalrecord.getLastName());
            }
            if (updateMedicalrecord.getBirthdate() != null) {
                medRec.setBirthdate(updateMedicalrecord.getBirthdate());
            }
            if (updateMedicalrecord.getMedications() != null){
                medRec.setMedications(updateMedicalrecord.getMedications());
            }
            if (updateMedicalrecord.getAllergies() != null){
                medRec.setAllergies(updateMedicalrecord.getAllergies());
            }
            log.info("la personne a ètè bien modifié!");
            return medRec;

        } else {
            log.error("je n'ai pas trouvé la personne a modifier !");
            return null;
        }
    }

    public boolean deleteMedicalrecords(String firstName, String lastName) {
        Optional<Medicalrecords> optionalMedicalrecords = dataStore.getData().getMedicalrecords()
                .stream()
                .filter(mr -> mr.getFirstName().equals(firstName) && mr.getLastName().equals(lastName))
                .findFirst();
        if (optionalMedicalrecords.isPresent()) {
            System.out.println("j'ai trouvé la personne a supprimer !");
            dataStore.getData().getMedicalrecords().remove(optionalMedicalrecords.get());
            return true;
        } else {
            log.error("je n'ai pas trouvé la personne a supprimer !");
            return false;
        }
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
}