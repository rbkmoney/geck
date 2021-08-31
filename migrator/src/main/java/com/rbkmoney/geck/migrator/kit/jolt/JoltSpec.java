package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.Chainr;
import com.rbkmoney.geck.migrator.ThriftSpec;

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
})
public class JoltSpec {

    private final ThriftSpec thriftSpec;
    private final Chainr chainr;

    public JoltSpec(ThriftSpec thriftSpec, Chainr chainr) {
        this.thriftSpec = thriftSpec;
        this.chainr = chainr;
    }

    public ThriftSpec getThriftSpec() {
        return thriftSpec;
    }

    public Chainr getChainr() {
        return chainr;
    }
}
