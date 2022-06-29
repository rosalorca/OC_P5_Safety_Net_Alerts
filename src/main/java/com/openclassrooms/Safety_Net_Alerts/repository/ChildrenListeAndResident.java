package com.openclassrooms.Safety_Net_Alerts.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
