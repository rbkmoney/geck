package com.rbkmoney.kebab.writer;

import com.rbkmoney.kebab.ByteStack;
import com.rbkmoney.kebab.StructWriter;
import com.rbkmoney.kebab.ThriftType;
import com.rbkmoney.kebab.exception.BadFormatException;

import java.io.IOException;
import java.io.Writer;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class JsonStructWriter implements StructWriter {

    static final byte EMPTY_OBJECT = 1;

    static final byte NONEMPTY_OBJECT = 2;

    static final byte EMPTY_ARRAY = 3;

    static final byte NONEMPTY_ARRAY = 4;

    static final byte JSON_NAME = 5;

    static final byte EMPTY_DOCUMENT = 6;

    static final byte NONEMPTY_DOCUMENT = 7;

    private final Writer out;

    private ByteStack stack = new ByteStack();

    {
        stack.push(EMPTY_DOCUMENT);
    }

    public JsonStructWriter(Writer out) {
        Objects.requireNonNull(out);
        this.out = out;
    }

    private void writeBeforeValue(ThriftType type) throws IOException {
        writeBegin(EMPTY_OBJECT, '{');
        name("type");
        beforeValue();
        writeString(type.toString());
        name("value");

    }

    private void writeAfterValue() throws IOException {
        writeEnd(EMPTY_OBJECT, NONEMPTY_OBJECT, '}');
    }

    private void newline() throws IOException {
        out.write("\n");
        for (int i = 0; i < stack.size(); i++) {
            out.write(' ');
        }
    }

    private void beforeValue() throws IOException {
        switch (stack.peek()) {
            case EMPTY_ARRAY:
                stack.pop();
                stack.push(NONEMPTY_ARRAY);
                newline();
                break;
            case NONEMPTY_ARRAY:
                out.append(',');
                newline();
                break;
            case EMPTY_DOCUMENT:
                stack.pop();
                stack.push(NONEMPTY_DOCUMENT);
                break;
            case JSON_NAME:
                out.append(':');
                stack.pop();
                stack.push(NONEMPTY_OBJECT);
                break;
        }
    }

    private void writeString(String value) throws IOException {
        out.write('"');
        out.write(value);
        out.write('"');
    }

    private void writeBegin(byte empty, char symbol) throws IOException {
        beforeValue();
        stack.push(empty);
        out.write(symbol);
    }

    private void writeEnd(byte empty, byte nonEmpty, char symbol) throws IOException {
        byte element = stack.peek();
        if (element != empty && element != nonEmpty) {
            throw new BadFormatException();
        }

        if (element == nonEmpty) {
            newline();
        }
        stack.pop();
        out.write(symbol);
    }

    private void writeValue(String value, ThriftType type) throws IOException {
        writeBeforeValue(type);
        beforeValue();
        if (type == ThriftType.BINARY || type == ThriftType.STRING) {
            writeString(value);
        } else {
            out.write(value);
        }
        writeAfterValue();
    }

    @Override
    public void beginStruct() throws IOException {
        writeBeforeValue(ThriftType.STRUCT);
        writeBegin(EMPTY_OBJECT, '{');
    }

    @Override
    public void endStruct() throws IOException {
        writeEnd(EMPTY_OBJECT, NONEMPTY_OBJECT, '}');
        writeAfterValue();
    }

    @Override
    public void beginList(int size) throws IOException {
        writeBeforeValue(ThriftType.LIST);
        writeBegin(EMPTY_ARRAY, '[');
    }

    @Override
    public void endList() throws IOException {
        writeEnd(EMPTY_ARRAY, NONEMPTY_ARRAY, ']');
        writeAfterValue();
    }

    @Override
    public void beginMap(int size) throws IOException {
        writeBeforeValue(ThriftType.MAP);
        writeBegin(EMPTY_ARRAY, '[');
    }

    @Override
    public void endMap() throws IOException {
        writeEnd(EMPTY_ARRAY, NONEMPTY_ARRAY, ']');
        writeAfterValue();
    }

    @Override
    public void beginKey() throws IOException {
        writeBegin(EMPTY_OBJECT, '{');
        name("key");
    }

    @Override
    public void endKey() throws IOException {
        if (stack.peek() == JSON_NAME) {
            throw new BadFormatException();
        }
    }

    @Override
    public void beginValue() throws IOException {
        name("value");
    }

    @Override
    public void endValue() throws IOException {
        writeEnd(EMPTY_OBJECT, NONEMPTY_OBJECT, '}');
    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name);

        if (stack.peek() == NONEMPTY_OBJECT) {
            out.write(',');
        }
        newline();
        stack.pop();
        stack.push(JSON_NAME);

        writeString(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        writeValue(value ? "true" : "false", ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value, ThriftType.STRING);
    }

    @Override
    public void value(byte value) throws IOException {
        writeValue(Byte.toString(value), ThriftType.BYTE);
    }

    @Override
    public void value(short value) throws IOException {
        writeValue(Short.toString(value), ThriftType.SHORT);
    }

    @Override
    public void value(int value) throws IOException {
        writeValue(Integer.toString(value), ThriftType.INTEGER);
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(Double.toString(value), ThriftType.DOUBLE);
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(Long.toString(value), ThriftType.LONG);
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value), ThriftType.BINARY);
    }

    @Override
    public void nullValue() throws IOException {
        writeValue("null", ThriftType.NULL);
    }

    @Override
    public void close() throws IOException {
        out.close();

        if (stack.size() > 1 || stack.size() == 1 && stack.peek() == NONEMPTY_DOCUMENT) {
            throw new BadFormatException();
        }
    }
}
