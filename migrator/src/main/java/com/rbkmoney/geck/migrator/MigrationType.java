package com.rbkmoney.geck.migrator;

import java.util.HashMap;
import java.util.Map;

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
public enum MigrationType {
    JOLT("JOLT"),
    JOBJECT("JOBJECT"),
    TS_JAVA("TS_JAVA"),
    NOP("NOP");

    private static class Holder {
        static Map<String, MigrationType> MAP = new HashMap<>();
    }

    private String key;

    public static MigrationType valueOfKey(String key) {
        return Holder.MAP.get(key);
    }

    MigrationType(String key) {
        this.key = key;
        Holder.MAP.put(key, this);
    }

    public String getKey() {
        return key;
    }
}
