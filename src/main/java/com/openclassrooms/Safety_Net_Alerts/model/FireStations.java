package com.openclassrooms.Safety_Net_Alerts.model;

public class FireStations {
    private int station;
    private Adresse adresse;

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
        adresse= new Adresse();
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public FireStations() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return "FireStations{" +
                "station=" + station +
                ", adresse=" + adresse +
                '}';
    }
}