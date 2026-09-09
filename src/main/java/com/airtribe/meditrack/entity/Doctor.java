package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

import com.airtribe.meditrack.interfaces.Searchable;

public class Doctor extends Person implements Searchable {
    private Specialization specialization;
    private double consultationFee;
    private int experienceYears;

    public Doctor(String id, LocalDateTime createdAt, String name, int age, String phone, String email,
            Specialization specialization, double consultationFee, int experienceYears) {
        super(id, createdAt, name, age, phone, email);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.experienceYears = experienceYears;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getDetails() {
        return super.getDetails() + ", Specialization: " + specialization + ", Consultation Fee: " + consultationFee
                + ", Experience Years: " + experienceYears;
    }

    @Override
    public boolean matches(String keyword) {
        return getName().toLowerCase().contains(keyword.toLowerCase())
                || specialization.toString().toLowerCase().contains(keyword.toLowerCase())
                || getEmail().toLowerCase().contains(keyword.toLowerCase())
                || getPhone().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public String getSearchableFields() {
        return getName();
    }

    @Override
    public void printSearchResult() {
        System.out.println(getDetails());
    }
}
