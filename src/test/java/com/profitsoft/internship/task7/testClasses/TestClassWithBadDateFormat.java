package com.profitsoft.internship.task7.testClasses;

import com.profitsoft.internship.task7.annotation.Property;

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
