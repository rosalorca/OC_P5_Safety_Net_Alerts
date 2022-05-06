package com.openclassrooms.Safety_Net_Alerts.controller;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FirestationAndPersonsAtAddress {

    Integer station;
    List<PersonMedicalRecords> persons;

}
