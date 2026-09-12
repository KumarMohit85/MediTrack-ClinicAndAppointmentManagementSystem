package com.airtribe.meditrack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.pattern.factory.BillType;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.PersistenceManager;
import com.airtribe.meditrack.util.DateUtil;

public class Main {

    private DoctorService doctorService;
    private PatientService patientService;
    private AppointmentService appointmentService;
    private BillService billService;
    private Scanner scanner;
    private boolean running;

    public static void main(String[] args) {
        Main app = new Main();
        app.initializeServices();
        if (args.length > 0 && Constants.LOAD_DATA_ARG.equals(args[0])) {
            app.loadData();
        }
        app.running = true;
        while (app.running) {
            app.showMenu();
            int choice = app.readInt("Enter choice: ");
            app.handleChoice(choice);
        }
    }

    private void initializeServices() {
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService(patientService, doctorService);
        billService = new BillService();
        scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println();
        System.out.println("===== MediTrack Clinic =====");
        System.out.println(Constants.MENU_DOCTOR + ". Doctor");
        System.out.println(Constants.MENU_PATIENT + ". Patient");
        System.out.println(Constants.MENU_APPOINTMENT + ". Appointment");
        System.out.println(Constants.MENU_BILLING + ". Billing");
        System.out.println(Constants.MENU_SEARCH + ". Search");
        System.out.println(Constants.MENU_LOAD_DATA + ". Load data from CSV");
        System.out.println(Constants.MENU_EXIT + ". Exit");
    }

    private void handleChoice(int choice) {
        try {
            if (choice == Constants.MENU_DOCTOR) {
                handleDoctorMenu();
            } else if (choice == Constants.MENU_PATIENT) {
                handlePatientMenu();
            } else if (choice == Constants.MENU_APPOINTMENT) {
                handleAppointmentMenu();
            } else if (choice == Constants.MENU_BILLING) {
                handleBillingMenu();
            } else if (choice == Constants.MENU_SEARCH) {
                handleSearchMenu();
            } else if (choice == Constants.MENU_LOAD_DATA) {
                loadData();
            } else if (choice == Constants.MENU_EXIT) {
                exitApplication();
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleDoctorMenu() {
        System.out.println("--- Doctor ---");
        System.out.println(Constants.SUBMENU_ADD + ". Add");
        System.out.println(Constants.SUBMENU_GET_BY_ID + ". Get by ID");
        System.out.println(Constants.SUBMENU_LIST_ALL + ". List all");
        System.out.println(Constants.SUBMENU_UPDATE + ". Update");
        System.out.println(Constants.SUBMENU_DELETE + ". Delete");
        System.out.println(Constants.MENU_EXIT + ". Back");
        int choice = readInt("Enter choice: ");
        if (choice == Constants.SUBMENU_ADD) {
            Doctor doctor = readDoctor("");
            doctorService.addDoctor(doctor);
            System.out.println("Added: " + doctor.getDetails());
        } else if (choice == Constants.SUBMENU_GET_BY_ID) {
            Doctor doctor = doctorService.getDoctorById(readLine("Doctor ID: "));
            System.out.println(doctor == null ? "Doctor not found." : doctor.getDetails());
        } else if (choice == Constants.SUBMENU_LIST_ALL) {
            printDoctors(doctorService.getAllDoctors());
        } else if (choice == Constants.SUBMENU_UPDATE) {
            Doctor existing = doctorService.getDoctorById(readLine("Doctor ID: "));
            if (existing == null) {
                System.out.println("Doctor not found.");
                return;
            }
            Doctor updated = readDoctor(existing.getId());
            doctorService.updateDoctor(updated);
            System.out.println("Updated: " + updated.getDetails());
        } else if (choice == Constants.SUBMENU_DELETE) {
            doctorService.deleteDoctor(readLine("Doctor ID: "));
            System.out.println("Deleted.");
        }
    }

    private void handlePatientMenu() {
        System.out.println("--- Patient ---");
        System.out.println(Constants.SUBMENU_ADD + ". Add");
        System.out.println(Constants.SUBMENU_GET_BY_ID + ". Get by ID");
        System.out.println(Constants.SUBMENU_LIST_ALL + ". List all");
        System.out.println(Constants.SUBMENU_UPDATE + ". Update");
        System.out.println(Constants.SUBMENU_DELETE + ". Delete");
        System.out.println(Constants.MENU_EXIT + ". Back");
        int choice = readInt("Enter choice: ");
        if (choice == Constants.SUBMENU_ADD) {
            Patient patient = readPatient("");
            patientService.addPatient(patient);
            System.out.println("Added: " + patient.getDetails());
        } else if (choice == Constants.SUBMENU_GET_BY_ID) {
            Patient patient = patientService.getPatientById(readLine("Patient ID: "));
            System.out.println(patient == null ? "Patient not found." : patient.getDetails());
        } else if (choice == Constants.SUBMENU_LIST_ALL) {
            printPatients(patientService.getAllPatients());
        } else if (choice == Constants.SUBMENU_UPDATE) {
            Patient existing = patientService.getPatientById(readLine("Patient ID: "));
            if (existing == null) {
                System.out.println("Patient not found.");
                return;
            }
            Patient updated = readPatient(existing.getId());
            patientService.updatePatient(updated);
            System.out.println("Updated: " + updated.getDetails());
        } else if (choice == Constants.SUBMENU_DELETE) {
            patientService.deletePatient(readLine("Patient ID: "));
            System.out.println("Deleted.");
        }
    }

    private void handleAppointmentMenu() {
        System.out.println("--- Appointment ---");
        System.out.println(Constants.APPOINTMENT_CREATE + ". Create");
        System.out.println(Constants.APPOINTMENT_GET_BY_ID + ". Get by ID");
        System.out.println(Constants.APPOINTMENT_LIST_ALL + ". List all");
        System.out.println(Constants.APPOINTMENT_CONFIRM + ". Confirm");
        System.out.println(Constants.APPOINTMENT_CANCEL + ". Cancel");
        System.out.println(Constants.APPOINTMENT_COMPLETE + ". Complete");
        System.out.println(Constants.APPOINTMENT_BY_PATIENT + ". By patient");
        System.out.println(Constants.APPOINTMENT_BY_DOCTOR + ". By doctor");
        System.out.println(Constants.APPOINTMENT_BY_STATUS + ". By status");
        System.out.println(Constants.MENU_EXIT + ". Back");
        int choice = readInt("Enter choice: ");
        if (choice == Constants.APPOINTMENT_CREATE) {
            Appointment appointment = appointmentService.createAppointment(
                    readLine("Patient ID: "),
                    readLine("Doctor ID: "),
                    readDateTime("Date time (" + Constants.DATE_TIME_PATTERN + "): "),
                    readLine("Notes: "));
            System.out.println("Created: " + appointment.getDetails());
        } else if (choice == Constants.APPOINTMENT_GET_BY_ID) {
            System.out.println(appointmentService.getAppointmentById(readLine("Appointment ID: ")).getDetails());
        } else if (choice == Constants.APPOINTMENT_LIST_ALL) {
            printAppointments(appointmentService.getAllAppointments());
        } else if (choice == Constants.APPOINTMENT_CONFIRM) {
            appointmentService.confirmAppointment(readLine("Appointment ID: "));
            System.out.println("Confirmed.");
        } else if (choice == Constants.APPOINTMENT_CANCEL) {
            appointmentService.cancelAppointment(readLine("Appointment ID: "));
            System.out.println("Cancelled.");
        } else if (choice == Constants.APPOINTMENT_COMPLETE) {
            appointmentService.completeAppointment(readLine("Appointment ID: "));
            System.out.println("Completed.");
        } else if (choice == Constants.APPOINTMENT_BY_PATIENT) {
            printAppointments(appointmentService.getAppointmentsByPatient(readLine("Patient ID: ")));
        } else if (choice == Constants.APPOINTMENT_BY_DOCTOR) {
            printAppointments(appointmentService.getAppointmentsByDoctor(readLine("Doctor ID: ")));
        } else if (choice == Constants.APPOINTMENT_BY_STATUS) {
            printAppointments(appointmentService.getAppointmentsByStatus(readStatus()));
        }
    }

    private void handleBillingMenu() {
        System.out.println("--- Billing ---");
        System.out.println(Constants.BILLING_GENERATE + ". Generate bill");
        System.out.println(Constants.BILLING_GET_BY_ID + ". Get by ID");
        System.out.println(Constants.BILLING_LIST_ALL + ". List all");
        System.out.println(Constants.BILLING_BY_APPOINTMENT + ". Get by appointment");
        System.out.println(Constants.BILLING_SUMMARY + ". Summary");
        System.out.println(Constants.MENU_EXIT + ". Back");
        int choice = readInt("Enter choice: ");
        if (choice == Constants.BILLING_GENERATE) {
            Appointment appointment = appointmentService.getAppointmentById(readLine("Appointment ID: "));
            Bill bill = billService.generateBill(appointment, readBillType());
            System.out.println(bill.getDetails());
        } else if (choice == Constants.BILLING_GET_BY_ID) {
            System.out.println(billService.getBillById(readLine("Bill ID: ")).getDetails());
        } else if (choice == Constants.BILLING_LIST_ALL) {
            List<Bill> bills = billService.getAllBills();
            if (bills.isEmpty()) {
                System.out.println("No bills.");
            }
            for (int i = 0; i < bills.size(); i++) {
                System.out.println(bills.get(i).getDetails());
            }
        } else if (choice == Constants.BILLING_BY_APPOINTMENT) {
            System.out.println(billService.getBillByAppointment(readLine("Appointment ID: ")).getDetails());
        } else if (choice == Constants.BILLING_SUMMARY) {
            System.out.println(billService.getBillSummary(readLine("Bill ID: ")));
        }
    }

    private void handleSearchMenu() {
        System.out.println("--- Search ---");
        System.out.println(Constants.SEARCH_PATIENT_NAME + ". Patient by name/id");
        System.out.println(Constants.SEARCH_PATIENT_AGE + ". Patient by age");
        System.out.println(Constants.SEARCH_PATIENT_AGE_RANGE + ". Patients by age range");
        System.out.println(Constants.SEARCH_DOCTOR_KEYWORD + ". Doctor by keyword");
        System.out.println(Constants.SEARCH_DOCTOR_SPECIALIZATION + ". Doctor by specialization");
        System.out.println(Constants.SEARCH_DOCTORS_BY_FEE + ". Doctors sorted by fee");
        System.out.println(Constants.SEARCH_EXPERIENCED_DOCTORS + ". Experienced doctors");
        System.out.println(Constants.SEARCH_APPOINTMENTS_PER_DOCTOR + ". Appointments per doctor");
        System.out.println(Constants.MENU_EXIT + ". Back");
        int choice = readInt("Enter choice: ");
        if (choice == Constants.SEARCH_PATIENT_NAME) {
            printPatients(patientService.searchPatient(readLine("Name or ID: ")));
        } else if (choice == Constants.SEARCH_PATIENT_AGE) {
            printPatients(patientService.searchPatient(readInt("Age: ")));
        } else if (choice == Constants.SEARCH_PATIENT_AGE_RANGE) {
            printPatients(patientService.getPatientsByAgeRange(readInt("Min age: "), readInt("Max age: ")));
        } else if (choice == Constants.SEARCH_DOCTOR_KEYWORD) {
            printDoctors(doctorService.searchDoctor(readLine("Keyword: ")));
        } else if (choice == Constants.SEARCH_DOCTOR_SPECIALIZATION) {
            printDoctors(doctorService.findBySpecialization(readSpecialization()));
        } else if (choice == Constants.SEARCH_DOCTORS_BY_FEE) {
            printDoctors(doctorService.getDoctorsSortedByFee());
            System.out.println("Average fee: " + doctorService.calculateAverageFee());
        } else if (choice == Constants.SEARCH_EXPERIENCED_DOCTORS) {
            printDoctors(doctorService.findExperiencedDoctors(readInt("Min years: ")));
        } else if (choice == Constants.SEARCH_APPOINTMENTS_PER_DOCTOR) {
            Map<Doctor, Long> counts = appointmentService.getAppointmentsPerDoctor();
            if (counts.isEmpty()) {
                System.out.println("No appointments.");
            }
            for (Map.Entry<Doctor, Long> entry : counts.entrySet()) {
                System.out.println(entry.getKey().getName() + ": " + entry.getValue());
            }
        }
    }

private void loadData() {
    PersistenceManager.loadAll(
            patientService,
            doctorService,
            appointmentService,
            billService);

    System.out.println("Loaded patients: "
            + patientService.getAllPatients().size());

    System.out.println("Loaded doctors: "
            + doctorService.getAllDoctors().size());

    System.out.println("Loaded appointments: "
            + appointmentService.getAllAppointments().size());

    System.out.println("Loaded bills: "
            + billService.getAllBills().size());
}

private void exitApplication() {
    PersistenceManager.saveAll(
            patientService,
            doctorService,
            appointmentService,
            billService);

    System.out.println("Data saved successfully.");
    System.out.println("Exiting MediTrack.");

    running = false;

    if (scanner != null) {
        scanner.close();
    }
}

    private Doctor readDoctor(String id) {
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String email = readLine("Email: ");
        Specialization specialization = readSpecialization();
        double fee = readDouble("Consultation fee: ");
        int experience = readInt("Experience years: ");
        return new Doctor(id, LocalDateTime.now(), name, age, phone, email, specialization, fee, experience);
    }

    private Patient readPatient(String id) {
        String name = readLine("Name: ");
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String email = readLine("Email: ");
        String historyRaw = readLine("Medical history (use | to separate): ");
        List<String> history = new ArrayList<>();
        if (!historyRaw.isBlank()) {
            String[] parts = historyRaw.split("\\|");
            for (int i = 0; i < parts.length; i++) {
                history.add(parts[i].trim());
            }
        }
        String emergency = readLine("Emergency contact phone: ");
        String bloodGroup = readLine("Blood group: ");
        return new Patient(id, LocalDateTime.now(), name, age, phone, email, history, emergency, bloodGroup);
    }

    private Specialization readSpecialization() {
        Specialization[] values = Specialization.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        int index = readInt("Specialization: ") - 1;
        if (index < 0 || index >= values.length) {
            throw new IllegalArgumentException("Invalid specialization");
        }
        return values[index];
    }

    private AppointmentStatus readStatus() {
        AppointmentStatus[] values = AppointmentStatus.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        int index = readInt("Status: ") - 1;
        if (index < 0 || index >= values.length) {
            throw new IllegalArgumentException("Invalid status");
        }
        return values[index];
    }

    private BillType readBillType() {
        System.out.println(Constants.BILL_TYPE_STANDARD + ". STANDARD");
        System.out.println(Constants.BILL_TYPE_INSURANCE + ". INSURANCE");
        System.out.println(Constants.BILL_TYPE_PREMIUM + ". PREMIUM");
        int choice = readInt("Bill type: ");
        if (choice == Constants.BILL_TYPE_STANDARD) {
            return BillType.STANDARD;
        }
        if (choice == Constants.BILL_TYPE_INSURANCE) {
            return BillType.INSURANCE;
        }
        if (choice == Constants.BILL_TYPE_PREMIUM) {
            return BillType.PREMIUM;
        }
        throw new IllegalArgumentException("Invalid bill type");
    }

    private void printPatients(List<Patient> patients) {
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (int i = 0; i < patients.size(); i++) {
            System.out.println(patients.get(i).getDetails());
        }
    }

    private void printDoctors(List<Doctor> doctors) {
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println(doctors.get(i).getDetails());
        }
    }

    private void printAppointments(List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println(appointments.get(i).getDetails());
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private LocalDateTime readDateTime(String prompt) {
        String value = readLine(prompt);
        LocalDateTime parsed = DateUtil.parseDateTime(value);
        if (parsed == null) {
            throw new IllegalArgumentException("Invalid date time. Use " + Constants.DATE_TIME_PATTERN);
        }
        return parsed;
    }
}
