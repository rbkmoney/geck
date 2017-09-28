package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class StructVisitor {
    private static final int NO_REPEAT_BEHIND = -1;
    private final Selector root;
    private final Supplier<Selector.Context[]> ctxSupplier;
    private Selector selector;
    private List<Rule> selectedRules;
    private Selector.Context[] contexts;
    private int lastRepeatPos;
    private int posOffset;

    StructVisitor(Selector root, Supplier<Selector.Context[]> ctxSupplier) {
        Objects.requireNonNull(root);
        Objects.requireNonNull(ctxSupplier);
        this.root = root;
        this.ctxSupplier = ctxSupplier;
    }

    void init() {
        this.selector = root;
        this.selectedRules = null;
        this.contexts = ctxSupplier.get();
        resetRepeatPos();
    }

    private Selector resetAndPullLevel() {
        Selector.Context childContext = contexts[lastRepeatPos + posOffset--];
        childContext.repeat();
        return contexts[childContext.getSelector().getContextIndex() - (posOffset < 0 ? 0 : 1)].getSelector();
    }

    StructHandleResult visit(byte itemType, Object val) {
        StructHandleResult visitResult;
        if (posOffset > 0) {
            visitResult = selector.getSkipForReuseOp(contexts[selector.getContextIndex()]);
            selector = resetAndPullLevel();
            return visitResult;
        }

        SelectionResult result = selector.select(itemType, val, contexts);
        contexts[selector.getContextIndex()].saveResult(result);
        switch (result.type) {
            case MATCH:
                selectedRules = ((SelectionResult.Match) result).rules;
                return StructHandleResult.TERMINATE;
            case MISMATCH:
                lastRepeatPos = findLastRepeat(contexts, selector.getContextIndex());
                if (lastRepeatPos != NO_REPEAT_BEHIND) {
                    visitResult = selector.getSkipForReuseOp(contexts[selector.getContextIndex()]);
                    posOffset = selector.getContextIndex() - lastRepeatPos;
                    selector = resetAndPullLevel();
                    return visitResult;
                } else {
                    return StructHandleResult.TERMINATE;
                }

            case REUSE_LEVEL:
                return StructHandleResult.SKIP_SUBTREE;
            case PUSH_LEVEL:
                selector = ((SelectionResult.PushLevel) result).pushedSelector;
                return StructHandleResult.CONTINUE;
            default:
                throw new IllegalStateException("Illegal return type: " + result.type);
        }
    }

    List<Rule> getSelected() {
        return selectedRules;
    }

    private void resetRepeatPos() {
        lastRepeatPos = NO_REPEAT_BEHIND;
        posOffset = 0;
    }

    private int findLastRepeat(Selector.Context[] contexts, int lastIdx) {
        for (int i = lastIdx; i >= 0; --i) {
            if (contexts[i].getSelector().getType() == Selector.Type.REPEATABLE) {
                return i;
            }
        }
        return NO_REPEAT_BEHIND;
    }
}
