package com.openclassrooms.Safety_Net_Alerts.model;

public class Adresse {

    private String adresse;
    private String city;
    private int zip;

    public Adresse(String adresse, String city, int zip){
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public Adresse() {
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
        return "Adresse{" +
                "adresse='" + adresse + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                '}';
    }
}
