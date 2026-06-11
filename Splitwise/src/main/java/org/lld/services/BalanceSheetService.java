package org.lld.services;

import org.lld.models.Group;
import org.lld.models.Split;
import org.lld.models.User;

import java.util.List;

public class BalanceSheetService {
    public void updateBalances(Group g, User paidBy, List<Split> splits) {
        double amount = splits.stream().mapToDouble(Split::getAmount).sum();
        g.getBalanceSheetMap(paidBy).addToTotalPaid(amount);

        for(Split split:splits){
            User user=split.getUser();
            double amt=split.getAmount();
            g.getBalanceSheetMap(user).addToTotalExpenditure(amt);
            if(!user.equals(paidBy)){
                g.getBalanceSheetMap(user).addToBalances(paidBy,-amt);
                g.getBalanceSheetMap(paidBy).addToBalances(user,amt);
            }
        }
    }
}
