package com.rbkmoney.geck.serializer.kit.tbase;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class TDomainStringErrorHandler extends TErrorHandler<String> {
    private StringBuilder builder;

    @Override
    public void beginStruct(int size) throws IOException {
    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {

    }

    @Override
    public void value(String value) throws IOException {
        if (builder == null) {
            builder = new StringBuilder();
        }

        if (builder.length() > 0) {
            builder.append(':');
        }
        builder.append(value);
    }

    @Override
    public String getResult() throws IOException {
        String result = builder.toString();
        builder = null;
        return result;
    }
}
