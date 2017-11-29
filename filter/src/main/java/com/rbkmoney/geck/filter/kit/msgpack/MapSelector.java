package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class MapSelector extends Selector {
    private final Rule rule;

    MapSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            context.setLevelConsumed(eventFlag == EventFlags.endStruct);
            return mismatchResult(config);
        } else {
            if (eventFlag == EventFlags.startMap) {
                context.setLevelSelected(true);
                return ((Number)val).longValue() > 0 ? selectPushResult(val, rule, config.nextConfig, config) : mismatchResult(config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    MapSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
