package com.openclassrooms.Safety_Net_Alerts.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class JsonReader {

    @Autowired
    protected DataStore dataStore;

    @PostConstruct
    public void initInternalData() {
        log.info("Loading JSON file");
        // TODO mettre les données du fichier json dans le data store
        //dataStore.setData(JSON_CONTENT);
    }
}
