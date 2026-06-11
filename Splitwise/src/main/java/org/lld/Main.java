package org.lld;

import org.lld.enums.SplitTypes;
import org.lld.models.User;
import org.lld.repo.GroupRepo;
import org.lld.services.*;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        User subh=new User("Subh","u1");
        User bob=new User("bob","u2");
        User tom=new User("tom","u3");
        User joe=new User("joe","u4");

        GroupRepo groupRepo=new GroupRepo();
        BalanceSheetService balanceSheetService=new BalanceSheetService();
        ExpenseService expenseService=new ExpenseService(balanceSheetService);
        SimplifyDebtsService simplifyDebtsService=new SimplifyDebtsService();
        SplitService splitService=new SplitService();

        GroupService groupService =new GroupService(groupRepo,simplifyDebtsService,splitService,expenseService);

String goaGorupId= groupService.createGroup("Goa Trip", List.of(subh,bob,tom));
groupService.addExpense(goaGorupId,"Dinner",1000,subh,List.of(tom,bob), SplitTypes.EQUAL,null);
groupService.addExpense(goaGorupId,"Dinner-2",150,tom,List.of(tom,bob), SplitTypes.EQUAL,null);

        System.out.println("Before Simplify............................");
        groupService.printBalances(goaGorupId);

        groupService.simplifyDebts(goaGorupId);

        System.out.println("After  Simplify............................");

        groupService.printBalances(goaGorupId);

    }
}