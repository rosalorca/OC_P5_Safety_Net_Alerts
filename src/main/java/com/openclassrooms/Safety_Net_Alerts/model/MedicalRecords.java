package com.openclassrooms.Safety_Net_Alerts.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MedicalRecords {
    private String firstName;
    private String lastName;
    private int birtDay;

    private Map <String, Integer> medications;
    private List<String> allergies;


}
