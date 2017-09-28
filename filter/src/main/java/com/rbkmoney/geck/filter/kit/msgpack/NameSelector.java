package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class NameSelector extends Selector {
    private final Selector nextSelector;
    private final Rule rule;

    NameSelector(Rule rule, Selector selector, Type type, int level) {
        super(type, StructHandleResult.SKIP_SUBTREE, level);
        this.rule = rule;
        this.nextSelector = selector;
    }

    @Override
    SelectionResult select(byte eventType, Object val, Selector.Context[] contexts) {
        Context context = (Context) tryInitContext(contexts);
        if (context.isLevelSelected()) {
            if (eventType == EventFlags.pointName) {
                return selectResult(val, rule, nextSelector);
            } else {
                return mismatchResult();
            }
        } else {
            if (eventType == EventFlags.startStruct) {
                context.setLevelSelected(true);
                return pushResult(this);
            } else {
                return mismatchResult();
            }
        }
    }

    @Override
    NameSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
