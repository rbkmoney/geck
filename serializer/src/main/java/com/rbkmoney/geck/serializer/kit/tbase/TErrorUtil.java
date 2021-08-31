package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.payment_processing.errors.PaymentFailure;

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
public class TErrorUtil {
    public static Failure toGeneral(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TTypedToDomainErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Failure toGeneral(String failure) {
        try {
            return new TTypedStringToDomainErrorProcessor().process(failure, new TTypedToDomainErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(Failure failure) {
        try {
            return new TBaseProcessor().process(failure, new TDomainToStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TTypedToStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
