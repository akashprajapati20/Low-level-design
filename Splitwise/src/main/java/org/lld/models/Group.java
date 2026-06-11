package org.lld.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Group {
    private final  String name;
    private final String groupId;
    private final List<User> members= new ArrayList<>();
    private final  List<Expense> expenses= new ArrayList<>();
    private final Map<User,BalanceSheet> balanceSheetMap =new HashMap<>();


    public Group(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void addMembers(User u){
        members.add(u);
        balanceSheetMap.putIfAbsent(u, new BalanceSheet());
    }

    public List<User> getMembers() {
        return members;
    }

    public void addExpence(Expense e){
        expenses.add(e);
    }

    public Map<User, BalanceSheet> getBalanceSheetMap() {
        return balanceSheetMap;
    }

    public BalanceSheet getBalanceSheetMap(User user) {
        return balanceSheetMap.get(user);
    }
}
