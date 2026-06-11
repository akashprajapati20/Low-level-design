package org.lld;

import org.lld.enums.SplitTypes;
import org.lld.models.Split;
import org.lld.models.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    List<Split> getSplits(double amount, List<User> participants, Map<User,Double> metadata);
}
