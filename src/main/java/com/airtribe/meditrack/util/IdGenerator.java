package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final IdGenerator instance = new IdGenerator();

    private static final AtomicInteger patientCounter = new AtomicInteger(1);
    private static final AtomicInteger doctorCounter = new AtomicInteger(1);
    private static final AtomicInteger appointmentCounter = new AtomicInteger(1);
    private static final AtomicInteger billCounter = new AtomicInteger(1);

    private IdGenerator() {
    }

    public static IdGenerator getInstance() {
        return instance;
    }

    public String generatePatientId() {
        return String.format("PAT-%03d", patientCounter.getAndIncrement());
    }

    public String generateDoctorId() {
        return String.format("DOC-%03d", doctorCounter.getAndIncrement());
    }

    public String generateAppointmentId() {
        return String.format("APT-%03d", appointmentCounter.getAndIncrement());
    }

    public String generateBillId() {
        return String.format("BILL-%03d", billCounter.getAndIncrement());
    }

    public void observeExistingId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return;
        }

        if (id.startsWith("PAT-")) {
            updateCounter(patientCounter, id);
        } else if (id.startsWith("DOC-")) {
            updateCounter(doctorCounter, id);
        } else if (id.startsWith("APT-")) {
            updateCounter(appointmentCounter, id);
        } else if (id.startsWith("BILL-")) {
            updateCounter(billCounter, id);
        }
    }

    private void updateCounter(AtomicInteger counter, String id) {
        try {
            int dashIndex = id.indexOf('-');
            int number = Integer.parseInt(id.substring(dashIndex + 1));

            counter.updateAndGet(current -> Math.max(current, number + 1));
        } catch (NumberFormatException e) {
            // Ignore invalid IDs.
        }
    }
}