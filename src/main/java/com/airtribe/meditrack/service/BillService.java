package com.airtribe.meditrack.service;

import java.util.List;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.exception.BillNotFoundException;
import com.airtribe.meditrack.pattern.factory.BillFactory;
import com.airtribe.meditrack.pattern.factory.BillType;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

public class BillService {

    private final DataStore<Bill> billStore = new DataStore<>();
    private final IdGenerator idGenerator = IdGenerator.getInstance();
    private final BillFactory billFactory = new BillFactory();

    public Bill generateBill(Appointment appointment, BillType type) {
        Bill bill = billFactory.createBill(appointment, type);
        bill.setId(idGenerator.generateBillId());
        Validator.validateBill(bill);
        billStore.add(bill);
        return bill;
    }

    public Bill getBillById(String id) {
        Bill bill = billStore.getById(id);
        if (bill == null) {
            throw new BillNotFoundException("No bill found with ID: " + id);
        }
        return bill;
    }

    public List<Bill> getAllBills() {
        return billStore.getAll();
    }

    public Bill getBillByAppointment(String appointmentId) {
        for (Bill bill : billStore.getAll()) {
            if (bill.getAppointment() != null
                    && bill.getAppointment().getAppointmentId().equals(appointmentId)) {
                return bill;
            }
        }
        throw new BillNotFoundException("No bill found for appointment: " + appointmentId);
    }

    public BillSummary getBillSummary(String billId) {
        return getBillById(billId).getSummary();
    }

    public void restoreBill(Bill bill) {
    if (bill == null) {
        return;
    }

    if (bill.getBillId() == null || bill.getBillId().isBlank()) {
        return;
    }

    if (billStore.exists(bill.getBillId())) {
        return;
    }

    billStore.add(bill);
}
}
