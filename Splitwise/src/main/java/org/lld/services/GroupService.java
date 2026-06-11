package org.lld.services;

import org.lld.SplitFactory;
import org.lld.SplitStrategy;
import org.lld.enums.SplitTypes;
import org.lld.models.BalanceSheet;
import org.lld.models.Expense;
import org.lld.models.Group;
import org.lld.models.User;
import org.lld.repo.GroupRepo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GroupService {
    private GroupRepo groupRepo;
    private SimplifyDebtsService simplifyDebtsService;
    private SplitService splitService;
private ExpenseService expenseService;

    public GroupService(GroupRepo groupRepo, SimplifyDebtsService simplifyDebtsService, SplitService splitService, ExpenseService expenseService) {
        this.groupRepo = groupRepo;
        this.simplifyDebtsService = simplifyDebtsService;
        this.splitService = splitService;
        this.expenseService = expenseService;
    }

    public String createGroup(String name , List<User> members){
        String id= UUID.randomUUID().toString();
      Group group = new Group(name,id);
        for (User member : members) {
            group.addMembers(member);
        }
        groupRepo.addGroup(group);
        return id;
    }

    public void addExpense(String groupId, String des, double amount, User paidBy,
                           List<User>participants, SplitTypes splitTypes, Map<User,Double>meta){
        expenseService.addExpense(get(groupId),des,amount,paidBy,participants,splitTypes,meta);

    }
    private Group get(String id) {
        return groupRepo.getGroup(id).orElseThrow(()->new IllegalArgumentException("not found"));
    }

    public void simplifyDebts(String groupId){
        simplifyDebtsService.simplify(get(groupId));
    }
    public void printBalances(String groupId){
        Group group = get(groupId);
        System.out.println("----- Balances for group: " + groupId + " -----");
        boolean anyBalance = false;
        for (User member : group.getMembers()) {
            BalanceSheet sheet = group.getBalanceSheetMap(member);
            if (sheet == null) continue;
            for (Map.Entry<User, Double> entry : sheet.getBalances().entrySet()) {
                User other = entry.getKey();
                double amount = entry.getValue();
                if (amount < 0) {
                    System.out.printf("%s owes %s: %.2f%n",
                            member.getName(), other.getName(), -amount);
                    anyBalance = true;
                }
            }
        }
        if (!anyBalance) {
            System.out.println("All settled up, no balances.");
        }
    }
}
