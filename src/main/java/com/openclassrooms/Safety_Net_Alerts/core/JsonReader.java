package com.openclassrooms.Safety_Net_Alerts.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.Safety_Net_Alerts.model.WorkData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class JsonReader {

    @Autowired
    protected DataStore dataStore;

    @Autowired
    private ObjectMapper objectMapper;


    @PostConstruct
    public void initInternalData() throws IOException {
        log.info("Loading JSON file");
        InputStream resource = new ClassPathResource("data.json").getInputStream();
        WorkData datas = objectMapper.readValue(resource, WorkData.class);
        //System.out.println(datas);
        dataStore.setData(datas);
    }
}
