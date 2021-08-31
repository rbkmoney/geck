package com.rbkmoney.geck.serializer.kit.tbase;

import java.io.IOException;

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
})
public class TTypedToStringErrorHandler extends TDomainToStringErrorHandler {

    @Override
    public void beginStruct(int size) throws IOException {
    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {
        super.processCode(name);
    }

    @Override
    public void value(String value) throws IOException {
        throw new UnsupportedOperationException();
    }
}
