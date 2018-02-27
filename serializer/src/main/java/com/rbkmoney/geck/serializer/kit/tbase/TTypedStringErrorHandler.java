package com.rbkmoney.geck.serializer.kit.tbase;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class TTypedStringErrorHandler extends TDomainStringErrorHandler {

    @Override
    public void beginStruct(int size) throws IOException {
    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {
        super.value(name);
    }

    @Override
    public void value(String value) throws IOException {
    }
}
