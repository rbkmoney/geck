package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class FieldSelector extends Selector {
    private final Rule rule;

    FieldSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);

        if (!context.isLevelSelected()) {
            StructSelector.Context strContext = (StructSelector.Context) config.prevNativeConfig.context;
            context.setLevelSelected(true);
            context.setRemainSelections(strContext.getStructSize());
        }

        int remains = context.getRemainSelections();
        if (remains > 0 || eventFlag == EventFlags.endStruct) {
            if (eventFlag == EventFlags.pointName) {
                context.setRemainSelections(remains - 1);
                return selectPushResult(val, rule, config.nextConfig, config);
            } else if (eventFlag == EventFlags.endStruct){
                context.reset();
                return reuseResult(config.prevConfig, config);
            }
            context.setLevelConsumed(true);
        }
        return mismatchResult(config);
    }

    @Override
    FieldSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int remainSelections;

        public int getRemainSelections() {
            return remainSelections;
        }

        public void setRemainSelections(int remainSelections) {
            this.remainSelections = remainSelections;
        }
    }
}
