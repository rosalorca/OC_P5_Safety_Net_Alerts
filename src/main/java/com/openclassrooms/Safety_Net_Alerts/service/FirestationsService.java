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


    public void addFirestation(Firestations firestations) {
        dataStore.getData().getFirestations();
        log.info("la station a ètè bien ajouté!");
    }

    
    /**
     * 
     * @param address
     * @param updateFirestation
     * @return 
     */
    public Firestations updateFirestation(String address, Firestations updateFirestation) {
        Firestations fireStationUpdated = null;
        List<Firestations> firestations = dataStore.getData().getFirestations();
        for (Firestations firestations1 : firestations) {
            if (firestations1.getAddress().equals(updateFirestation.getAddress())) {
                firestations1.setStation(updateFirestation.getStation());
                fireStationUpdated = firestations1;
            }
        }
        return fireStationUpdated;
    }

    public boolean deleteFirestation(String address, String station) {
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
    
     public List<String> getStreets(final String firestation) {
                return dataStore.getData().getFirestations()
                .stream().filter(firestations -> firestations.getStation().equals(firestation))
                .map(Firestations::getAddress).collect(Collectors.toList());
    }
     
     /**
      * getNum : je cherche le numéro de la station qui est à cette adresse
      * @param address : une adresse 
      * @return  le numéro de la sations associé a cette adresse
      */
     public String getNum(String address) {
                 return dataStore.getData().getFirestations() // on récupère toutes les stations
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address)) // parmi toutes les stations, on sélectionne toutes celles à cette adresse
                .map(Firestations::getStation) // ici on ne garde que le numéro des stations
                .findAny() // on prend la première station qu'on trouve
                .get();
     }


}
