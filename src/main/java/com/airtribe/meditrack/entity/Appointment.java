package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class Appointment extends MedicalEntity implements Cloneable {
    private Patient patient;
    private Doctor doctor;
    private AppointmentStatus status;
    private LocalDateTime appointmentDate;
    private String notes;

    public Appointment(String appointmentId, Patient patient, Doctor doctor, AppointmentStatus status,
            LocalDateTime appointmentDate, String notes) {
        super(appointmentId, LocalDateTime.now());
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.appointmentDate = appointmentDate;
        this.notes = notes;
    }

    public String getAppointmentId() {
        return getId();
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public AppointmentStatus getAppointmentStatus() {
        return status;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setAppointmentId(String appointmentId) {
        setId(appointmentId);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setAppointmentStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDetails() {
        return "Appointment ID: " + getId() + ", Patient: " + (patient != null ? patient.getName() : "N/A")
                + ", Doctor: " + (doctor != null ? doctor.getName() : "N/A")
                + ", Appointment Status: " + status + ", Appointment Date: " + appointmentDate
                + ", Notes: " + notes;
    }

    public void cancel() {
        if (status == AppointmentStatus.PENDING) {
            status = AppointmentStatus.CANCELLED;
        } else {
            System.out.println("Appointment is not pending");
        }
    }

    public void confirm() {
        if (status == AppointmentStatus.PENDING) {
            status = AppointmentStatus.CONFIRMED;
        } else {
            System.out.println("Appointment is not pending");
        }
    }

    @Override
    public Appointment clone() {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
