package com.openclassrooms.Safety_Net_Alerts.controller;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ChildrenAndMembersHome {
    List<PersonMedicalRecords> childListe;
    List<PersonMedicalRecords> memberHomeListe;
}
