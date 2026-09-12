package com.airtribe.meditrack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.CSVUtil;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--loadData")) {
            loadData();
            return;
        }

        System.out.println("MediTrack Clinic And Appointment Management System");
        System.out.println("Usage: java com.airtribe.meditrack.Main --loadData");
    }

    private static void loadData() {
        List<Patient> patients = CSVUtil.loadPatients("data/patients.csv");
        List<Doctor> doctors = CSVUtil.loadDoctors("data/doctors.csv");
        List<Appointment> appointments = CSVUtil.loadAppointments("data/appointments.csv");

        Map<String, Patient> patientMap = new HashMap<>();
        for (int i = 0; i < patients.size(); i++) {
            patientMap.put(patients.get(i).getId(), patients.get(i));
        }

        Map<String, Doctor> doctorMap = new HashMap<>();
        for (int i = 0; i < doctors.size(); i++) {
            doctorMap.put(doctors.get(i).getId(), doctors.get(i));
        }

        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            String patientId = a.getPatient().getId();
            String doctorId = a.getDoctor().getId();
            a.setPatient(patientMap.get(patientId));
            a.setDoctor(doctorMap.get(doctorId));
        }

        System.out.println("Loaded patients: " + patients.size());
        System.out.println("Loaded doctors: " + doctors.size());
        System.out.println("Loaded appointments: " + appointments.size());

        for (int i = 0; i < appointments.size(); i++) {
            System.out.println(appointments.get(i).getDetails());
        }
    }
}
