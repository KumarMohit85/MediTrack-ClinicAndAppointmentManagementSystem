package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger billCounter= new AtomicInteger(1);
    public static String generateBillId(){
        return "BILL- "+ billCounter.getAndIncrement();
    }

}
