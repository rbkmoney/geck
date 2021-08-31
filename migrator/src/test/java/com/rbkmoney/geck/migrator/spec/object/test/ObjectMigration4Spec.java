package com.rbkmoney.geck.migrator.spec.object.test;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.ThriftSpec;
import com.rbkmoney.geck.migrator.kit.object.ObjectSpec;

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
public class ObjectMigration4Spec implements ObjectSpec {
    @Override
    public Object apply(Object in) throws MigrationException {
        if(in instanceof Map) {
            ((Map) in).put("v5_field", "v5");
        }
        return in;
    }

    @Override
    public ThriftSpec getThriftSpec() {
        return new ThriftSpec(new ThriftDef(4), new ThriftDef(5));
    }
}
