package org.lld.services;

import org.lld.models.BalanceSheet;
import org.lld.models.Group;
import org.lld.models.User;

import java.util.*;

public class SimplifyDebtsService {
    public void simplify(Group group) {

        List<User> users=new ArrayList<>(group.getMembers());
        Map<User, BalanceSheet> allBalanceSheets=group.getBalanceSheetMap();
        Map<User,Double> netBalance=new HashMap<>();

        for(User u:users){
            double net=0.0;
            Map<User,Double> sheet=allBalanceSheets.get(u).getBalances();
        for(double amt:sheet.values()){
        net+=amt;
        }
        netBalance.put(u,net);
        allBalanceSheets.get(u).clearBalances();
        }

        PriorityQueue<User> creditors = new PriorityQueue<>(
                        (a, b) -> Double.compare(
                                netBalance.get(b),  // larger positive first
                                netBalance.get(a)
                        )
                );

        PriorityQueue<User> debtors =
                new PriorityQueue<>(
                        (a, b) -> Double.compare(
                                netBalance.get(a),  // more negative first
                                netBalance.get(b)
                        )
                );

        for
        (User user : users) {
            double net = netBalance.get(user);
            if (net > 0) {
                creditors.offer (user);
            } else if (net < 0) {
                debtors.offer(user);
            }
        }

        // Step 3: Match debtors and creditors to settle debts
        while (!creditors.isEmpty() && !debtors.isEmpty()) {
            User creditor = creditors.poll();
            User debtor = debtors.poll();
            double creditAmount = netBalance.get(creditor);
            double debitAmount = netBalance.get(debtor);
            double settledAmount = Math.min(creditAmount, -debitAmount);
          // Update balances both sides
            allBalanceSheets.get(creditor).addToBalances(debtor, settledAmount);
            allBalanceSheets.get(debtor).addToBalances(creditor, -settledAmount);

            netBalance.put(creditor, creditAmount - settledAmount);
            netBalance.put(debtor, debitAmount + settledAmount);

            //if still unsettle enter in queue again
            if(netBalance.get(creditor)>0){
                creditors.offer(creditor);
            }
            if(netBalance.get(debtor)<0){
                debtors.offer(debtor);
            }

        }
    }
}
