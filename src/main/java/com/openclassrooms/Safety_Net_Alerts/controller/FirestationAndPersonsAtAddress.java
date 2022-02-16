package com.openclassrooms.Safety_Net_Alerts.controller;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FirestationAndPersonsAtAddress {

    String station;
    List<PersonMedicalRecords> persons;

}
