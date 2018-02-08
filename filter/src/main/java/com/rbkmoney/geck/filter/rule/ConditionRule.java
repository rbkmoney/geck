package com.rbkmoney.geck.filter.rule;

import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Rule;

/**
 * Created by vpankrashkin on 22.09.17.
 */
public class ConditionRule implements Rule {
    private final Condition[] conditions;

    public ConditionRule(Condition condition) {
        this(new Condition[]{condition}, false);
    }

    public ConditionRule(Condition[] conditions) {
        this(conditions, true);
    }


    private ConditionRule(Condition[] conditions, boolean copy) {
        if (copy) {
            Condition[] arrCopy = new Condition[conditions.length];
            System.arraycopy(conditions, 0, arrCopy, 0, conditions.length);
            this.conditions = arrCopy;
        } else {
            this.conditions = conditions;
        }
    }

    @Override
    public Condition[] getConditions() {
        return conditions;
    }
}
