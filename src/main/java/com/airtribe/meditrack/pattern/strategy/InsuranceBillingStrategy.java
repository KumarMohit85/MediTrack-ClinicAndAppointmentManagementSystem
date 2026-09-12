package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;

public class InsuranceBillingStrategy implements BillingStrategy {

    private final double insuranceCoverage = Constants.INSURANCE_COVERAGE;

    @Override
    public double calculateAmount(Appointment appointment) {
        double consultationFee = appointment.getDoctor().getConsultationFee();
        return consultationFee - (consultationFee * insuranceCoverage);
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
