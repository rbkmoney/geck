package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.serializer.StructHandleResult;
import com.rbkmoney.geck.serializer.kit.EventFlags;

import java.util.Objects;

import static com.rbkmoney.geck.filter.kit.msgpack.SelectionResult.SelectionType.MATCH;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class NameMultiSelector extends MultiSelector {
    private final NameSelector[] selectors;

    NameMultiSelector(NameSelector[] selectors, Type type, int contextIndex) {
        super(type, StructHandleResult.SKIP_SIBLINGS, contextIndex);
        Objects.requireNonNull(selectors);
        this.selectors = selectors;
    }

    @Override
    SelectionResult select(byte eventType, Object val, Selector.Context[] contexts) {
        Context context = (Context) tryInitContext(contexts);
        if (!context.isLevelSelected()) {
            if (eventType != EventFlags.startStruct) {
                return mismatchResult();
            } else {
                context.setLevelSelected(true);
            }
        }

        SelectionResult[] results = context.getResults();
        SelectionResult aggrResult = null;
        for (int i = 0; i < selectors.length; ++i) {
            int resultIndex = context.getResultsOffset() + i;
            if (!context.isFinalResult(results[resultIndex])) {
                Selector selector = selectors[i];
                SelectionResult result = selector.select(eventType, val, contexts);
                results[resultIndex] = result;
                if (aggrResult == null || (context.isFinalResult(aggrResult) && !context.isFinalResult(result))) {
                    aggrResult = result;
                } else if (result.type == MATCH && aggrResult.type != MATCH) {
                    aggrResult = result;
                }
            }
        }
        return aggrResult != null ? aggrResult : mismatchResult();
    }

    class Context extends MultiSelector.Context {
        Context(SelectionResult[] results, int resultsOffset) {
            super(results, resultsOffset);
        }
    }
}
