package com.airtribe.meditrack.interfaces;

import com.airtribe.meditrack.entity.Bill;

public interface Payable {
    Bill generateBill();

    String getPaymentDetails();
}
