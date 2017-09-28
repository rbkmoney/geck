package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;

import java.util.*;
import java.util.function.Function;

import static com.rbkmoney.geck.filter.kit.msgpack.SelectorParser.State.*;

/**
 * Created by vpankrashkin on 21.09.17.
 */
public class SelectorParser {
    enum State {
        READY,
        NAME,
        ANY_NAME,
        ANY_ELEM,
        LEVEL
    }

    public Map.Entry<Selector, Selector.Context[]> parse(String line, Rule valueMatcher) throws IllegalArgumentException {
        List<Selector.Context> contexts = new ArrayList<>();
        ObjectStack<SelectorInitializer> selectorStack = new ObjectStack<>(null);
        State state = READY;
        StringBuilder buffer = new StringBuilder();
        int level = 0;
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            switch (c) {
                case '*':
                    if (!(state == READY || state == LEVEL)) {
                        throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", state, ANY_NAME));
                    } else {
                        selectorStack.push(new SelectorInitializer(createAnyNameFunc(level++), State.ANY_NAME));
                        state = ANY_NAME;
                    }
                    break;
                case '.':
                    if (state != ANY_NAME) {
                        if (state != NAME) {
                            throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", state, LEVEL));
                        } else {
                            selectorStack.push(new SelectorInitializer(createNameFunc(getAndReset(buffer), level++), NAME));
                        }
                    }
                    state = LEVEL;
                    break;
                default:
                    if (state == ANY_NAME) {
                        throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", state, NAME));
                    }
                    buffer.append(c);
                    state = NAME;
                    break;
            }
        }
        if (state == NAME) {
            selectorStack.push(new SelectorInitializer(createNameFunc(getAndReset(buffer), level++), NAME));
        }
        selectorStack.push(new SelectorInitializer(createValueFunc(valueMatcher, level++), null));

        if (selectorStack.size() == 0) {
            throw new IllegalArgumentException("No selections found");
        }
        SelectorInitializer initializer = selectorStack.pop();
        Selector nextSelector = initializer.selectorCreator.apply(null);
        contexts.add(nextSelector.createContext());
        while (selectorStack.size() > 0) {
            nextSelector = selectorStack.pop().selectorCreator.apply(nextSelector);
            contexts.add(nextSelector.createContext());
        }
        Collections.reverse(contexts);
        return new AbstractMap.SimpleImmutableEntry<>(nextSelector, contexts.toArray(new Selector.Context[contexts.size()]));
    }

    private String getAndReset(StringBuilder builder) {
        String data = builder.toString();
        builder.setLength(0);
        return data;
    }


    private NameSelectorFunc createAnyNameFunc(int level) {
        return new NameSelectorFunc(new ConditionRule(obj -> true), Selector.Type.REPEATABLE, level);
    }

    private NameSelectorFunc createNameFunc(String name, int level) {
        return new NameSelectorFunc(new ConditionRule(new EqualsCondition(name)), Selector.Type.REPEATABLE, level);
    }

    private ValueSelectorFunc createValueFunc(Rule rule, int level) {
        return new ValueSelectorFunc(rule, Selector.Type.UNREPEATABLE, level);
    }

    private static class SelectorInitializer {
        private final Function<Selector, Selector> selectorCreator;
        private final State state;

        public SelectorInitializer(Function<Selector, Selector> selectorCreator, State state) {
            this.selectorCreator = selectorCreator;
            this.state = state;
        }

        public State getState() {
            return state;
        }
    }

    private abstract static class SelectorFunc implements Function<Selector, Selector> {
        final Selector.Type type;
        final Rule rule;
        final int level;

        public SelectorFunc(Rule rule, Selector.Type type, int level) {
            this.rule = rule;
            this.type = type;
            this.level = level;
        }

    }


    private static class NameSelectorFunc extends SelectorFunc {
        public NameSelectorFunc(Rule rule, Selector.Type type, int level) {
            super(rule, type, level);
        }

        @Override
        public Selector apply(Selector selector) {
            return new NameSelector(rule, selector, type, level);
        }
    }

    private static class ValueSelectorFunc extends SelectorFunc {
        public ValueSelectorFunc(Rule rule, Selector.Type type, int level) {
            super(rule, type, level);
        }

        @Override
        public Selector apply(Selector selector) {
            return new ValueSelector(rule, selector, type, level);
        }
    }

}
