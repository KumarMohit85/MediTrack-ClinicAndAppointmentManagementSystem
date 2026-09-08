package com.airtribe.meditrack.util;

public class Validator {
    public static boolean validateName(String name) {
        return name != null && !name.isEmpty() && name.matches("^[A-Za-z ]+$");
    }

    public static boolean validatePhone(String phone) {
        return true;
    }

    public static boolean validateEmail(String email) {
        return true;
    }

}
