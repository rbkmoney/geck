package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

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
class ValueSelector extends Selector {
    private final Rule rule;

    ValueSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) tryInitContext(config.context);
        context.setLevelSelected(true);
        return selectPushResult(eventFlag == EventFlags.pointValue ? val : new SelectedData(eventFlag, val), rule, config.nextConfig, config);
    }

    @Override
    ValueSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
