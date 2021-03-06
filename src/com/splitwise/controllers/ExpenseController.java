package com.splitwise.controllers;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.notfound.GroupNotFoundException;
import com.splitwise.exceptions.notfound.UserNotFoundException;
import com.splitwise.models.Expense;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.ExpenseRepository;
import com.splitwise.repositories.GroupRepository;
import com.splitwise.repositories.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.paymentstretegy.PaymentStrategy;
import com.splitwise.services.splitstrategy.SplitStrategy;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExpenseController {
    UserRepository userRepository;
    ExpenseRepository expenseRepository;
    GroupRepository groupRepository;

    public ExpenseController(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository=groupRepository;
    }

    public Expense createExpenseWithUsers(AuthenticationContext authenticationContext,
                                       List<Long> participantIds,
                                       Date date,
                                       String description,
                                       PaymentStrategy paymentStrategy,
                                       SplitStrategy splitStrategy
    ) {
        User currentlyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User Needs to login to create expense"));
        Set<User> participants = participantIds
                .stream()
                .map((id) -> userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id.toString())))
                .collect(Collectors.toSet());
        participants.add(currentlyLoggedInUser);
        Expense expense = new Expense(date,
                description,
                participants);

        paymentStrategy.calculatePaidAmounts(expense);
        splitStrategy.calculateOwedAmounts(expense);

        expenseRepository.save(expense);
        return expense;
    }

    public Expense createExpenseWithGroup(AuthenticationContext authenticationContext,
                                       Long groupId,
                                       Date date,
                                       String descriptiton,
                                       PaymentStrategy paymentStrategy,
                                       SplitStrategy splitStrategy) {
        User currentlyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User Needs to login to create expense"));
        Group group= groupRepository
                .findById(groupId)
                .orElseThrow(()-> new GroupNotFoundException(groupId.toString()));
        Set<User> participants = group.getMembers();

        if(!participants.contains(currentlyLoggedInUser))
            throw new GroupNotFoundException("You are not a member of this group");

        Expense expense= new Expense(date, descriptiton, participants);

        paymentStrategy.calculatePaidAmounts(expense);
        splitStrategy.calculateOwedAmounts(expense);

        expense.setGroup(group);

        expenseRepository.save(expense);
        return expense;
    }
}
