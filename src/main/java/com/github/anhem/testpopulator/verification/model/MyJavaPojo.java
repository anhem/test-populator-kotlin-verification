package com.github.anhem.testpopulator.verification.model;

public class MyJavaPojo {
    private final String stringValue;
    private final int intValue;

    public MyJavaPojo(String stringValue, int intValue) {
        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
