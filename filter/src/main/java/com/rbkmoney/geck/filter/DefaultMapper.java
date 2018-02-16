package com.rbkmoney.geck.filter;

import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by vpankrashkin on 12.02.18.
 */
public class DefaultMapper<T, R> implements Mapper<T, R> {
    private final Map<? extends Rule, Handler<T, R>> mappings;
    private final List<Filter<T>> filters;

    protected DefaultMapper(Map<? extends Rule, Handler<T, R>> mappings, List<Filter<T>> filters) {
        this.mappings = mappings;
        this.filters = filters;
    }

    @Override
    public <AR> R map(T obj, Object context, Action<T, AR> action, BiFunction<R, AR, R> reducer) {
        R result = null;
        for (int i = 0; i < filters.size(); ++i) {
            List<Rule> rules = filters.get(i).matchRules(obj);
            for (int j = 0; j < rules.size(); j++) {
                Rule rule = rules.get(j);
                Handler<T, R> handler = mappings.get(rule);
                if (handler != null) {
                    result = reducer.apply(result, action.perform(obj, rule, context, handler));
                }
            }
        }
        return result;
    }

    abstract protected static class Builder<RL extends Rule, T, R> implements Mapper.Builder<RL, T, R> {
        protected final Map<RL, Handler<T, R>> mappings = new LinkedHashMap<>();

        @Override
        public void mapping(RL rule, Handler<T, R> handler) {
            mappings.put(rule, handler);
        }

        @Override
        public Mapper build() {
            return new DefaultMapper(mappings, toFilters(mappings.keySet()));
        }

        abstract protected List<Filter<T>> toFilters(Collection<RL> rules);
    }
}
