package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class SelectionResult {
    final SelectionType type;

    public SelectionResult(SelectionType type) {
        this.type = type;
    }

    enum SelectionType {
        MATCH, MISMATCH, REUSE_LEVEL, PUSH_LEVEL, CONTINUE_POP_LEVEL, REPEAT_POP_LEVEL,
    }

    static final class Match extends SelectionResult {
        final List<Rule> rules;

        public Match(Rule rule) {
            this(Arrays.asList(rule));
        }

        public Match(List<Rule> rules) {
            super(SelectionType.MATCH);
            this.rules = rules;
        }
    }
    static final class Mismatch extends SelectionResult {
        public Mismatch() {
            super(SelectionType.MISMATCH);
        }
    }
    static final class ReuseLevel extends SelectionResult {
        public ReuseLevel() {
            super(SelectionType.REUSE_LEVEL);
        }
    }
    static final class PushLevel extends SelectionResult {
        final Selector pushedSelector;

        public PushLevel(Selector pushedSelector) {
            super(SelectionType.PUSH_LEVEL);
            this.pushedSelector = pushedSelector;
        }
    }
}
