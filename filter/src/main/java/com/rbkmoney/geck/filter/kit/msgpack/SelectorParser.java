package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import static com.rbkmoney.geck.filter.kit.msgpack.SelectorParser.State.*;

/**
 * Created by vpankrashkin on 21.09.17.
 */
public class SelectorParser {
    enum State {
        READY,
        NAME,
        ARRAY_OPEN,
        ARRAY_CLOSE,
        ANY_NAME,
        ANY_ELEM,
        LEVEL
    }

    private State computeState(char c, ObjectStack<State> stack) {
        State prevState = stack.peek();
        State nextState;
        switch (c) {
            case '[':
                if (prevState == READY || prevState == LEVEL) {
                    nextState = ARRAY_OPEN;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, ARRAY_OPEN));
                }
                break;
            case ']':
                if (prevState == ANY_ELEM) {
                    nextState = ARRAY_CLOSE;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, ARRAY_CLOSE));
                }
                break;
            case '*':
                if (prevState == READY || prevState == LEVEL || prevState == ARRAY_OPEN) {
                    nextState = (prevState == ARRAY_OPEN) ? ANY_ELEM : ANY_NAME;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s|%s", prevState, ANY_NAME, ANY_ELEM));
                }
                break;
            case '.':
                if (prevState == NAME || prevState == ANY_NAME || prevState == ARRAY_CLOSE) {
                    nextState = LEVEL;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, LEVEL));
                }
                break;
            default:
                if (prevState == READY || prevState == NAME || prevState == LEVEL) {
                    nextState = NAME;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, NAME));
                }
                break;
        }
        return nextState;
    }

    public Selector.Config[] parse(String line, Rule valueMatcher) throws IllegalArgumentException {
        List<Selector> selectors = new ArrayList<>();
        ObjectStack<State> stateStack = new ObjectStack<>(READY);
        State state = READY;
        State prevState = null;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            state = computeState(c, stateStack);
            switch (state) {
                case NAME:
                    if (prevState != NAME) {
                        selectors.add(createStructSelector());
                    }
                    buffer.append(c);
                    break;
                case LEVEL:
                    if (prevState == NAME) {
                        selectors.add(createNameSelector(getAndReset(buffer)));
                    }
                    break;
                case ANY_ELEM:
                    selectors.add(createArrayAnyIndexSelector());
                    break;
                case ANY_NAME:
                    selectors.add(createStructSelector());
                    selectors.add(createAnyNameSelector());
                    break;
                case ARRAY_CLOSE:
                    break;
                case ARRAY_OPEN:
                    selectors.add(createArraySelector());
                    break;
            }
            if (!(prevState == state && state == NAME)) {
                stateStack.push(state);
            }
            prevState = state;
        }

        if (state == NAME) {
            //selectors.add(createStructSelector());
            selectors.add(createNameSelector(getAndReset(buffer)));
        }

        selectors.add(createValueSelector(valueMatcher));
        
        List<Selector.Config> configs  = new ArrayList<>(selectors.size());
        for (int i = 0; i < selectors.size(); i++) {
            configs.add(new Selector.Config(selectors.get(i).createContext()));
        }

        for (int i = 0; i < configs.size(); i++) {
            Selector.Config config = configs.get(i);
            if (0 == i) {
                config.prevConfig = null;
                config.prevNativeConfig = null;
                continue;
            }
            if (i == selectors.size() - 1) {
                config.nextConfig = null;
                config.nextNativeConfig = null;
                continue;
            }
            config.prevNativeConfig = configs.get(i - 1);
            config.prevConfig = configs.get(i - i);
            config.nextNativeConfig = configs.get(i + 1);
            config.nextConfig = configs.get(i + 1);
        }

        return configs.toArray(new Selector.Config[configs.size()]);
    }

    private String getAndReset(StringBuilder builder) {
        String data = builder.toString();
        builder.setLength(0);
        return data;
    }

    private ArrayIndexSelector createArrayIndexSelector(Rule rule, Selector.Type type) {
        return new ArrayIndexSelector(rule, type);
    }

    private ArrayIndexSelector createArrayAnyIndexSelector() {
        return createArrayIndexSelector(new ConditionRule(obj -> true), Selector.Type.REPEATABLE);
    }

    private ArraySelector createArraySelector() {
        return new ArraySelector(new ConditionRule(obj -> true), Selector.Type.UNREPEATABLE);
    }

    private StructSelector createStructSelector() {
        return new StructSelector(new ConditionRule(obj -> true), Selector.Type.UNREPEATABLE);
    }

    private FieldSelector createAnyNameSelector() {
        return createNameSelector(new ConditionRule(obj -> true), Selector.Type.REPEATABLE);
    }

    private FieldSelector createNameSelector(String name) {
        return createNameSelector(new ConditionRule(new EqualsCondition(name)), Selector.Type.REPEATABLE);
    }

    private FieldSelector createNameSelector(Rule rule, Selector.Type type) {
        return new FieldSelector(rule, type);
    }

    private ValueSelector createValueSelector(Rule rule) {
        return new ValueSelector(rule, Selector.Type.UNREPEATABLE);
    }

}
