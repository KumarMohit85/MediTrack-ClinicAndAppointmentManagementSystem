package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;

public class Validator {

    private Validator() {
    }

    public static void validatePatient(Patient patient) {
        if (patient == null) {
            throw new InvalidDataException("Patient cannot be null");
        }
        if (patient.getId() == null || patient.getId().isBlank()) {
            throw new InvalidDataException("Patient ID is required");
        }
        if (!validateName(patient.getName())) {
            throw new InvalidDataException("Invalid patient name: " + patient.getName());
        }
        if (!validateAge(patient.getAge())) {
            throw new InvalidDataException("Invalid patient age: " + patient.getAge());
        }
        if (!validatePhone(patient.getPhone())) {
            throw new InvalidDataException("Invalid patient phone: " + patient.getPhone());
        }
        if (!validateEmail(patient.getEmail())) {
            throw new InvalidDataException("Invalid patient email: " + patient.getEmail());
        }
        if (patient.getEmergencyContact() == null || patient.getEmergencyContact().isBlank()) {
            throw new InvalidDataException("Patient emergency contact is required");
        }
        if (!validatePhone(patient.getEmergencyContact())) {
            throw new InvalidDataException("Invalid emergency contact phone: " + patient.getEmergencyContact());
        }
        if (patient.getBloodGroup() == null || patient.getBloodGroup().isBlank()) {
            throw new InvalidDataException("Patient blood group is required");
        }
    }

    public static void validateDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new InvalidDataException("Doctor cannot be null");
        }
        if (doctor.getId() == null || doctor.getId().isBlank()) {
            throw new InvalidDataException("Doctor ID is required");
        }
        if (!validateName(doctor.getName())) {
            throw new InvalidDataException("Invalid doctor name: " + doctor.getName());
        }
        if (!validateAge(doctor.getAge())) {
            throw new InvalidDataException("Invalid doctor age: " + doctor.getAge());
        }
        if (!validatePhone(doctor.getPhone())) {
            throw new InvalidDataException("Invalid doctor phone: " + doctor.getPhone());
        }
        if (!validateEmail(doctor.getEmail())) {
            throw new InvalidDataException("Invalid doctor email: " + doctor.getEmail());
        }
        if (doctor.getSpecialization() == null) {
            throw new InvalidDataException("Doctor specialization is required");
        }
        if (!validateFee(doctor.getConsultationFee())) {
            throw new InvalidDataException("Invalid consultation fee: " + doctor.getConsultationFee());
        }
        if (doctor.getExperienceYears() < 0) {
            throw new InvalidDataException("Experience years cannot be negative: " + doctor.getExperienceYears());
        }
    }

    public static void validateAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new InvalidDataException("Appointment cannot be null");
        }
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId().isBlank()) {
            throw new InvalidDataException("Appointment ID is required");
        }
        if (appointment.getPatient() == null) {
            throw new InvalidDataException("Appointment patient is required");
        }
        if (appointment.getDoctor() == null) {
            throw new InvalidDataException("Appointment doctor is required");
        }
        if (appointment.getAppointmentStatus() == null) {
            throw new InvalidDataException("Appointment status is required");
        }
        if (appointment.getAppointmentDate() == null) {
            throw new InvalidDataException("Appointment date is required");
        }
        if (DateUtil.isPast(appointment.getAppointmentDate())) {
            throw new InvalidDataException("Appointment date cannot be in the past: " + appointment.getAppointmentDate());
        }
    }

    public static void validateBill(Bill bill) {
        if (bill == null) {
            throw new InvalidDataException("Bill cannot be null");
        }
        if (bill.getBillId() == null || bill.getBillId().isBlank()) {
            throw new InvalidDataException("Bill ID is required");
        }
        if (bill.getAppointment() == null) {
            throw new InvalidDataException("Bill appointment is required");
        }
        if (bill.getAmount() < 0) {
            throw new InvalidDataException("Bill amount cannot be negative: " + bill.getAmount());
        }
        if (bill.getTax() < 0) {
            throw new InvalidDataException("Bill tax cannot be negative: " + bill.getTax());
        }
        if (bill.getTotal() < 0) {
            throw new InvalidDataException("Bill total cannot be negative: " + bill.getTotal());
        }
    }

    public static boolean validateName(String name) {
        return name != null && !name.isBlank() && name.matches("^[A-Za-z .]+$");
    }

    public static boolean validateAge(int age) {
        return age > 0 && age <= 120;
    }

    public static boolean validatePhone(String phone) {
        return phone != null && phone.matches("^[6-9]\\d{9}$");
    }

    public static boolean validateEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean validateFee(double fee) {
        return fee > 0;
    }
}
