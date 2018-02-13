package com.rbkmoney.geck.filter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by vpankrashkin on 12.02.18.
 */
public class DefaultMapper<T, R> implements Mapper<T, R> {
    private final List<Map.Entry<Filter<T>, Handler<T, R>>> mappings;

    protected DefaultMapper(List<Map.Entry<Filter<T>, Handler<T, R>>> mappings) {
        this.mappings = mappings;
    }

    @Override
    public R map(T obj) {
        return map(obj, (acc, tmp) -> tmp);
    }

    @Override
    public R map(T obj, BiFunction<R, R, R> combiner) {
        R result = null;
        for (int i = 0; i < mappings.size(); ++i) {
            Map.Entry<Filter<T>, Handler<T, R>> mapping = mappings.get(i);
            List<Rule> rules = mapping.getKey().matchRules(obj);
            for (int j = 0; j < rules.size(); j++) {
                result = combiner.apply(result, mapping.getValue().handle(obj, rules.get(j)));
            }
        }
        return result;
    }

    protected static class Builder<T, R> implements Mapper.Builder<T, R> {
        private final List<Map.Entry<Filter<T>, Handler<T, R>>> mappings = new ArrayList<>();

        @Override
        public void mapping(Filter<T> filter, Handler<T, R> handler) {
            mappings.add(new AbstractMap.SimpleImmutableEntry<>(filter, handler));
        }

        @Override
        public Mapper build() {
            return new DefaultMapper(mappings);
        }
    }
}
