package com.profitsoft.internship.task3;

/**
 *
 * @author Dmytro Donchenko
 */
public class Cube implements Shape {
    
    private final double length;

    public Cube(double length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Negative or zero parameter!");
        }
        this.length = length;
    }
    
    @Override
    public double volume() {
        return Math.pow(length, 3);
    }

    @Override
    public String toString() {
        return volume() + "";
    }
    
    

}
