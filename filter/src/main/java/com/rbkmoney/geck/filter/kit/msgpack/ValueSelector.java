package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 21.09.17.
 */
class ValueSelector extends Selector {
    private final Selector nextSelector;
    private final Rule rule;

    ValueSelector(Rule rule, Selector selector, Type type, int contextIndex) {
        super(type, StructHandleResult.SKIP_SIBLINGS, contextIndex);
        this.rule = rule;
        this.nextSelector = selector;
    }

    @Override
    SelectionResult select(byte eventType, Object val, Selector.Context[] contexts) {
        Context context = (Context) tryInitContext(contexts);
        if (context.isLevelSelected() && eventType != EventFlags.pointValue) {
            return mismatchResult();
        } else {
            context.setLevelSelected(true);
            return selectResult(eventType == EventFlags.pointValue ? val : new SelectedData(eventType, val), rule, nextSelector);
        }
    }

    @Override
    ValueSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
