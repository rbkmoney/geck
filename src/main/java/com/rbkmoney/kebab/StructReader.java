package com.rbkmoney.kebab;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tolkonepiu on 06/02/2017.
 */
public interface StructReader extends Closeable {

    void beginStruct(int size) throws IOException;

    void endStruct() throws IOException;

    void beginList(int size) throws IOException;

    void endList() throws IOException;

    void beginMap(int size) throws IOException;

    void endMap() throws IOException;

    void beginKey() throws IOException;

    void endKey() throws IOException;

    void beginValue() throws IOException;

    void endValue() throws IOException;

    void name(String name) throws IOException;

    void value(boolean value) throws IOException;

    void value(String value) throws IOException;

    void value(byte value) throws IOException;

    void value(short value) throws IOException;

    void value(int value) throws IOException;

    void value(double value) throws IOException;

    void value(long value) throws IOException;

    void value(byte[] value) throws IOException;

    void nullValue() throws IOException;

}
