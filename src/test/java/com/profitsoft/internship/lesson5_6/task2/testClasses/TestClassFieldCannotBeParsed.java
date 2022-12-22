package com.profitsoft.internship.lesson5_6.task2.testClasses;

import com.profitsoft.internship.lesson5_6.task2.annotation.Property;

public class TestClassFieldCannotBeParsed {

    @Property(name = "numberProperty")
    private double thisCannotBeParsed;

    public double getThisCannotBeParsed() {
        return thisCannotBeParsed;
    }

    public void setThisCannotBeParsed(double thisCannotBeParsed) {
        this.thisCannotBeParsed = thisCannotBeParsed;
    }
}
