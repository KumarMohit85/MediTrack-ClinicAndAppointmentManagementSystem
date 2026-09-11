package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

import java.time.LocalDateTime;

public class Bill implements Payable {
    private String id;
    private Appointment appointment;
    private double amount;
    private double tax;
    private double total;
    private LocalDateTime generatedAt;

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
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public String getDetails() {
        return "Bill ID: " + id + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
                + ", Tax: " + tax + ", Total: " + total;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    @Override
    public Bill generateBill() {
        this.total = this.amount + this.tax;
        this.generatedAt = LocalDateTime.now();
        return this;
    }


    @Override
    public String getPaymentDetails() {
        return "Bill ID: " + id + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
                + ", Tax: " + tax + ", Total: " + total;
    }
}
