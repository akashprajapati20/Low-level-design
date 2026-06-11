package org.lld.services;

import org.lld.SplitFactory;
import org.lld.SplitStrategy;
import org.lld.enums.SplitTypes;
import org.lld.models.Expense;
import org.lld.models.Group;
import org.lld.models.Split;
import org.lld.models.User;

import java.util.List;
import java.util.Map;

public class ExpenseService {
    private final BalanceSheetService balanceSheetService;

    public ExpenseService(BalanceSheetService balanceSheetService) {
        this.balanceSheetService = balanceSheetService;
    }

    public void addExpense(Group g,  String des, double amount, User paidBy,
                           List<User> participants, SplitTypes splitTypes, Map<User,Double> meta){

      SplitStrategy splitStrategy= SplitFactory.getSplitStrategy(splitTypes);
      List<Split>splits=splitStrategy.getSplits(amount,participants,meta);
        Expense expense=new Expense(paidBy,des,amount,splitTypes,splits);
        g.addExpence(expense);
        balanceSheetService.updateBalances(g,paidBy,splits);

    };
}
