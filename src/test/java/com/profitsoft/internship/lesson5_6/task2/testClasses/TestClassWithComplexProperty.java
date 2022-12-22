package com.profitsoft.internship.lesson5_6.task2.testClasses;

import com.profitsoft.internship.lesson5_6.task2.annotation.Property;

public class TestClassWithComplexProperty {

    @Property(name = "property.int")
    private int property;

    @Property(name = "numberProperty")
    private Integer integer;

    @Property(name = "number")
    private String string;

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
