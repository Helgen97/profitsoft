package com.profitsoft.internship.lesson3_4.task2.model;

import lombok.Data;

@Data
public class Violation {
    private ViolationType type;
    private double fineAmount;
}
