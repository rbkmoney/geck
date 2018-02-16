package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.DefaultMapper;
import com.rbkmoney.geck.filter.Filter;
import com.rbkmoney.geck.filter.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vpankrashkin on 13.02.18.
 */
public class MsgPackMapper<R> extends DefaultMapper<byte[], R> {
    private MsgPackMapper(Map<SelectorRule,  Handler<byte[], R>> mappings, List<Filter<byte[]>> filters) {
        super(mappings, filters);
    }

    public static class Builder<R> extends DefaultMapper.Builder<SelectorRule, byte[], R> {
        public void mapping(SelectorRule rule, Handler<byte[], R> handler) {
            mapping(rule, handler);
        }

        @Override
        public Mapper build() {
            return new MsgPackMapper(mappings, toFilters(mappings.keySet()));
        }

        @Override
        protected List<Filter<byte[]>> toFilters(Collection<SelectorRule> rules) {
            return rules.stream().map(r -> MsgPackFilter.Builder.buildInstance(r)).collect(Collectors.toList());
        }
    }
}
