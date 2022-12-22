package com.profitsoft.internship.lesson1_2.task3;

/**
 * @author Dmytro Donchenko
 */
public class Ball implements Shape {

    private final double radius;

    public Ball(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Negative or zero parameter!");
        }
        this.radius = radius;
    }

    @Override
    public double getVolume() {
        return (4 / 3) * Math.PI * Math.pow(radius, 3);
    }

    @Override
    public String toString() {
        return getVolume() + "";
    }


}
