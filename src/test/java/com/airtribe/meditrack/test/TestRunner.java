package com.airtribe.meditrack.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.BillNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.pattern.factory.BillFactory;
import com.airtribe.meditrack.pattern.factory.BillType;
import com.airtribe.meditrack.pattern.strategy.BillingStrategy;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;

public class TestRunner {

    public static void main(String[] args) {
        new TestRunner().runAllTests();
    }

    public void runAllTests() {
        System.out.println("========== MediTrack TestRunner ==========");
        testDateUtil();
        testPatientCRUD();
        testDoctorCRUD();
        testAppointmentCRUD();
        testPatientSearch();
        testDoctorSearch();
        testDeepCopy();
        testBillGeneration();
        testBillingStrategy();
        testBillFactory();
        testStreamsAndLambdas();
        testEqualsAndHashCode();
        testExceptionHandling();
        System.out.println("========== All TestRunner tests executed ==========");
    }

    public void testDateUtil() {
        System.out.println("\n[Test] DateUtil");
        LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
        LocalDateTime pastDate = LocalDateTime.now().minusDays(2);
        check(!DateUtil.isPast(futureDate), "future date is not past");
        check(DateUtil.isPast(pastDate), "past date is past");
        String formatted = DateUtil.formatDateTime(futureDate);
        check(formatted != null && !formatted.isBlank(), "formatDateTime returns a value");
        pass("DateUtil");
    }

    public void testPatientCRUD() {
        System.out.println("\n[Test] Patient CRUD");
        PatientService patientService = new PatientService();
        Patient patient = samplePatient("PAT-T01", "Alice Smith", 30);
        patientService.addPatient(patient);

        Patient found = patientService.getPatientById("PAT-T01");
        check(found != null && "Alice Smith".equals(found.getName()), "patient add/get");

        found.setAge(31);
        patientService.updatePatient(found);
        check(patientService.getPatientById("PAT-T01").getAge() == 31, "patient update");
        check(patientService.getAllPatients().size() == 1, "patient list");

        patientService.deletePatient("PAT-T01");
        check(patientService.getPatientById("PAT-T01") == null, "patient delete");
        pass("Patient CRUD");
    }

    public void testDoctorCRUD() {
        System.out.println("\n[Test] Doctor CRUD");
        DoctorService doctorService = new DoctorService();
        Doctor doctor = sampleDoctor("DOC-T01", "Dr John Doe", Specialization.CARDIOLOGY, 500.0, 15);
        doctorService.addDoctor(doctor);

        Doctor found = doctorService.getDoctorById("DOC-T01");
        check(found != null && found.getConsultationFee() == 500.0, "doctor add/get");

        found.setConsultationFee(550.0);
        doctorService.updateDoctor(found);
        check(doctorService.getDoctorById("DOC-T01").getConsultationFee() == 550.0, "doctor update");

        doctorService.deleteDoctor("DOC-T01");
        check(doctorService.getDoctorById("DOC-T01") == null, "doctor delete");
        pass("Doctor CRUD");
    }

    public void testAppointmentCRUD() {
        System.out.println("\n[Test] Appointment CRUD");
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService(patientService, doctorService);

        doctorService.addDoctor(sampleDoctor("DOC-T02", "Dr Jane Smith", Specialization.DERMATOLOGY, 1000.0, 10));
        patientService.addPatient(samplePatient("PAT-T02", "Charlie Brown", 25));

        Appointment appointment = appointmentService.createAppointment(
                "PAT-T02", "DOC-T02", LocalDateTime.now().plusDays(1), "Skin allergy");
        check(appointment.getStatus() == AppointmentStatus.PENDING, "appointment create");

        appointmentService.confirmAppointment(appointment.getAppointmentId());
        check(appointmentService.getAppointmentById(appointment.getAppointmentId()).getStatus()
                == AppointmentStatus.CONFIRMED, "appointment confirm");

        appointmentService.completeAppointment(appointment.getAppointmentId());
        check(appointmentService.getCompletedAppointments().size() == 1, "appointment complete");
        pass("Appointment CRUD");
    }

    public void testPatientSearch() {
        System.out.println("\n[Test] Patient search");
        PatientService patientService = new PatientService();
        patientService.addPatient(samplePatient("PAT-T03", "Alice Smith", 30));
        patientService.addPatient(samplePatient("PAT-T04", "Bob Jones", 40));

        check(patientService.findPatientsByName("Alice").size() == 1, "find by name");
        check(patientService.searchPatient(30).size() == 1, "search by age");
        check(patientService.getPatientsByAgeRange(25, 35).size() == 1, "age range");
        pass("Patient search");
    }

    public void testDoctorSearch() {
        System.out.println("\n[Test] Doctor search");
        DoctorService doctorService = new DoctorService();
        doctorService.addDoctor(sampleDoctor("DOC-T03", "Dr John Doe", Specialization.CARDIOLOGY, 500.0, 15));
        doctorService.addDoctor(sampleDoctor("DOC-T04", "Dr Jane Smith", Specialization.DERMATOLOGY, 1000.0, 5));

        check(doctorService.searchDoctor("John").size() == 1, "search keyword");
        check(doctorService.findBySpecialization(Specialization.CARDIOLOGY).size() == 1, "specialization");
        pass("Doctor search");
    }

    public void testDeepCopy() {
        System.out.println("\n[Test] Deep copy / clone");
        Patient original = samplePatient("PAT-T05", "Clone Patient", 28);
        Patient copy = original.clone();
        check(original != copy, "clone is a different instance");
        check(original.equals(copy), "clone equals original by id");

        copy.setName("Changed Name");
        check(!"Changed Name".equals(original.getName()), "changing clone name does not change original");

        Appointment appointment = new Appointment("APT-T01", original,
                sampleDoctor("DOC-T05", "Dr Clone", Specialization.PEDIATRICS, 300.0, 8),
                AppointmentStatus.PENDING, LocalDateTime.now().plusDays(2), "Clone test");
        Appointment appointmentCopy = appointment.clone();
        check(appointment != appointmentCopy, "appointment clone is a different instance");
        appointmentCopy.setNotes("Changed notes");
        check(!"Changed notes".equals(appointment.getNotes()), "changing clone notes does not change original");
        pass("Deep copy");
    }

    public void testBillGeneration() {
        System.out.println("\n[Test] Bill generation");
        Appointment appointment = sampleAppointment();
        BillService billService = new BillService();
        Bill bill = billService.generateBill(appointment, BillType.STANDARD);
        check(bill.getBillId() != null && !bill.getBillId().isBlank(), "bill id generated");
        check(bill.getTotal() > 0, "bill total calculated");
        check(billService.getBillSummary(bill.getBillId()) != null, "bill summary");
        pass("Bill generation");
    }

    public void testBillingStrategy() {
        System.out.println("\n[Test] Billing strategy");
        Appointment appointment = sampleAppointment();
        BillingStrategy standard = BillFactory.createStrategy(BillType.STANDARD);
        BillingStrategy insurance = BillFactory.createStrategy(BillType.INSURANCE);
        BillingStrategy premium = BillFactory.createStrategy(BillType.PREMIUM);

        double fee = appointment.getDoctor().getConsultationFee();
        check(standard.calculateAmount(appointment) == fee, "standard amount");
        check(insurance.calculateAmount(appointment) == fee - (fee * Constants.INSURANCE_COVERAGE), "insurance amount");
        check(premium.calculateAmount(appointment) == fee + Constants.PREMIUM_CHARGE, "premium amount");

        double amount = standard.calculateAmount(appointment);
        check(standard.calculateTax(amount) == amount * Constants.TAX_RATE, "tax uses TAX_RATE");
        check(standard.calculateTotal(amount) == amount + standard.calculateTax(amount), "total");
        pass("Billing strategy");
    }

    public void testBillFactory() {
        System.out.println("\n[Test] Bill factory");
        Appointment appointment = sampleAppointment();
        BillFactory factory = new BillFactory();
        check(BillFactory.createStrategy(BillType.STANDARD).getStrategyName().contains("Standard"), "createStrategy");
        Bill bill = factory.createBill(appointment, BillType.PREMIUM);
        check(bill.getAmount() == appointment.getDoctor().getConsultationFee() + Constants.PREMIUM_CHARGE,
                "createBill uses premium charge");
        pass("Bill factory");
    }

    public void testStreamsAndLambdas() {
        System.out.println("\n[Test] Streams and lambdas");
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService(patientService, doctorService);

        doctorService.addDoctor(sampleDoctor("DOC-T06", "Dr Low Fee", Specialization.NEUROLOGY, 400.0, 4));
        doctorService.addDoctor(sampleDoctor("DOC-T07", "Dr High Fee", Specialization.ORTHOPEDICS, 900.0, 12));
        patientService.addPatient(samplePatient("PAT-T06", "Stream Patient", 33));

        check(doctorService.getDoctorsSortedByFee().get(0).getConsultationFee() == 400.0, "sorted by fee");
        check(doctorService.calculateAverageFee() == 650.0, "average fee");
        check(doctorService.findExperiencedDoctors(10).size() == 1, "experienced doctors");

        appointmentService.createAppointment("PAT-T06", "DOC-T07", LocalDateTime.now().plusDays(3), "Stream");
        Map<Doctor, Long> perDoctor = appointmentService.getAppointmentsPerDoctor();
        check(!perDoctor.isEmpty(), "appointments per doctor");
        pass("Streams and lambdas");
    }

    public void testEqualsAndHashCode() {
        System.out.println("\n[Test] Equals and hashCode");
        Doctor first = sampleDoctor("DOC-EQ", "Dr Equal", Specialization.GENERAL_MEDICINE, 200.0, 2);
        Doctor second = sampleDoctor("DOC-EQ", "Dr Other Name", Specialization.PEDIATRICS, 300.0, 3);
        check(first.equals(second), "same id means equals");
        check(first.hashCode() == second.hashCode(), "same id means same hashCode");
        check(!first.equals(sampleDoctor("DOC-OTHER", "Dr Equal", Specialization.GENERAL_MEDICINE, 200.0, 2)),
                "different id not equal");
        pass("Equals and hashCode");
    }

    public void testExceptionHandling() {
        System.out.println("\n[Test] Exception handling");
        PatientService patientService = new PatientService();
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService(patientService, doctorService);
        BillService billService = new BillService();

        boolean invalidPatient = false;
        try {
            patientService.addPatient(new Patient("PAT-BAD", LocalDateTime.now(), "123", 30,
                    "12345", "bad", new ArrayList<>(), "12345", "O+"));
        } catch (InvalidDataException e) {
            invalidPatient = true;
        }
        check(invalidPatient, "invalid patient throws InvalidDataException");

        boolean missingAppointment = false;
        try {
            appointmentService.getAppointmentById("APT-MISSING");
        } catch (AppointmentNotFoundException e) {
            missingAppointment = true;
        }
        check(missingAppointment, "missing appointment throws AppointmentNotFoundException");

        boolean missingBill = false;
        try {
            billService.getBillById("BILL-MISSING");
        } catch (BillNotFoundException e) {
            missingBill = true;
        }
        check(missingBill, "missing bill throws BillNotFoundException");

        boolean pastAppointment = false;
        doctorService.addDoctor(sampleDoctor("DOC-T08", "Dr Past", Specialization.CARDIOLOGY, 500.0, 9));
        patientService.addPatient(samplePatient("PAT-T08", "Past Patient", 29));
        try {
            appointmentService.createAppointment("PAT-T08", "DOC-T08", LocalDateTime.now().minusDays(1), "Past");
        } catch (IllegalArgumentException | InvalidDataException e) {
            pastAppointment = true;
        }
        check(pastAppointment, "past appointment is rejected");
        pass("Exception handling");
    }

    private Patient samplePatient(String id, String name, int age) {
        return new Patient(id, LocalDateTime.now(), name, age, "9123456780",
                "patient@example.com", new ArrayList<>(List.of("Asthma")), "9876543210", "O+");
    }

    private Doctor sampleDoctor(String id, String name, Specialization specialization, double fee, int years) {
        return new Doctor(id, LocalDateTime.now(), name, 40, "9988776655",
                "doctor@example.com", specialization, fee, years);
    }

    private Appointment sampleAppointment() {
        return new Appointment("APT-BILL",
                samplePatient("PAT-BILL", "Bill Patient", 32),
                sampleDoctor("DOC-BILL", "Dr Billing", Specialization.CARDIOLOGY, 500.0, 11),
                AppointmentStatus.COMPLETED, LocalDateTime.now().plusDays(1), "Billing test");
    }

    private void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Failed: " + message);
        }
        System.out.println("  OK: " + message);
    }

    private void pass(String name) {
        System.out.println("  PASSED: " + name);
    }
}
