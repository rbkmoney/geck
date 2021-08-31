package com.rbkmoney.geck.migrator.kit.object;

import com.rbkmoney.geck.migrator.MigrationException;
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
public interface ObjectSpec {
    Object apply(Object in) throws MigrationException;
    ThriftSpec getThriftSpec();
}
