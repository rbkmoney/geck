package com.rbkmoney.kebab;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tolkonepiu on 26/01/2017.
 */
public interface ObjectWriter extends Closeable {

    ObjectWriter beginObject() throws IOException;

    ObjectWriter endObject() throws IOException;

    ObjectWriter beginList() throws IOException;

    ObjectWriter endList() throws IOException;

    ObjectWriter beginMap() throws IOException;

    ObjectWriter endMap() throws IOException;

    ObjectWriter beginKey() throws IOException;

    ObjectWriter endKey() throws IOException;

    ObjectWriter beginValue() throws IOException;

    ObjectWriter endValue() throws IOException;

    ObjectWriter name(String name) throws IOException;

    ObjectWriter value(boolean value) throws IOException;

    ObjectWriter value(String value) throws IOException;

    ObjectWriter value(byte value) throws IOException;

    ObjectWriter value(short value) throws IOException;

    ObjectWriter value(int value) throws IOException;

    ObjectWriter value(double value) throws IOException;

    ObjectWriter value(long value) throws IOException;

    ObjectWriter binaryValue(byte[] value) throws IOException;

    ObjectWriter nullValue() throws IOException;

}
