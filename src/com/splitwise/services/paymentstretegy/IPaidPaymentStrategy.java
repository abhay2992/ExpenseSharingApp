package com.splitwise.services.paymentstretegy;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.models.Expense;
import com.splitwise.models.User;

import java.util.HashMap;
import java.util.Map;

public class IPaidPaymentStrategy implements PaymentStrategy {
    private final User user;
    private final Double amount;

    public IPaidPaymentStrategy(User user, Double amount) {
        this.user = user;
        this.amount = amount;
    }

    @Override
    public void calculatePaidAmounts(Expense expense) {
        Map<User, Double> paidAmounts=new HashMap<>();
        paidAmounts.put(user, amount);
        for(User participant: expense.getParticipants()){
            if(participant.equals(user))
                continue;
            paidAmounts.put(participant, 0.0);
        }
        expense.setPaidAmount(paidAmounts);
    }
}

