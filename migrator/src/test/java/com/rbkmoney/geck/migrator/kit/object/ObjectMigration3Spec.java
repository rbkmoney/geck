package com.rbkmoney.geck.migrator.kit.object;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.ThriftSpec;

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
public class ObjectMigration3Spec implements ObjectSpec {
    @Override
    public Object apply(Object in) throws MigrationException {
        if(in instanceof Map) {
            ((Map) in).put("v4_field", "v4");
        }
        return in;
    }

    @Override
    public ThriftSpec getThriftSpec() {
        return new ThriftSpec(new ThriftDef(3), new ThriftDef(4));
    }
}
