package com.rbkmoney.geck.migrator.kit.nop;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationPoint;
import com.rbkmoney.geck.migrator.MigrationType;
import com.rbkmoney.geck.migrator.SerializerSpec;
import com.rbkmoney.geck.migrator.kit.AbstractMigrator;

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
public class NopMigrator extends AbstractMigrator {

    @Override
    public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
        return serialize(data, serializerSpec, mPoint.getThriftSpec());
    }

    @Override
    public String getMigrationType() {
        return MigrationType.NOP.getKey();
    }
}
