package com.openclassrooms.Safety_Net_Alerts.controller;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PersonsWithNumberOfAdultsAndChildren {
    List<PersonMedicalRecords> persons;

    int nbAdults;
    int nbChildren;


}
