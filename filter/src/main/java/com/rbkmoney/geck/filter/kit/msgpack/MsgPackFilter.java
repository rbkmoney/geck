package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Filter;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;

import java.io.IOException;
import java.util.List;
/**
 * Created by vpankrashkin on 13.09.17.
 */
class MsgPackFilter implements Filter<byte[]> {
    private final SelectorRule rule;

    public MsgPackFilter(SelectorRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean match(byte[] value) {
        return matchRule(value) != null;
    }

    @Override
    public Rule matchRule(byte[] value) {
        List<Rule> matched = matchRules(value);
        return matched.size() > 0 ? matched.get(0) : null;
    }

    @Override
    public List<Rule> matchRules(byte[] value) {
        StructVisitor visitor = new StructVisitor(() -> rule.getConfigs());
        try {
            return MsgPackProcessor.newBinaryInstance().process(value, new FilteringHandler(visitor));
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    public static class Builder implements Filter.Builder<SelectorRule, byte[]> {

        public static Filter<byte[]> buildInstance(String path, Rule rule) {
            return new MsgPackFilter(new SelectorRule(path, rule));
        }

        public static Filter<byte[]> buildInstance(SelectorRule rule) {
            return new MsgPackFilter(rule);
        }

        public Filter<byte[]> build(SelectorRule rule) {
            return buildInstance(rule);
        }
    }
}
