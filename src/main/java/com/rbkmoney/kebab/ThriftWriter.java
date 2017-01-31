package com.rbkmoney.kebab;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Created by tolkonepiu on 26/01/2017.
 */
public interface ThriftWriter extends Closeable, Flushable {

    ThriftWriter beginObject() throws IOException;

    ThriftWriter endObject() throws IOException;

    ThriftWriter beginList() throws IOException;

    ThriftWriter endList() throws IOException;

    ThriftWriter beginSet() throws IOException;

    ThriftWriter endSet() throws IOException;

    ThriftWriter beginMap() throws IOException;

    ThriftWriter endMap() throws IOException;

    ThriftWriter beginKey() throws IOException;

    ThriftWriter endKey() throws IOException;

    ThriftWriter beginValue() throws IOException;

    ThriftWriter endValue() throws IOException;

    ThriftWriter name(String name) throws IOException;

    ThriftWriter value(boolean value) throws IOException;

    ThriftWriter value(String value) throws IOException;

    ThriftWriter value(byte value) throws IOException;

    ThriftWriter value(short value) throws IOException;

    ThriftWriter value(int value) throws IOException;

    ThriftWriter value(double value) throws IOException;

    ThriftWriter value(long value) throws IOException;

    ThriftWriter binaryValue(byte[] value) throws IOException;

    ThriftWriter nullValue() throws IOException;

}
