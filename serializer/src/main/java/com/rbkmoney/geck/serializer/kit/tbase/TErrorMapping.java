package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.domain.Failure;
import com.rbkmoney.damsel.payment_processing.errors.PaymentFailure;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class TErrorMapping {
    public static Failure toGeneral(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TDomainErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(Failure failure) {
        try {
            return new TBaseProcessor().process(failure, new TDomainStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStringVal(PaymentFailure failure) {
        try {
            return new TBaseProcessor().process(failure, new TTypedStringErrorHandler());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
