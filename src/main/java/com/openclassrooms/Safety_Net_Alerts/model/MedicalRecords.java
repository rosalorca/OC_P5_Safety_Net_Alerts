package com.openclassrooms.Safety_Net_Alerts.model;

import java.util.List;
import java.util.Map;

public class MedicalRecords {
    private int birtDay;
    private Map <String, Integer> medications;
    private List<String> allergies;

        public MedicalRecords(int birtday)

    public int getBirtDay() {
        return birtDay;
    }

    public void setBirtDay(int birtDay) {
        this.birtDay = birtDay;
    }

    public Map<String, Integer> getMedications() {
        return medications;
    }

    public void setMedications(Map<String, Integer> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public MedicalRecords() {
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
        return "MedicalRecords{" +
                "birtDay=" + birtDay +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
