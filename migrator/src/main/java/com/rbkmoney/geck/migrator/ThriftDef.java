package com.rbkmoney.geck.migrator;

import java.util.Objects;

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
public class ThriftDef {
    public static final int NO_VERSION = -1;
    private final String type;
    private final int version;

    public ThriftDef(int version) {
        this(version, null);
    }

    public ThriftDef(int version, String type) {
        this.type = type;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThriftDef)) return false;
        ThriftDef thriftDef = (ThriftDef) o;
        return getVersion() == thriftDef.getVersion() &&
                Objects.equals(getType(), thriftDef.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getVersion());
    }

    @Override
    public String toString() {
        return "ThriftDef{" +
                "type='" + getType() + '\'' +
                ", version=" + getVersion() +
                '}';
    }
}
