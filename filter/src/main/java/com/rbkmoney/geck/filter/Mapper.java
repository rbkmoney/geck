package com.rbkmoney.geck.filter;


import java.util.function.BiFunction;

/**
 * Created by vpankrashkin on 12.02.18.
 */
public interface Mapper<T, R> {
    R map(T obj);
    R map(T obj, BiFunction<R, R, R> combiner);

    interface Handler<T, R> {
        R handle(T obj, Rule rule);
    }

    interface Builder<T, R> {
        void mapping(Filter<T> filter, Handler<T, R> handler);
        Mapper<T, R> build();
    }
}
