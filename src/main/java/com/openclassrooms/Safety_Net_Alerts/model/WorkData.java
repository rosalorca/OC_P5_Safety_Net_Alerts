package com.openclassrooms.Safety_Net_Alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkData {

    List<Persons> persons;
    List<FireStations> fireStations;
    List<MedicalRecords> medicalRecords;

}
