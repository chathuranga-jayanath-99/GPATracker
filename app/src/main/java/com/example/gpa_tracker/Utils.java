package com.example.gpa_tracker;

public class Utils {
    public static float roundToTwoDecimalPlaces(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }
}
