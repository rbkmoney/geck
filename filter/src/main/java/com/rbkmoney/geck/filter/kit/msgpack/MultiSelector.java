package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.serializer.StructHandleResult;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by vpankrashkin on 20.09.17.
 */
abstract class MultiSelector extends Selector {

    public MultiSelector(Type type, StructHandleResult skipLevelOp, int contextIndex) {
        super(type, skipLevelOp, contextIndex);
    }

    MultiSelector.Context initContext(Selector.Context context) {
        Context tContext = (Context) context;
        if (tContext.getResultsOffset() > 0) {
            //results array already initialized
        } else if (tContext.getResults() != null) {
            Arrays.fill(tContext.getResults(), null);
        } else {
            throw new IllegalStateException("Context results must be at least initialized");
        }
        return tContext;
    }

    class Context extends Selector.Context {
        private final SelectionResult[] results;
        private final int resultsOffset;

        Context(SelectionResult[] results, int resultsOffset) {
            Objects.requireNonNull(results);
            this.results = results;
            this.resultsOffset = resultsOffset;
        }

        SelectionResult[] getResults() {
            return results;
        }

        public int getResultsOffset() {
            return resultsOffset;
        }
    }
}
