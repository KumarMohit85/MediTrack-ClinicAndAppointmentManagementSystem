package com.airtribe.meditrack.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate parseDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(date.trim(), DATE_FORMATTER);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null || dateTime.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTime.trim(), DATE_TIME_FORMATTER);
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static boolean isPastDate(LocalDate date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDate.now());
    }

    public static boolean isPast(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return dateTime.isBefore(LocalDateTime.now());
    }
}
