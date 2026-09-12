package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;

public class StandardBillingStrategy implements BillingStrategy {

    @Override
    public double calculateAmount(Appointment appointment) {
        return appointment.getDoctor().getConsultationFee();
    }

    @Override
    public double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Standard Billing";
    }
}
