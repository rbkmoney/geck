package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Filter;
import com.rbkmoney.geck.filter.rule.ConditionRule;

/**
 * Created by vpankrashkin on 08.02.18.
 */
public class FilterBuilder {
    public static Filter<byte[]> build(String path, Condition condition) {
        SelectorRule rule = new SelectorRule(path, new ConditionRule(condition));
        return new MsgPackFilter(rule);
    }
}
