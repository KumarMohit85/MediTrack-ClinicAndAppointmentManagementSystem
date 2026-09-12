# User Guide

## Main menu
The final CLI should expose the core application operations through a menu similar to:

```text
========== MediTrack ==========
1. Patient Management
2. Doctor Management
3. Appointment Management
4. Billing
5. Doctor Recommendation
6. Analytics
0. Exit
```

## Patient management
Typical operations:
1. Add patient
2. View all patients
3. Search patient
4. Update patient
5. Delete patient
0. Back

Search demonstrates overloaded methods:
- ID/name → `searchPatient(String)`
- age → `searchPatient(int)`

## Doctor management
Typical operations:
1. Add doctor
2. View all doctors
3. Search doctor
4. Update doctor
5. Delete doctor
6. Filter by specialization
7. Sort by consultation fee
0. Back

## Appointment management
Typical operations:
1. Create appointment
2. View appointment
3. View all appointments
4. Confirm appointment
5. Cancel appointment
6. Complete appointment
0. Back

Appointment status should be represented by `AppointmentStatus`, not arbitrary strings.

## Billing
Typical flow:

```text
Completed Appointment
        |
        v
Select Bill Type
        |
        v
BillFactory
        |
        v
BillingStrategy
        |
        v
Calculate amount + tax
        |
        v
Bill / BillSummary
```

## Recommendation
Enter symptoms/keywords. The rule-based helper maps them to a specialization and recommends matching doctors.

## Analytics
Streams/lambdas can be used to show examples such as:
- Doctors filtered by specialization.
- Average consultation fee.
- Sorted doctors by fee.
- Appointment counts by doctor/status.

## Persistence
If persistence is enabled, the CLI can be started with `--loadData`. The application loads saved records before showing the menu.
