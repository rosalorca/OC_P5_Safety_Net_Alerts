package com.openclassrooms.Safety_Net_Alerts.repository;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChildrenListeAndResident {
    List<PersonMedicalRecords> childListe;
    List<PersonMedicalRecords> residentListe;

}
