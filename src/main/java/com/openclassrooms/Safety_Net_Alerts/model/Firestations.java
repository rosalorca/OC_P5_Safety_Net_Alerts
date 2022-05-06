package com.openclassrooms.Safety_Net_Alerts.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter


public class  Firestations {

    private Integer station;
    private String address;



}