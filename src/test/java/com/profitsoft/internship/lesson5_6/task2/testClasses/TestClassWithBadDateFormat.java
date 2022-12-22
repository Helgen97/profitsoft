package com.profitsoft.internship.lesson5_6.task2.testClasses;

import com.profitsoft.internship.lesson5_6.task2.annotation.Property;

import java.time.Instant;

public class TestClassWithBadDateFormat {

    @Property(format = "dd.MM HH:mm")
    private Instant timeProperty;

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
