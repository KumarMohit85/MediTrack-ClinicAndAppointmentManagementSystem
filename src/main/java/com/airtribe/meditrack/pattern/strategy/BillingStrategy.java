package com.airtribe.meditrack.pattern.strategy;

import com.airtribe.meditrack.entity.Appointment;

public interface BillingStrategy {

    double calculateAmount(Appointment appointment);

    double calculateTax(double amount);

    String getStrategyName();
}