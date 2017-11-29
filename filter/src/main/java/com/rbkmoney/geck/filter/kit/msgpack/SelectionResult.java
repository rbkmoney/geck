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
        MATCH, MISMATCH, REUSE_LEVEL, PUSH_LEVEL,
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
        final Selector.Config config;
        public ReuseLevel(Selector.Config config) {
            super(SelectionType.REUSE_LEVEL);
            this.config = config;
        }
    }
    static final class PushLevel extends SelectionResult {
        final Selector.Config pushedConfig;

        public PushLevel(Selector.Config pushedConfig) {
            super(SelectionType.PUSH_LEVEL);
            this.pushedConfig = pushedConfig;
        }
    }
}
