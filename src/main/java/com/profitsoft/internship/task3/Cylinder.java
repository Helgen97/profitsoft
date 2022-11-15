package com.profitsoft.internship.task3;

/**
 * @author Dmytro Donchenko
 */
public class Cylinder implements Shape {

    private final double radius;
    private final double height;

    public Cylinder(double radius, double height) {
        if (radius <= 0 || height <= 0)
            throw new IllegalArgumentException("Negative or zero parameters!");
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double getVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }

    @Override
    public String toString() {
        return getVolume() + "";
    }


}
