package com.profitsoft.internship.task5.model;

import lombok.Data;

@Data
public class Violation {
    private ViolationType type;
    private double fineAmount;
}
