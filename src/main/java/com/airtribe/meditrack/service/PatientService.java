package com.airtribe.meditrack.service;

import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

public class PatientService {

    private final DataStore<Patient> patientStore = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();

    public void addPatient(Patient patient) {
        if (patient == null) {
            return;
        }
        if (patient.getId() == null || patient.getId().isBlank()) {
            patient.setId(idGenerator.generatePatientId());
        }
        if (patientStore.exists(patient.getId())) {
            return;
        }
        Validator.validatePatient(patient);
        patientStore.add(patient);
    }

    public Patient getPatientById(String id) {
        return patientStore.getById(id);
    }

    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    public void updatePatient(Patient patient) {
        if (patient == null || !patientStore.exists(patient.getId())) {
            return;
        }
        Validator.validatePatient(patient);
        patientStore.update(patient);
    }

    public void deletePatient(String id) {
        patientStore.delete(id);
    }

    public List<Patient> searchPatient(String name) {
        String needle = name == null ? "" : name.toLowerCase();
        return patientStore.getAll().stream()
                .filter(p -> p.getId().equalsIgnoreCase(name)
                        || p.matches(needle))
                .collect(Collectors.toList());
    }

    public List<Patient> searchPatient(int age) {
        return patientStore.getAll().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    public List<Patient> findPatientsByName(String keyword) {
        String needle = keyword == null ? "" : keyword.toLowerCase();
        return patientStore.getAll().stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(needle))
                .collect(Collectors.toList());
    }

    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge) {
        return patientStore.getAll().stream()
                .filter(p -> p.getAge() >= minAge && p.getAge() <= maxAge)
                .collect(Collectors.toList());
    }
}
