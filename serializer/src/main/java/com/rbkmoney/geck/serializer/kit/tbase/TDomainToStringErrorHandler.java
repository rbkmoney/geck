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
public class TDomainToStringErrorHandler extends TErrorHandler<String> {
    private StringBuilder builder;
    private final String CODE_NAME = "code";
    private boolean code;

    @Override
    public void beginStruct(int size) throws IOException {
    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {
        code = CODE_NAME.equals(name);
    }

    @Override
    public void value(String value) throws IOException {
        if (!code) {
            return;
        }
        processCode(value);
        code = false;
    }

    @Override
    public String getResult() throws IOException {
        String result = builder.toString();
        builder = null;
        code = false;
        return result;
    }

    protected void processCode(String code) {
        if (builder == null) {
            builder = new StringBuilder();
        }

        if (builder.length() > 0) {
            builder.append(':');
        }
        builder.append(code.replaceAll(":", " "));
    }
}
