package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused", "Indentation",
        "EmptyLineSeparator",
        "LineLength",
        "MissingSwitchDefault",
        "EmptyBlock",
        "NeedBraces",
        "LeftCurly",
        "LocalVariableName",
        "TypeName",
        "WhitespaceAround",
        "MemberName",
        "ParameterName",
        "NoWhitespaceBefore",
        "ParenPad",
        "AbbreviationAsWordInName",
        "MethodName",
        "ArrayTypeStyle",
        "VariableDeclarationUsageDistance",
        "RightCurlySame",
        "RightCurlyAlone",
        "FallThrough",
        "NoWhitespaceBefore",
        "NonEmptyAtclauseDescription",
        "OverloadMethodsDeclarationOrder",
        "ModifierOrder",
        "OperatorWrap",
})
class ArrayIndexSelector extends Selector {
    private final Rule rule;

    ArrayIndexSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);

        if (!context.isLevelSelected()) {
            ArraySelector.Context arrContext = (ArraySelector.Context) config.prevNativeConfig.context;
            context.setLevelSelected(true);
            context.setRemainSelections(arrContext.getArraySize());
        }

        int remains = context.getRemainSelections();
        if (remains > 0) {
            context.setRemainSelections(remains - 1);
            return selectReuseResult(val, rule, config.nextConfig, config);
        } else {
            context.setLevelConsumed(true);
            return mismatchResult(config);
        }
    }

    @Override
    ArrayIndexSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int remainSelections;

        @Override
        public Selector.Context init() {
            remainSelections = -1;
            return super.init();
        }

        public int getRemainSelections() {
            return remainSelections;
        }

        public void setRemainSelections(int remainSelections) {
            this.remainSelections = remainSelections;
        }
    }
}
