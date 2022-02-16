package com.openclassrooms.Safety_Net_Alerts.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PersonMedicalRecords {

    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

}
