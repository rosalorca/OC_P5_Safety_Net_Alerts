package com.openclassrooms.Safety_Net_Alerts.model;

import lombok.*;

import java.util.List;
import java.util.Map;

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
