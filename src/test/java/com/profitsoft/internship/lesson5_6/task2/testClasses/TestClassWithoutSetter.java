package com.profitsoft.internship.lesson5_6.task2.testClasses;

import java.time.Instant;

public class TestClassWithoutSetter {

    private String stringProperty;

    private int numberProperty;

    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public int getNumberProperty() {
        return numberProperty;
    }

    public void setNumberProperty(int numberProperty) {
        this.numberProperty = numberProperty;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
