package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class MapKeySelector extends Selector {
    private final Rule rule;

    MapKeySelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if (eventFlag == EventFlags.endMapKey) {
                context.setLevelConsumed(true);
                return mismatchResult(config);
            } else {
                return selectPushResult(val, rule, config.nextConfig, config);
            }
        } else {
            if (eventFlag == EventFlags.startMapKey) {
                context.setLevelSelected(true);
                return pushResult(config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    MapKeySelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
