package com.rbkmoney.geck.filter.kit.msgpack;

/**
 * Created by vpankrashkin on 20.09.17.
 */
abstract class MultiSelector extends Selector {

    public MultiSelector(Type type) {
        super(type);
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) config.context;
        if (context.isLevelConsumed()) {
            return mismatchResult(config);//TODO remove this
        }

        /*SelectionResult result = null;
        for (int i = 0; i < context.children.length; ++i) {
            Selector.Context chContext = context.children[i];
            if (!chContext.isFinalResult()) {
                SelectionResult chResult = chContext.getSelector().select(eventType, val, contexts);
                result = nearestResult(result, chResult);
            }
        }*/
        return null;

    }

    private SelectionResult nearestResult(SelectionResult prevResult, SelectionResult newResult) {
        return null;
    }

    class Context extends Selector.Context {
        private final Selector.Context[][] childrenLevels;

        Context(Selector.Context[][] childrenLevels) {
            this.childrenLevels = childrenLevels;
        }

        private boolean isSelectable(SelectionResult result) {
            //if (result.type == SelectionResult.SelectionType.PUSH_LEVEL)
            return false;
        }

        @Override
        public Selector.Context init() {
            /*for (int i = 0; i < childrenLevels[getContextIndex()].length; ++i) {
                childrenLevels[getContextIndex()][i].init();
            }*/
            return super.init();
        }
    }
}
