package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.rule.ConditionRule;

/**
 * Created by vpankrashkin on 08.02.18.
 */
class SelectorRule extends ConditionRule {
    private final Selector.Config configs[];

    SelectorRule(String path, Rule rule) {
        super(rule.getConditions());
        this.configs = new SelectorParser().parse(path, rule);
    }

    Selector.Config[] getConfigs() {
        return configs;
    }
}
