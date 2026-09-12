package com.airtribe.meditrack.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

public class DoctorService {

    private final DataStore<Doctor> doctorStore = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();

    public void addDoctor(Doctor doctor) {
        if (doctor == null) {
            return;
        }
        if (doctor.getId() == null || doctor.getId().isBlank()) {
            doctor.setId(idGenerator.generateDoctorId());
        }
        if (doctorStore.exists(doctor.getId())) {
            return;
        }
        Validator.validateDoctor(doctor);
        doctorStore.add(doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorStore.getById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    public void updateDoctor(Doctor doctor) {
        if (doctor == null || !doctorStore.exists(doctor.getId())) {
            return;
        }
        Validator.validateDoctor(doctor);
        doctorStore.update(doctor);
    }

    public void deleteDoctor(String id) {
        doctorStore.delete(id);
    }

    public List<Doctor> searchDoctor(String keyword) {
        String needle = keyword == null ? "" : keyword.toLowerCase();
        return doctorStore.getAll().stream()
                .filter(d -> d.getId().equalsIgnoreCase(keyword) || d.matches(needle))
                .collect(Collectors.toList());
    }

    public List<Doctor> findBySpecialization(Specialization specialization) {
        return doctorStore.getAll().stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsSortedByFee() {
        return doctorStore.getAll().stream()
                .sorted(Comparator.comparingDouble(Doctor::getConsultationFee))
                .collect(Collectors.toList());
    }

    public double calculateAverageFee() {
        return doctorStore.getAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }

    public List<Doctor> findExperiencedDoctors(int minYears) {
        return doctorStore.getAll().stream()
                .filter(d -> d.getExperienceYears() >= minYears)
                .collect(Collectors.toList());
    }
}
