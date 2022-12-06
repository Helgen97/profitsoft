package com.profitsoft.internship.task7.testClasses;

import com.profitsoft.internship.task7.annotation.Property;

import java.time.Instant;

public class TestClassWithPrivateDefaultConstructor {

    private String stringProperty;

    @Property(name = "numberProperty")
    private int number;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    private TestClassWithPrivateDefaultConstructor() {

    }

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
