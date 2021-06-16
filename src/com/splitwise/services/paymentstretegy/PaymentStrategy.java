package com.splitwise.services.paymentstretegy;

import com.splitwise.models.Expense;

public interface PaymentStrategy {
    void calculatePaidAmounts(Expense expense);
}

