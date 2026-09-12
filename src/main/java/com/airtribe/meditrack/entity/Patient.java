package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.lang.Cloneable;
import com.airtribe.meditrack.interfaces.Searchable;

public class Patient extends Person implements Searchable, Cloneable {
    private List<String> medicalHistory;
    private String emergencyContact;
    private String bloodGroup;

    public Patient(String id, LocalDateTime createdAt, String name, int age, String phone, String email,
            List<String> medicalHistory, String emergencyContact, String bloodGroup) {
        super(id, createdAt, name, age, phone, email);
        this.medicalHistory = medicalHistory;
        this.emergencyContact = emergencyContact;
        this.bloodGroup = bloodGroup;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDetails() {
        return super.getDetails() + ", Medical History: " + medicalHistory + ", Emergency Contact: " + emergencyContact
                + ", Blood Group: " + bloodGroup;
    }

    @Override
    public boolean matches(String keyword) {
        return getName().toLowerCase().contains(keyword.toLowerCase())
                || getEmail().toLowerCase().contains(keyword.toLowerCase())
                || getPhone().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String getSearchableFields() {
        return getName();
    }

    @Override
    public void printSearchResult() {
        System.out.println(getAge());
    }

    @Override
    public Patient clone() {
        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

}
