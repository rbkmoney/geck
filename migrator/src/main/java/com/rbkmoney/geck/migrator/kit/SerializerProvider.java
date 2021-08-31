package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.SerializerDef;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;

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
public interface SerializerProvider {
    <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef);
    <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef);
    boolean accept(SerializerDef sDef);
}
