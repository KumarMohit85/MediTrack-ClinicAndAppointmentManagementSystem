package com.airtribe.meditrack.pattern.factory;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.pattern.strategy.BillingStrategy;
import com.airtribe.meditrack.pattern.strategy.InsuranceBillingStrategy;
import com.airtribe.meditrack.pattern.strategy.PremiumBillingStrategy;
import com.airtribe.meditrack.pattern.strategy.StandardBillingStrategy;

public class BillFactory {
    public static BillingStrategy createStrategy(BillType type) {
        switch (type) {
            case STANDARD:
                return new StandardBillingStrategy();

            case INSURANCE:
                return new InsuranceBillingStrategy();

            case PREMIUM:
                return new PremiumBillingStrategy();

            default:
                throw new IllegalArgumentException("Unsupported bill type: " + type);
        }
    }

    public Bill createBill(Appointment appointment, BillType type) {
        BillingStrategy strategy = createStrategy(type);
        Bill bill = new Bill("", appointment);
        bill.setAmount(strategy.calculateAmount(appointment));
        return bill.generateBill();
    }
}
