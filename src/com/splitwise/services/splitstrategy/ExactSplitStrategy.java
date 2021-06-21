package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {
    private final Map<User, Double> owedAmounts;

    public ExactSplitStrategy(Map<User, Double> owedAmounts) {
        this.owedAmounts = owedAmounts;
    }

    @Override
    public void calculateOwedAmounts(Expense expense) {
        expense.setOwedAmount(owedAmounts);
    }
}

