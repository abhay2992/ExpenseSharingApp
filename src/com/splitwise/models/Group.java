package com.splitwise.models;

import java.util.List;
import java.util.Set;

public class Group extends Auditable {
    private String name;
    private User owner;
    private Set<User> members;
    private Set<Expense> expenses;

    public Group(User currenltyLoggedInUser, String name) {
        this.owner = currenltyLoggedInUser;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
}
