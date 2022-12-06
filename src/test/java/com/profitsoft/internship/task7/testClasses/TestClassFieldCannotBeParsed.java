package com.profitsoft.internship.task7.testClasses;

import com.profitsoft.internship.task7.annotation.Property;

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
