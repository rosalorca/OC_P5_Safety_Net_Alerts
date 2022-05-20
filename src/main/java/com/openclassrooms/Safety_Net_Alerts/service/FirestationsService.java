package com.openclassrooms.Safety_Net_Alerts.service;

import com.openclassrooms.Safety_Net_Alerts.model.Firestations;
import com.openclassrooms.Safety_Net_Alerts.repository.DataStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FirestationsService {

    @Autowired
    private DataStore dataStore;

    public List<Firestations> getFirestations() {

        return dataStore.getData().getFirestations();
    }
    // for le request fire station that including firs name, last name, addresses,telephone number
    // and calculate how many child or adult live hear
    public List<String> getAddressesCoveredByStation(final Integer station) {
        return dataStore.getData().getFirestations().stream()
                .filter(firestations -> firestations.getStation().equals(station))
                .map(Firestations::getAddress)
                .collect(Collectors.toList());
    }
    // for request fire that including firs name, last name, telephone number
    public Integer getStationCoveredByAddress(final String address) {
        return dataStore.getData().getFirestations()
                .stream().filter(firestations -> firestations.getAddress().equals(address))
                .map(Firestations::getStation).findAny().get();
    }
    public List<Firestations> getStation (final List<Integer> station){
        return dataStore.getData().getFirestations().stream()
              .filter(f->f.getStation().equals(station))
              .collect(Collectors.toList());

    }

    // the request for add, update and delete fire stations
    public void addFirestation(Firestations firestations) {
        dataStore.getData().getFirestations().add(firestations);
        log.info("la station a ètè bien ajouté!");
    }

    public Firestations updateFirestation(String address, Firestations updateFirestation) {
        Firestations fireStationUpdated = null;
        List<Firestations> firestations = dataStore.getData().getFirestations();
        for (Firestations firestations1 : firestations) {
            if (firestations1.getAddress().equals(address)) {
                firestations1.setStation(updateFirestation.getStation());
                fireStationUpdated = firestations1;
            }
        }
        return fireStationUpdated;
    }

    public boolean deleteFirestation(String address, Integer station) {
        Optional<Firestations> optionalFirestation = dataStore.getData().getFirestations()
                .stream()
                .filter(fs -> fs.getStation().equals(station) && fs.getAddress().equals(address)).findFirst();
        if (optionalFirestation.isPresent()) {
            System.out.println("j'ai trouvé la station ou l'adresse a supprimer !");
            dataStore.getData().getPersons().remove(optionalFirestation.get());
            return true;
        } else {
            log.error("je n'ai pas trouvé la station ou l'adresse a supprimer !");
            return false;
        }
    }



}
