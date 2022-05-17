package com.openclassrooms.Safety_Net_Alerts.repository;

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
