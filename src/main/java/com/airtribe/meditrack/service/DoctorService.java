package com.airtribe.meditrack.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;

public class DoctorService {

    private final Map<String, Doctor> doctors = new HashMap<>();

    public boolean addDoctor(Doctor doctor) {
        if (doctor == null || doctor.getId() == null || doctors.containsKey(doctor.getId())) {
            return false;
        }
        doctors.put(doctor.getId(), doctor);
        return true;
    }

    public Doctor getDoctorById(String id) {
        return doctors.get(id);
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctors.values());
    }

    public boolean updateDoctor(Doctor doctor) {
        if (doctor == null || !doctors.containsKey(doctor.getId())) {
            return false;
        }
        doctors.put(doctor.getId(), doctor);
        return true;
    }

    public boolean deleteDoctor(String id) {
        Iterator<Doctor> it = doctors.values().iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Doctor> searchDoctor(String keyword) {
        return doctors.values().stream()
                .filter(d -> d.matches(keyword))
                .collect(Collectors.toList());
    }

    public List<Doctor> findBySpecialization(Specialization specialization) {
        return doctors.values().stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsSortedByFee() {
        return doctors.values().stream()
                .sorted(Comparator.comparingDouble(Doctor::getConsultationFee))
                .collect(Collectors.toList());
    }

    public double calculateAverageFee() {
        return doctors.values().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }
}
