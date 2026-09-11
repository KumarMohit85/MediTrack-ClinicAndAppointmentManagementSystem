package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;

public class PremiumBillingStrategy implements BillingStrategy {

    @Override
    public double calculateAmount(Appointment appointment) {
        double consultationFee = appointment.getDoctor().getConsultationFee();
        double premiumCharge = 500;

        return consultationFee + premiumCharge;
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
