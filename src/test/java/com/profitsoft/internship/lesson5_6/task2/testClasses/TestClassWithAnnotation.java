package com.profitsoft.internship.lesson5_6.task2.testClasses;

import com.profitsoft.internship.lesson5_6.task2.annotation.Property;

import java.time.Instant;

public class TestClassWithAnnotation {

    private String stringProperty;

    @Property(name = "numberProperty")
    private int number;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
