package com.airtribe.meditrack.constants;

public final class Constants {

    public static final double TAX_RATE = 0.18;
    public static final double INSURANCE_COVERAGE = 0.20;
    public static final double PREMIUM_CHARGE = 500.0;

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String LOAD_DATA_ARG = "--loadData";
    public static final String PATIENTS_CSV_PATH = "data/patients.csv";
    public static final String DOCTORS_CSV_PATH = "data/doctors.csv";
    public static final String APPOINTMENTS_CSV_PATH = "data/appointments.csv";
    public static final String BILLS_CSV_PATH = "data/bills.csv";

    public static final int MENU_EXIT = 0;
    public static final int MENU_DOCTOR = 1;
    public static final int MENU_PATIENT = 2;
    public static final int MENU_APPOINTMENT = 3;
    public static final int MENU_BILLING = 4;
    public static final int MENU_SEARCH = 5;
    public static final int MENU_LOAD_DATA = 6;

    public static final int SUBMENU_ADD = 1;
    public static final int SUBMENU_GET_BY_ID = 2;
    public static final int SUBMENU_LIST_ALL = 3;
    public static final int SUBMENU_UPDATE = 4;
    public static final int SUBMENU_DELETE = 5;

    public static final int APPOINTMENT_CREATE = 1;
    public static final int APPOINTMENT_GET_BY_ID = 2;
    public static final int APPOINTMENT_LIST_ALL = 3;
    public static final int APPOINTMENT_CONFIRM = 4;
    public static final int APPOINTMENT_CANCEL = 5;
    public static final int APPOINTMENT_COMPLETE = 6;
    public static final int APPOINTMENT_BY_PATIENT = 7;
    public static final int APPOINTMENT_BY_DOCTOR = 8;
    public static final int APPOINTMENT_BY_STATUS = 9;

    public static final int BILLING_GENERATE = 1;
    public static final int BILLING_GET_BY_ID = 2;
    public static final int BILLING_LIST_ALL = 3;
    public static final int BILLING_BY_APPOINTMENT = 4;
    public static final int BILLING_SUMMARY = 5;

    public static final int SEARCH_PATIENT_NAME = 1;
    public static final int SEARCH_PATIENT_AGE = 2;
    public static final int SEARCH_PATIENT_AGE_RANGE = 3;
    public static final int SEARCH_DOCTOR_KEYWORD = 4;
    public static final int SEARCH_DOCTOR_SPECIALIZATION = 5;
    public static final int SEARCH_DOCTORS_BY_FEE = 6;
    public static final int SEARCH_EXPERIENCED_DOCTORS = 7;
    public static final int SEARCH_APPOINTMENTS_PER_DOCTOR = 8;

    public static final int BILL_TYPE_STANDARD = 1;
    public static final int BILL_TYPE_INSURANCE = 2;
    public static final int BILL_TYPE_PREMIUM = 3;

    private Constants() {
    }
}
