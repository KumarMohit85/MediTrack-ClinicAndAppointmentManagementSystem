package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;

import java.time.LocalDateTime;

public class Bill extends MedicalEntity implements Payable {
    private Appointment appointment;
    private double amount;
    private double tax;
    private double total;
    private LocalDateTime generatedAt;

    public Bill(String id, Appointment appointment) {
        super(id, LocalDateTime.now());
        this.appointment = appointment;
    }

    public String getBillId() {
        return getId();
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

    public double calculateAmount() {
        return amount;
    }

    public double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }

    public BillSummary getSummary() {
        return new BillSummary(getBillId(), this.amount, tax, total, generatedAt);
    }

    public String getDetails() {
        return "Bill ID: " + getId() + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
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

    public void setGeneratedAt(LocalDateTime generatedAt) {
    this.generatedAt = generatedAt;
}

    @Override
    public Bill generateBill() {
        this.tax = calculateTax(calculateAmount());
        this.total = this.amount + this.tax;
        this.generatedAt = LocalDateTime.now();
        return this;
    }


    @Override
    public String getPaymentDetails() {
        return "Bill ID: " + getId() + ", Appointment: " + appointment.getAppointmentId() + ", Amount: " + amount
                + ", Tax: " + tax + ", Total: " + total;
    }
}
