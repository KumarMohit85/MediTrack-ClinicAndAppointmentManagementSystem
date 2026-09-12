package com.airtribe.meditrack.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;


public final class PersistenceManager {

    private PersistenceManager() {
    }

    public static void saveAll(
            PatientService patientService,
            DoctorService doctorService,
            AppointmentService appointmentService,
            BillService billService) {

        ensureDataDirectory();

        CSVUtil.savePatients(
                patientService.getAllPatients(),
                Constants.PATIENTS_CSV_PATH);

        CSVUtil.saveDoctors(
                doctorService.getAllDoctors(),
                Constants.DOCTORS_CSV_PATH);

        CSVUtil.saveAppointments(
                appointmentService.getAllAppointments(),
                Constants.APPOINTMENTS_CSV_PATH);

        CSVUtil.saveBills(
                billService.getAllBills(),
                Constants.BILLS_CSV_PATH);
    }

    public static void loadAll(
            PatientService patientService,
            DoctorService doctorService,
            AppointmentService appointmentService,
            BillService billService) {

        IdGenerator idGenerator = IdGenerator.getInstance();

        /*
         * Load patients first because appointments depend on patients.
         */
        if (Files.exists(Paths.get(Constants.PATIENTS_CSV_PATH))) {
            List<Patient> patients =
                    CSVUtil.loadPatients(Constants.PATIENTS_CSV_PATH);

            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);

                idGenerator.observeExistingId(patient.getId());
                patientService.addPatient(patient);
            }
        }

        /*
         * Load doctors next because appointments depend on doctors.
         */
        if (Files.exists(Paths.get(Constants.DOCTORS_CSV_PATH))) {
            List<Doctor> doctors =
                    CSVUtil.loadDoctors(Constants.DOCTORS_CSV_PATH);

            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);

                idGenerator.observeExistingId(doctor.getId());
                doctorService.addDoctor(doctor);
            }
        }

        /*
         * Load appointments after patients and doctors.
         */
        if (Files.exists(Paths.get(Constants.APPOINTMENTS_CSV_PATH))) {
            List<Appointment> appointments =
                    CSVUtil.loadAppointments(Constants.APPOINTMENTS_CSV_PATH);

            for (int i = 0; i < appointments.size(); i++) {
                Appointment appointment = appointments.get(i);

                if (appointment.getPatient() == null
                        || appointment.getDoctor() == null) {
                    continue;
                }

                Patient patient = patientService.getPatientById(
                        appointment.getPatient().getId());

                Doctor doctor = doctorService.getDoctorById(
                        appointment.getDoctor().getId());

                if (patient == null || doctor == null) {
                    continue;
                }

                appointment.setPatient(patient);
                appointment.setDoctor(doctor);

                idGenerator.observeExistingId(
                        appointment.getAppointmentId());

                appointmentService.restoreAppointment(appointment);
            }
        }

        /*
         * Load bills last because bills depend on appointments.
         */
        if (Files.exists(Paths.get(Constants.BILLS_CSV_PATH))) {
            List<String[]> billRows =
                    CSVUtil.loadBillRows(Constants.BILLS_CSV_PATH);

            for (int i = 0; i < billRows.size(); i++) {
                String[] parts = billRows.get(i);

                String billId = parts[0].trim();
                String appointmentId = parts[1].trim();

                Appointment appointment =
                        appointmentService.getAppointmentById(appointmentId);

                if (appointment == null) {
                    continue;
                }

                Bill bill = new Bill(billId, appointment);

                bill.setAmount(Double.parseDouble(parts[2].trim()));
                bill.setTax(Double.parseDouble(parts[3].trim()));
                bill.setTotal(Double.parseDouble(parts[4].trim()));
                bill.setGeneratedAt(DateUtil.parseDateTime(parts[5].trim()));

                idGenerator.observeExistingId(billId);

                billService.restoreBill(bill);
            }
        }
    }

    private static void ensureDataDirectory() {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (Exception e) {
            throw new RuntimeException("Could not create data directory", e);
        }
    }
}