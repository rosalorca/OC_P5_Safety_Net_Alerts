package com.openclassrooms.Safety_Net_Alerts.Dao;

import com.openclassrooms.Safety_Net_Alerts.model.WorkData;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class DataStore {

    protected WorkData data;

}
