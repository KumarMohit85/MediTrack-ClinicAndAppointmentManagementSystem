package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.exception.BillNotFoundException;
import com.airtribe.meditrack.pattern.factory.BillFactory;
import com.airtribe.meditrack.pattern.factory.BillType;
import com.airtribe.meditrack.pattern.strategy.BillingStrategy;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.*;

public class BillService {
    private final List<Bill> bills = new ArrayList<>();
    public Bill generateBill(Appointment appointment, BillType billType) {

        BillingStrategy strategy = BillFactory.createStrategy(billType);

        double amount = strategy.calculateAmount(appointment);

        double tax = strategy.calculateTax(amount);
        String billId= IdGenerator.generateBillId();

        Bill bill = new Bill(billId, appointment);


        bill.setAmount(amount);
        bill.setTax(tax);

        bill.generateBill();
        bills.add(bill);
        return bill;
    }
    public Bill getBillById(String billId) {

        for (Bill bill : bills) {
            if (bill.getBillId().equals(billId)) {
                return bill;
            }
        }

        throw new BillNotFoundException("No bill found with ID: " + billId);
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }

    public Bill getBillByAppointment(Appointment appointment) {

        for (Bill bill : bills) {
            if (bill.getAppointment().equals(appointment)) {
                return bill;
            }
        }

        throw new BillNotFoundException("No bill found for appointment: " + appointment.getAppointmentId());
    }

    public BillSummary getBillSummary(String billId) {

        Bill bill = getBillById(billId);

        return new BillSummary(
                bill.getBillId(),
                bill.getAmount(),
                bill.getTax(),
                bill.getTotal(),
                bill.getGeneratedAt()
        );
    }

}
