package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;

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
public class TTypedStringToDomainErrorProcessor implements StructProcessor<String> {
    @Override
    public <R> R process(String value, StructHandler<R> handler) throws IOException {
        String[] tokens = value.split(":");
        for (int i = 0; i < tokens.length; i++) {
            handler.beginStruct(1);
            handler.name(tokens[i]);
        }
        handler.beginStruct(0);
        handler.endStruct();
        for (int i = 0; i < tokens.length; i++) {
            handler.endStruct();
        }
        return handler.getResult();
    }
}
