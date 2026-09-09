package com.airtribe.meditrack.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Patient;

public class PatientService {

    private final Map<String, Patient> patients = new HashMap<>();

    public boolean addPatient(Patient patient) {
        if (patient == null || patient.getId() == null || patients.containsKey(patient.getId())) {
            return false;
        }
        patients.put(patient.getId(), patient);
        return true;
    }

    public Patient getPatientById(String id) {
        return patients.get(id);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public boolean updatePatient(Patient patient) {
        if (patient == null || !patients.containsKey(patient.getId())) {
            return false;
        }
        patients.put(patient.getId(), patient);
        return true;
    }

    public boolean deletePatient(String id) {
        Iterator<Patient> it = patients.values().iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // overloaded: by id/name text vs by age
    public List<Patient> searchPatient(String query) {
        String needle = query == null ? "" : query.toLowerCase();
        return patients.values().stream()
                .filter(p -> p.getId().equalsIgnoreCase(query)
                        || (p.getName() != null && p.getName().toLowerCase().contains(needle)))
                .collect(Collectors.toList());
    }

    public List<Patient> searchPatient(int age) {
        return patients.values().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    public List<Patient> findPatientsByName(String name) {
        String needle = name.toLowerCase();
        return patients.values().stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge) {
        return patients.values().stream()
                .filter(p -> p.getAge() >= minAge && p.getAge() <= maxAge)
                .collect(Collectors.toList());
    }
}
