package com.splitwise.models;

import java.util.List;

public class User extends Auditable{
    private String fullname;
    private String username;
    private String phone;
    private String hashedSaltedPassword;

    private List<Expense> expenses;
    private List<Group> groups;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHashedSaltedPassword() {
        return hashedSaltedPassword;
    }

    public void setHashedSaltedPassword(String hashedSaltedPassword) {
        this.hashedSaltedPassword = hashedSaltedPassword;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Double getTotalOwedAmount() {
        Double totalOwedAmount = 0.0;
        for (Expense expense : expenses) {
            totalOwedAmount += expense.getOwedAmount().getOrDefault(this, 0.0);
            totalOwedAmount -= expense.getPaidAmount().getOrDefault(this, 0.0);
        }
        return totalOwedAmount;
    }
}

