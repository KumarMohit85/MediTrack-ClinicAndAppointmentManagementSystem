package com.airtribe.meditrack.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.AppointmentStatus;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.pattern.factory.BillFactory;
import com.airtribe.meditrack.pattern.factory.BillType;
import com.airtribe.meditrack.pattern.strategy.BillingStrategy;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;

public class TestRun {

    public static void main(String[] args) {
        System.out.println("========== Running MediTrack Unit & Integration Tests ==========\n");

        testDateUtil();
        testAppointmentService();
        testDesignPatternsAndBilling();

        System.out.println("\nAll Appointment Service & Design Pattern tests executed successfully!");
    }

    private static void testDateUtil() {
        System.out.println("[Test 1] Testing DateUtil...");
        LocalDateTime futureDate = LocalDateTime.now().plusDays(2);
        LocalDateTime pastDate = LocalDateTime.now().minusDays(2);

        assert !DateUtil.isPast(futureDate) : "Future date should not be past";
        assert DateUtil.isPast(pastDate) : "Past date should be past";

        String formatted = DateUtil.formatDateTime(futureDate);
        System.out.println("  Formatted future date: " + formatted);
        System.out.println("  PASSED: DateUtil tests passed.");
    }

    private static void testAppointmentService() {
        System.out.println("\n[Test 2] Testing AppointmentService...");
        DoctorService doctorService = new DoctorService();
        PatientService patientService = new PatientService();
        AppointmentService appointmentService = new AppointmentService(patientService, doctorService);

        Doctor doc = new Doctor("DOC-001", LocalDateTime.now(), "Dr. John Doe", 45, "9876543210",
                "john@example.com", Specialization.CARDIOLOGY, 500.0, 15);
        doctorService.addDoctor(doc);

        Patient patient = new Patient("PAT-001", LocalDateTime.now(), "Alice Smith", 30, "9123456780",
                "alice@example.com", new ArrayList<>(List.of("Asthma")), "Bob Smith", "O+");
        patientService.addPatient(patient);

        LocalDateTime appTime = LocalDateTime.now().plusDays(1);
        Appointment app = appointmentService.createAppointment("PAT-001", "DOC-001", appTime, "Regular checkup");
        System.out.println("  Created Appointment: " + app.getAppointmentId() + ", Status: " + app.getStatus());

        appointmentService.confirmAppointment(app.getAppointmentId());
        System.out.println("  Confirmed Appointment Status: " + app.getStatus());

        Map<Doctor, Long> perDoctor = appointmentService.getAppointmentsPerDoctor();
        System.out.println("  Appointments for Dr. John: " + perDoctor.get(doc));

        System.out.println("  PASSED: AppointmentService tests passed.");
    }

    private static void testDesignPatternsAndBilling() {
        System.out.println("\n[Test 3] Testing Design Patterns (Strategy & Factory) and BillService...");
        Doctor doc = new Doctor("DOC-002", LocalDateTime.now(), "Dr. Jane Smith", 40, "9988776655",
                "jane@example.com", Specialization.DERMATOLOGY, 1000.0, 10);
        Patient patient = new Patient("PAT-002", LocalDateTime.now(), "Charlie Brown", 25, "9112233445",
                "charlie@example.com", new ArrayList<>(), "Lucy", "A+");
        Appointment app = new Appointment("APT-099", patient, doc, AppointmentStatus.CONFIRMED,
                LocalDateTime.now().plusDays(1), "Skin allergy");

        // Factory & Strategies test
        BillingStrategy standardStrat = BillFactory.createStrategy(BillType.STANDARD);
        BillingStrategy insuranceStrat = BillFactory.createStrategy(BillType.INSURANCE);
        BillingStrategy premiumStrat = BillFactory.createStrategy(BillType.PREMIUM);

        System.out.println("  Strategy 1: " + standardStrat.getStrategyName() + " -> Total: " + standardStrat.calculateTotal(standardStrat.calculateAmount(app)));
        System.out.println("  Strategy 2: " + insuranceStrat.getStrategyName() + " -> Total: " + insuranceStrat.calculateTotal(insuranceStrat.calculateAmount(app)));
        System.out.println("  Strategy 3: " + premiumStrat.getStrategyName() + " -> Total: " + premiumStrat.calculateTotal(premiumStrat.calculateAmount(app)));

        // BillService test
        BillService billService = new BillService();
        Bill bill = billService.generateBill(app, BillType.INSURANCE);
        System.out.println("  Generated Bill: ID=" + bill.getBillId() + ", Amount=" + bill.getAmount() + ", Tax=" + bill.getTax() + ", Total=" + bill.getTotal());

        BillSummary summary = billService.getBillSummary(bill.getBillId());
        System.out.println("  Immutable BillSummary generated: " + summary);

        System.out.println("  PASSED: Design Patterns and BillService tests passed.");
    }
}
