package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public final class BillSummary {
    private final String billId;
    private final double subtotal;
    private final double tax;
    private final double total;
    private final LocalDateTime generatedAt;

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

    @Override
    public String toString() {
        return "BillSummary{billId='" + billId + "', subtotal=" + subtotal
                + ", tax=" + tax + ", total=" + total + ", generatedAt=" + generatedAt + "}";
    }
}
