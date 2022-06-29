package com.openclassrooms.Safety_Net_Alerts.model;

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
public class Medicalrecords {
    private String firstName;
    private String lastName;
    private String birthdate;

    private List<String> medications;
    private List<String> allergies;


}
