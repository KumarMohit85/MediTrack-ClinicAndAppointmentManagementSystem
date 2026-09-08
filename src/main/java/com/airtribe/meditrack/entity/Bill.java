package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

public class Bill implements Payable {
    private String id;
    private Appointment appointment;
    private double amount;
    private double tax;
    private double total;

    public Bill(String id, Appointment appointment) {
        this.id = id;
        this.appointment = appointment;

    }

    public String getBillId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public double getAmount() {
        return amount;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    public String getDetails() {
        return "Bill ID: " + id + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
                + ", Tax: " + tax + ", Total: " + total;
    }

    @Override
    public Bill generateBill() {
        this.amount = calculateAmount();
        this.tax = calculateTax(amount);
        this.total = amount + tax;
        return this;
    }

    @Override
    public double calculateAmount() {
        return appointment.getDoctor().getConsultationFee();
    }

    @Override
    public double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }

    @Override
    public String getPaymentDetails() {
        return "Bill ID: " + id + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
                + ", Tax: " + tax + ", Total: " + total;
    }
}
