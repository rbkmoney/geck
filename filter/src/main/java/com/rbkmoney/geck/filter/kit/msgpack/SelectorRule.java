package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.rule.ConditionRule;

/**
 * Created by vpankrashkin on 08.02.18.
 */
class SelectorRule extends ConditionRule {
    private final Selector.Config configs[];
    private final Rule valueRule;

    SelectorRule(String path, Rule valueRule) {
        super(valueRule.getConditions());
        this.valueRule = valueRule;
        this.configs = new SelectorParser().parse(path, valueRule);
    }

    Rule getValueRule() {
        return valueRule;
    }

    Selector.Config[] getConfigs() {
        return configs;
    }
}
