package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.DefaultMapper;
import com.rbkmoney.geck.filter.Filter;
import com.rbkmoney.geck.filter.Rule;

import java.util.List;
import java.util.Map;

/**
 * Created by vpankrashkin on 13.02.18.
 */
public class MsgPackMapper<R> extends DefaultMapper<byte[], R> {
    private MsgPackMapper(List<Map.Entry<Filter<byte[]>, Handler<byte[], R>>> mappings) {
        super(mappings);
    }

    public static class Builder<R> extends DefaultMapper.Builder<byte[], R> {
        public void mapping(String path, Rule rule, Handler<byte[], R> handler) {
            mapping(MsgPackFilter.Builder.buildInstance(path, rule), handler);
        }}
}
