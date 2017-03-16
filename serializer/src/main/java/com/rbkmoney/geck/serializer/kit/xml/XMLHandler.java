package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.StructHandler;
import static com.rbkmoney.geck.serializer.kit.xml.XMLConstants.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Base64;

/**
 * Created by inalarsanukaev on 14.03.17.
 */
public class XMLHandler implements StructHandler<Writer> {

    private ByteStack stack = new ByteStack();
    private Writer writer = new StringWriter();
    private XMLStreamWriter out;
    private boolean isClosed;

    {
        try {
            out = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
            out.writeStartDocument();
            out.writeStartElement(ROOT);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    private void writeStartElement(String type) {
        try {
            if (!stack.isEmpty()) {
                byte x = stack.peek();
                if (x == NONEMPTY_LIST || x == NONEMPTY_SET) {
                    out.writeStartElement(ELEMENT);
                }
            }
            if (type != null) {
                out.writeAttribute(ATTRIBUTE_TYPE, type);
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }
    private void writeValue(String value, String type){
        try {
            writeStartElement(type);
            out.writeCharacters(value.toString());
            out.writeEndElement();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }
    private void writeEndElement() {
        try {
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginStruct(int size) throws IOException {
        writeStartElement(null);
        stack.push(NONEMPTY_STRUCT);
    }

    @Override
    public void endStruct() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginList(int size) throws IOException {
        writeStartElement(LIST);
        stack.push(NONEMPTY_LIST);
    }

    @Override
    public void endList() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginSet(int size) throws IOException {
        writeStartElement(SET);
        stack.push(NONEMPTY_SET);
    }

    @Override
    public void endSet() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginMap(int size) throws IOException {
        stack.push(NONEMPTY_MAP);
        try {
            out.writeAttribute(ATTRIBUTE_TYPE, MAP);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    @Override
    public void endMap() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginKey() throws IOException {
        try {
            out.writeStartElement(ELEMENT);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
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
        writeEndElement();
    }

    @Override
    public void name(String name) throws IOException {
        try {
            out.writeStartElement(name);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    @Override
    public void value(boolean value) throws IOException {
        writeValue(String.valueOf(value), BOOL);
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value, null);
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(String.valueOf(value), DOUBLE);
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(String.valueOf(value), LONG);
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value), BYTEARRAY);
    }

    @Override
    public void nullValue() throws IOException {
        writeValue(NULL, null);
    }

    @Override
    public Writer getResult() throws IOException {
        try {
            if (!isClosed) {
                out.writeEndDocument();
                isClosed = true;
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
        return writer;
    }
}
