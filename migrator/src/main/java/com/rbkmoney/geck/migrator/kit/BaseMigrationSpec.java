package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.MigrationSpec;

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
public class BaseMigrationSpec<T> implements MigrationSpec<T> {
    private final T spec;
    private final String type;

    public BaseMigrationSpec(T spec, String type) {
        this.spec = spec;
        this.type = type;
    }

    @Override
    public T getSpec() {
        return spec;
    }

    @Override
    public String getType() {
        return type;
    }
}
