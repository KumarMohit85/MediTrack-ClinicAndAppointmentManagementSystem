package com.airtribe.meditrack.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;

public class CSVUtil {

    public static void savePatients(List<Patient> patients, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("id,createdAt,name,age,phone,email,medicalHistory,emergencyContact,bloodGroup");
            writer.newLine();

            for (int i = 0; i < patients.size(); i++) {
                Patient p = patients.get(i);
                String history = "";
                if (p.getMedicalHistory() != null) {
                    for (int j = 0; j < p.getMedicalHistory().size(); j++) {
                        if (j > 0) {
                            history = history + "|";
                        }
                        history = history + p.getMedicalHistory().get(j);
                    }
                }

                String line = p.getId() + ","
                        + DateUtil.formatDateTime(p.getCreatedAt()) + ","
                        + p.getName() + ","
                        + p.getAge() + ","
                        + p.getPhone() + ","
                        + p.getEmail() + ","
                        + history + ","
                        + p.getEmergencyContact() + ","
                        + p.getBloodGroup();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not save patients");
        }
    }

    public static List<Patient> loadPatients(String path) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    patients.add(parsePatient(line));
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not load patients");
        }
        return patients;
    }

    public static void saveDoctors(List<Doctor> doctors, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("id,createdAt,name,age,phone,email,specialization,consultationFee,experienceYears");
            writer.newLine();

            for (int i = 0; i < doctors.size(); i++) {
                Doctor d = doctors.get(i);
                String line = d.getId() + ","
                        + DateUtil.formatDateTime(d.getCreatedAt()) + ","
                        + d.getName() + ","
                        + d.getAge() + ","
                        + d.getPhone() + ","
                        + d.getEmail() + ","
                        + d.getSpecialization() + ","
                        + d.getConsultationFee() + ","
                        + d.getExperienceYears();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not save doctors");
        }
    }

    public static List<Doctor> loadDoctors(String path) {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    doctors.add(parseDoctor(line));
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not load doctors");
        }
        return doctors;
    }

    public static void saveAppointments(List<Appointment> appointments, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("appointmentId,patientId,doctorId,status,appointmentDate,notes");
            writer.newLine();

            for (int i = 0; i < appointments.size(); i++) {
                Appointment a = appointments.get(i);
                String patientId = "";
                String doctorId = "";
                if (a.getPatient() != null) {
                    patientId = a.getPatient().getId();
                }
                if (a.getDoctor() != null) {
                    doctorId = a.getDoctor().getId();
                }

                String line = a.getAppointmentId() + ","
                        + patientId + ","
                        + doctorId + ","
                        + a.getAppointmentStatus() + ","
                        + DateUtil.formatDateTime(a.getAppointmentDate()) + ","
                        + a.getNotes();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not save appointments");
        }
    }

    public static List<Appointment> loadAppointments(String path) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    appointments.add(parseAppointment(line));
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not load appointments");
        }
        return appointments;
    }

    private static Patient parsePatient(String line) {
        String[] parts = line.split(",");

        List<String> history = new ArrayList<>();
        if (parts[6] != null && !parts[6].trim().isEmpty()) {
            String[] historyParts = parts[6].split("\\|");
            for (int i = 0; i < historyParts.length; i++) {
                history.add(historyParts[i].trim());
            }
        }

        Patient patient = new Patient(
                parts[0].trim(),
                DateUtil.parseDateTime(parts[1].trim()),
                parts[2].trim(),
                Integer.parseInt(parts[3].trim()),
                parts[4].trim(),
                parts[5].trim(),
                history,
                parts[7].trim(),
                parts[8].trim());
        return patient;
    }

    private static Doctor parseDoctor(String line) {
        String[] parts = line.split(",");

        Doctor doctor = new Doctor(
                parts[0].trim(),
                DateUtil.parseDateTime(parts[1].trim()),
                parts[2].trim(),
                Integer.parseInt(parts[3].trim()),
                parts[4].trim(),
                parts[5].trim(),
                Specialization.valueOf(parts[6].trim()),
                Double.parseDouble(parts[7].trim()),
                Integer.parseInt(parts[8].trim()));
        return doctor;
    }

    private static Appointment parseAppointment(String line) {
        String[] parts = line.split(",");

        Patient patient = new Patient(
                parts[1].trim(),
                LocalDateTime.now(),
                "Temp",
                1,
                "6000000000",
                "temp@mail.com",
                new ArrayList<>(),
                "6000000000",
                "O+");

        Doctor doctor = new Doctor(
                parts[2].trim(),
                LocalDateTime.now(),
                "Temp",
                1,
                "6000000000",
                "temp@mail.com",
                Specialization.GENERAL_MEDICINE,
                1.0,
                0);

        Appointment appointment = new Appointment(
                parts[0].trim(),
                patient,
                doctor,
                AppointmentStatus.valueOf(parts[3].trim()),
                DateUtil.parseDateTime(parts[4].trim()),
                parts[5].trim());
        return appointment;
    }

    public static void saveBills(List<Bill> bills, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("billId,appointmentId,amount,tax,total,generatedAt");
            writer.newLine();

            for (int i = 0; i < bills.size(); i++) {
                Bill bill = bills.get(i);

                String appointmentId = "";

                if (bill.getAppointment() != null) {
                    appointmentId = bill.getAppointment().getAppointmentId();
                }

                String generatedAt = "";

                if (bill.getGeneratedAt() != null) {
                    generatedAt = DateUtil.formatDateTime(bill.getGeneratedAt());
                }

                String line = bill.getBillId() + ","
                        + appointmentId + ","
                        + bill.getAmount() + ","
                        + bill.getTax() + ","
                        + bill.getTotal() + ","
                        + generatedAt;

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not save bills");
        }
    }

    public static List<String[]> loadBillRows(String path) {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rows.add(line.split(","));
                }
            }
        } catch (IOException e) {
            throw new InvalidDataException("Could not load bills");
        }

        return rows;
    }
    
}
