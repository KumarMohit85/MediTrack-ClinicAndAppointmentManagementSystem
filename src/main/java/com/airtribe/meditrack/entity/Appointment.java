package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Appointment implements Cloneable {
    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private AppointmentStatus appointmentStatus;
    private LocalDateTime appointmentDate;
    private String notes;

    Appointment(String appointmentId, Patient patient, Doctor doctor, AppointmentStatus appointmentStatus,
            LocalDateTime appointmentDate, String notes) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentStatus = appointmentStatus;
        this.appointmentDate = appointmentDate;
        this.notes = notes;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDetails() {
        return "Appointment ID: " + appointmentId + ", Patient: " + patient.getName() + ", Doctor: " + doctor.getName()
                + ", Appointment Status: " + appointmentStatus + ", Appointment Date: " + appointmentDate + ", Notes: "
                + notes;
    }

    public void cancel() {
        if (appointmentStatus == AppointmentStatus.PENDING) {
            appointmentStatus = AppointmentStatus.CANCELLED;
        } else {
            System.out.println("Appointment is not pending");
        }
    }

    public void confirm() {
        if (appointmentStatus == AppointmentStatus.PENDING) {
            appointmentStatus = AppointmentStatus.CONFIRMED;
        } else {
            System.out.println("Appointment is not pending");
        }
    }

    public Appointment clone() {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }

    }

}
