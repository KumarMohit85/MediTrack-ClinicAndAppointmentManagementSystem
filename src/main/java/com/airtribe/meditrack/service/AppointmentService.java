package com.airtribe.meditrack.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DateUtil;

public class AppointmentService {

    private final Map<String, Appointment> appointments = new HashMap<>();
    private PatientService patientService;
    private DoctorService doctorService;
    private int appointmentCounter = 1;

    public AppointmentService() {
    }

    public AppointmentService(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    public void setDoctorService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public Appointment createAppointment(String patientId, String doctorId, LocalDateTime appointmentDate, String notes) {
        if (patientService != null) {
            Patient patient = patientService.getPatientById(patientId);
            if (patient == null) {
                throw new IllegalArgumentException("Invalid patient ID: " + patientId);
            }
        }

        if (doctorService != null) {
            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (doctor == null) {
                throw new IllegalArgumentException("Invalid doctor ID: " + doctorId);
            }
        }

        if (DateUtil.isPast(appointmentDate)) {
            throw new IllegalArgumentException("Appointment date cannot be in the past: " + appointmentDate);
        }

        Patient patient = patientService != null ? patientService.getPatientById(patientId) : null;
        Doctor doctor = doctorService != null ? doctorService.getDoctorById(doctorId) : null;

        String appointmentId = String.format("APT-%03d", appointmentCounter++);
        Appointment appointment = new Appointment(appointmentId, patient, doctor, AppointmentStatus.PENDING, appointmentDate, notes);
        appointments.put(appointmentId, appointment);
        return appointment;
    }

    public Appointment getAppointmentById(String id) {
        Appointment appointment = appointments.get(id);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found with ID: " + id);
        }
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public boolean confirmAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        appointment.confirm();
        return appointment.getStatus() == AppointmentStatus.CONFIRMED;
    }

    public boolean cancelAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        appointment.cancel();
        return appointment.getStatus() == AppointmentStatus.CANCELLED;
    }

    public boolean completeAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        return true;
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.values().stream()
                .filter(a -> a.getPatient() != null && a.getPatient().getId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointments.values().stream()
                .filter(a -> a.getDoctor() != null && a.getDoctor().getId().equalsIgnoreCase(doctorId))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointments.values().stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Appointment> getCompletedAppointments() {
        return getAppointmentsByStatus(AppointmentStatus.COMPLETED);
    }

    public Map<Doctor, Long> getAppointmentsPerDoctor() {
        return appointments.values().stream()
                .filter(a -> a.getDoctor() != null)
                .collect(Collectors.groupingBy(Appointment::getDoctor, Collectors.counting()));
    }
}
