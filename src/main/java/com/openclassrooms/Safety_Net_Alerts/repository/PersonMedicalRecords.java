package com.openclassrooms.Safety_Net_Alerts.repository;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer age;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> medications;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> allergies;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer station;
}
