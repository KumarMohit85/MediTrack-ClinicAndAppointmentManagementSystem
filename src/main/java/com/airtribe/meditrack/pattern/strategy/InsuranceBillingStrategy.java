package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;

public class InsuranceBillingStrategy implements BillingStrategy {

    @Override
    public double calculateAmount(Appointment appointment) {
        double consultationFee = appointment.getDoctor().getConsultationFee();

        double insuranceCoverage = consultationFee * 0.20;

        return consultationFee - insuranceCoverage;
    }

    @Override
    public double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Insurance Billing Strategy";
    }
}
