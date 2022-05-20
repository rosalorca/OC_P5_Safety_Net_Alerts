package com.openclassrooms.Safety_Net_Alerts.repository;

import com.openclassrooms.Safety_Net_Alerts.service.FirestationsService;
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
    List<PersonMedicalRecords> stations;

}
