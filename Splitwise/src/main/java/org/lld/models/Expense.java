package org.lld.models;

import org.lld.enums.SplitTypes;

import java.util.ArrayList;
import java.util.List;


public class Expense {
    User paidBy;
    String desription;
    double amount;
    List<Split> splits;
    SplitTypes splitTypes;

    public Expense(User paidBy, String desc, double amount, SplitTypes splitTypes,List<Split> splits){
        this.paidBy=paidBy;
        this.desription=desc;
        this.amount=amount;
        this.splitTypes=splitTypes;
        this.splits=splits;
    }

    public User getpaidBy() {
        return paidBy;
    }

    public SplitTypes getSplitTypes() {
        return splitTypes;
    }

    public String getDesription() {
        return desription;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public double getAmount() {
        return amount;
    }
}
