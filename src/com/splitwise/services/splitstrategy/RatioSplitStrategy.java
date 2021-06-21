package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class RatioSplitStrategy implements SplitStrategy {
    private final Double totalAmount;
    private final Map<User, Integer> ratios;

    public RatioSplitStrategy(Double totalAmount, Map<User, Integer> ratios) {
        this.totalAmount = totalAmount;
        this.ratios = ratios;
    }

    @Override
    public void calculateOwedAmounts(Expense expense) {
        Map<User, Double> owedAmounts = new HashMap<>();
        int totalRatio = ratios.values().stream().reduce(Integer::sum).orElse(0);
        for(User participant: expense.getParticipants()){
            Double amount = (ratios.get(participant)/totalRatio)*totalAmount;
            owedAmounts.put(participant, amount);
        }
        expense.setOwedAmount(owedAmounts);
    }
}
