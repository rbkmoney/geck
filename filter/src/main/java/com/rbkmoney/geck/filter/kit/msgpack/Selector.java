package com.rbkmoney.geck.filter.kit.msgpack;


import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;

/**
 * Created by vpankrashkin on 13.09.17.
 */
abstract class Selector {
    private static final SelectionResult.Mismatch mismatchResult = new SelectionResult.Mismatch();
    private static final SelectionResult.ReuseLevel reuseResult = new SelectionResult.ReuseLevel();

    private final Type type;
    private final StructHandleResult skipLevelOp;
    private final int contextIndex;

    Selector(Type type, StructHandleResult skipLevelOp, int contextIndex) {
        this.type = type;
        this.skipLevelOp = skipLevelOp;
        this.contextIndex = contextIndex;
    }

    public Type getType() {
        return type;
    }

    public StructHandleResult getSkipForReuseOp(Context context) {
        return (context.lastResult == null || context.lastResult.type == SelectionResult.SelectionType.MATCH) ? StructHandleResult.SKIP_SIBLINGS : StructHandleResult.SKIP_SUBTREE;
    }

    int getContextIndex() {
        return contextIndex;
    }

    SelectionResult.Match matchResult(Rule rule) {
        return new SelectionResult.Match(rule);
    }

    SelectionResult.Mismatch mismatchResult() {
        return mismatchResult;
    }

    SelectionResult.ReuseLevel reuseResult() {
        return reuseResult;
    }

    SelectionResult.PushLevel pushResult(Selector pushedSelector) {
        return new SelectionResult.PushLevel(pushedSelector);
    }

    SelectionResult selectResult(Object val, Rule rule, Selector nextSelector) {
        if (match(rule, val)) {
            return nextSelector != null ? pushResult(nextSelector) : matchResult(rule);
        } else {
            return mismatchResult();
        }
    }

    Context createContext() {
        return new Context();
    }

    Context tryInitContext(Context[] contexts) {
        Context context = contexts[getContextIndex()];
        return context.isInitialized() ? context : context.init();
    }

    abstract SelectionResult select(byte eventType, Object val, Context[] contexts);

    boolean match(Rule rule, Object data) {
        Condition[] conditions = rule.getConditions();
        for (int i = 0; i < conditions.length; ++i) {
            if (!conditions[i].accept(data)) {
                return false;
            }
        }
        return true;
    }

    class Context {
        private SelectionResult lastResult;
        private boolean initialized;
        private boolean levelSelected;

        Selector getSelector() {
            return Selector.this;
        }

        SelectionResult getLastResult() {
            return lastResult;
        }

        public boolean isInitialized() {
            return initialized;
        }

        public void setInitialized(boolean initialized) {
            this.initialized = initialized;
        }

        public Context init() {
            initialized = true;
            lastResult = null;
            levelSelected = false;
            return this;
        }

        public void reset() {
            initialized = false;
        }

        public void repeat() {
            lastResult = null;
        }

        boolean isLevelSelected() {
            return levelSelected;
        }

        void setLevelSelected(boolean levelSelected) {
            this.levelSelected = levelSelected;
        }

        void setLastResult(SelectionResult lastResult) {
            this.lastResult = lastResult;
        }

        boolean isFinalResult() {
            return isFinalResult(lastResult);
        }

        boolean isFinalResult(SelectionResult result) {
            return result != null && (result.type == SelectionResult.SelectionType.MATCH || result.type == SelectionResult.SelectionType.MISMATCH);
        }

        SelectionResult saveResult(SelectionResult result) {
            this.lastResult = result;
            return result;
        }
    }

    static class SelectedData {
        private final byte type;
        private final Object data;

        public SelectedData(byte type, Object data) {
            this.type = type;
            this.data = data;
        }

        public byte getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }

    enum Type {
        REPEATABLE, UNREPEATABLE, MULTI
    }
}
