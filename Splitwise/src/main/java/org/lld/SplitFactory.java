package org.lld;

import jdk.jfr.Percentage;
import org.lld.enums.SplitTypes;

public class SplitFactory {


    public static SplitStrategy getSplitStrategy(SplitTypes splitTypes) {
        switch (splitTypes){
            case EQUAL -> {
                return new EqualSplitStretagy();
            }
            case PERCENTAGE -> {
                return new PercentageStrategy();
            }
            default -> throw new RuntimeException("not correct splitTypes");
        }

    }
}
