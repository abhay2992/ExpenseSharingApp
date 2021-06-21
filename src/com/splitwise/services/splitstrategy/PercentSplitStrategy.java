package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class PercentSplitStrategy implements SplitStrategy {
    private final Double totalAmount;
    private final Map<User, Double> percentages;

    public PercentSplitStrategy(Double totalAmount, Map<User, Double> percentages) {
        this.totalAmount = totalAmount;
        this.percentages = percentages;
    }

    @Override
    public void calculateOwedAmounts(Expense expense) {
        Map<User, Double> owedAmounts = new HashMap<>();
        for(User participant: expense.getParticipants()){
            Double amount=percentages.get(participant)*totalAmount/100.0;
            owedAmounts.put(participant, amount);
        }
        expense.setOwedAmount(owedAmounts);
    }
}
