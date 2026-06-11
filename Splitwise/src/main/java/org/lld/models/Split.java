package org.lld.models;

public class Split {
    double amount;
    User user;

    public Split(double amount, User user) {
        this.amount = amount;
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }
}
