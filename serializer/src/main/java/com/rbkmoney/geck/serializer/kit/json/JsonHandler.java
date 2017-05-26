package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.*;
import java.util.Base64;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public abstract class JsonHandler<R> implements StructHandler<R> {

    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String ESC_SYMBOL = "@";
    //use 'true' value only for print, not for json-processor
    protected boolean pretty;
    protected R out;
    protected JsonGenerator jGenerator;
    protected JsonFactory jfactory = new JsonFactory();

    {
        try {
            init();
        } catch (BadFormatException e) {
            throw new RuntimeException(e);//TODO
        }
    }

    protected abstract void init() throws BadFormatException;

    public static JsonHandler<Writer> newWriterInstance() {
        return newWriterInstance(false);
    }
    public static JsonHandler<Writer> newWriterInstance(boolean isPretty) {
        return new JsonHandler<Writer>() {
            @Override
            protected void init() throws BadFormatException {
                try {
                    this.pretty = isPretty;
                    if (out != null) {
                        out.close();
                    }
                    out = new StringWriter();
                    this.jGenerator = jfactory.createGenerator(out);
                } catch (IOException e) {
                    throw new BadFormatException("Unknown error when init", e);
                }
            }
        };
    }

    @Override
    public void beginStruct(int size) throws IOException {
        jGenerator.writeStartObject();
    }

    @Override
    public void endStruct() throws IOException {
        jGenerator.writeEndObject();
    }

    @Override
    public void beginList(int size) throws IOException {
        jGenerator.writeStartArray();
        if (!pretty) jGenerator.writeString(StructType.LIST.getKey());
    }

    @Override
    public void endList() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginSet(int size) throws IOException {
        jGenerator.writeStartArray();
        if (!pretty) jGenerator.writeString(StructType.SET.getKey());
    }

    @Override
    public void endSet() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginMap(int size) throws IOException {
        jGenerator.writeStartArray();
        if (!pretty) jGenerator.writeString(StructType.MAP.getKey());
    }

    @Override
    public void endMap() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginKey() throws IOException {
        jGenerator.writeStartObject();
        name(KEY);
    }

    @Override
    public void endKey() throws IOException {
    }

    @Override
    public void beginValue() throws IOException {
        name(VALUE);
    }

    @Override
    public void endValue() throws IOException {
        jGenerator.writeEndObject();
    }

    @Override
    public void name(String name) throws IOException {
        jGenerator.writeFieldName(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        jGenerator.writeBoolean(value);
    }

    @Override
    public void value(String value) throws IOException {
        if ((!pretty) && value.startsWith(ESC_SYMBOL)) {
            value = ESC_SYMBOL+value;
        }
        jGenerator.writeString(value);
    }

    @Override
    public void value(double value) throws IOException {
        jGenerator.writeNumber(value);
    }

    @Override
    public void value(long value) throws IOException {
        jGenerator.writeNumber(value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        jGenerator.writeString((pretty ? "" : ESC_SYMBOL) + Base64.getEncoder().encodeToString(value));
    }

    @Override
    public void nullValue() throws IOException {
        jGenerator.writeNull();
    }

    @Override
    public R getResult() throws IOException {
        jGenerator.close();
        R resultOut = out;
        init();
        return resultOut;
    }
}
