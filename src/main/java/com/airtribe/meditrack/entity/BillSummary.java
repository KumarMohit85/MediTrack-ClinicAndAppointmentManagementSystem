package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class BillSummary {
    private String billId;
    private double subtotal;
    private double tax;
    private double total;
    private LocalDateTime generatedAt;

    public BillSummary(String billId, double subtotal, double tax, double total, LocalDateTime generatedAt) {
        this.billId = billId;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
        this.generatedAt = generatedAt;
    }

    public String getBillId() {
        return billId;
    }

    public double getSubtotal() {
        return subtotal;
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

}
