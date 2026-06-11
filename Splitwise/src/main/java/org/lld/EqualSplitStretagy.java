package org.lld;

import org.lld.enums.SplitTypes;
import org.lld.models.Split;
import org.lld.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EqualSplitStretagy implements SplitStrategy {
    @Override
    public List<Split> getSplits(double amount, List<User> participants, Map<User,Double> metadata) {
          double ekKaamount= amount / participants.size();
          List<Split>all_splits=new ArrayList<>();
          for(User u:participants){
              all_splits.add(new Split(ekKaamount,u));
          }
        return all_splits;
    }
}
