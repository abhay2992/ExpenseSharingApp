package com.splitwise.services.paymentstretegy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;
import com.splitwise.services.splitstrategy.SplitStrategy;

import java.util.Map;

public class MultipayPaymentStrategy implements PaymentStrategy {
    private final Map<User, Double> paidAmounts;
    public MultipayPaymentStrategy(Map<User, Double> paidAmounts) {
        this.paidAmounts=paidAmounts;
    }

    @Override
    public void calculatePaidAmounts(Expense expense) {
        expense.setPaidAmount(paidAmounts);
    }
}

