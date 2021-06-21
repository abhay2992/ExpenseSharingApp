package com.splitwise.services.splitstrategy;

import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public void calculateOwedAmounts(Expense expense) {
        Map<User, Double> owedAmounts = new HashMap<>();
        Double amount = expense.getTotalAmount();
        Double eachShare = amount / expense.getParticipants().size();
        for (User participant : expense.getParticipants()) {
            owedAmounts.put(participant, eachShare);
        }
        expense.setOwedAmount(owedAmounts);
    }
}

