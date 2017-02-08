package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.StructHandler;
import org.apache.thrift.TBase;

import java.io.IOException;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseHandler implements StructHandler<TBase> {

    private final Class<? extends TBase> parentClass;

    private TBase tBase = null;

    public TBaseHandler(Class<? extends TBase> parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public void beginStruct(int size) throws IOException {

    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void beginList(int size) throws IOException {

    }

    @Override
    public void endList() throws IOException {

    }

    @Override
    public void beginMap(int size) throws IOException {

    }

    @Override
    public void endMap() throws IOException {

    }

    @Override
    public void beginKey() throws IOException {

    }

    @Override
    public void endKey() throws IOException {

    }

    @Override
    public void beginValue() throws IOException {

    }

    @Override
    public void endValue() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {

    }

    @Override
    public void value(boolean value) throws IOException {

    }

    @Override
    public void value(String value) throws IOException {

    }

    @Override
    public void value(double value) throws IOException {

    }

    @Override
    public void value(long value) throws IOException {

    }

    @Override
    public void value(byte[] value) throws IOException {

    }

    @Override
    public void nullValue() throws IOException {

    }

    @Override
    public TBase getResult() throws IOException {
        return tBase;
    }
}
