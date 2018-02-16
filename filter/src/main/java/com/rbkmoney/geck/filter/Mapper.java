package com.rbkmoney.geck.filter;


import java.util.function.BiFunction;

/**
 * Created by vpankrashkin on 12.02.18.
 */
public interface Mapper<T, R> {
    <AR> R map(T obj, Object context, Action<T, AR> action, BiFunction<R, AR, R> reducer);

    default <AR> R map(T obj, Object context, BiFunction<R, AR, R> reducer) {
        return map(obj, context, ((T o, Rule r, Object c, Handler<T, ?> h) -> {
            try {
                return (AR) h.handle(o, r, c);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }), reducer);
    }

    default R map(T obj, Object context) {
        return map(obj, context, (r, ar) -> (R) ar);
    }

    interface Handler<T, R> {
        R handle(T obj, Rule rule, Object context) throws Exception;
    }

    interface Builder<RL extends Rule, T, R> {
        void mapping(RL rule, Handler<T, R> handler);

        Mapper<T, R> build();
    }

    interface Action<T, AR> {
        AR perform(T obj, Rule rule, Object context, Handler<T, ?> handler);
    }

}
