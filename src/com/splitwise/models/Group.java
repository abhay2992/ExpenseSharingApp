package com.splitwise.models;

import java.util.List;

public class Group extends Auditable {
    private String name;
    private User owner;
    private List<User> members;
    private List<Expense> expenses;

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

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
