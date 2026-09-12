package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;

public class PremiumBillingStrategy implements BillingStrategy {

    private final double premiumCharge = Constants.PREMIUM_CHARGE;

    @Override
    public double calculateAmount(Appointment appointment) {
        return appointment.getDoctor().getConsultationFee() + premiumCharge;
    }

    @Override
    public double calculateTax(double amount) {
        return amount * Constants.TAX_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Premium Billing Strategy";
    }
}
