package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.StructHandler;

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

    static final byte NONEMPTY_STRUCT = 2;

    static final byte NONEMPTY_LIST = 4;

    static final byte NONEMPTY_SET = 6;

    static final byte NONEMPTY_MAP = 8;

    private ByteStack stack = new ByteStack();

    private Writer writer = new StringWriter();
    private XMLStreamWriter out = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
    private boolean isClosed;
    private int i;

    {
        out.writeStartDocument();
        System.out.println("balance = "+(++i));
        out.writeStartElement("root");
    }

    public XMLHandler() throws Exception {
    }

    @Override
    public void beginStruct(int size) throws IOException {
        if (!stack.isEmpty()) {
            byte x = stack.peek();
            if (x == NONEMPTY_LIST || x == NONEMPTY_SET) {
                System.out.println("balance = " + (++i));
                try {
                    out.writeStartElement("element");
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
       // writeStartElement("struct");
        System.out.println("beginStruct");
        stack.push(NONEMPTY_STRUCT);
    }

    @Override
    public void endStruct() throws IOException {
        //writeEndElement();
        System.out.println("endStruct");
        stack.pop();

        try {
            System.out.println("balance = " + (--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginList(int size) throws IOException {
        if (!stack.isEmpty()) {
            byte x = stack.peek();
            if (x == NONEMPTY_LIST || x == NONEMPTY_SET) {
                System.out.println("balance = " + (++i));
                try {
                    out.writeStartElement("element");
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
        //writeStartElement("list");
        System.out.println("beginList");
        stack.push(NONEMPTY_LIST);
    }

    @Override
    public void endList() throws IOException {
        //writeEndElement();
        System.out.println("endList");
        stack.pop();
        try {
            System.out.println("balance = "+(--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginSet(int size) throws IOException {
        if (!stack.isEmpty()) {
            byte x = stack.peek();
            if (x == NONEMPTY_LIST || x == NONEMPTY_SET) {
                System.out.println("balance = " + (++i));
                try {
                    out.writeStartElement("element");
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                }
            }
        }
        //writeStartElement("set");
        System.out.println("beginSet");
        stack.push(NONEMPTY_SET);
    }

    @Override
    public void endSet() throws IOException {
       // writeEndElement();
        System.out.println("endSet");
        stack.pop();
        try {
            System.out.println("balance = "+(--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginMap(int size) throws IOException {
       // writeStartElement("map");
        System.out.println("beginMap");
        stack.push(NONEMPTY_MAP);
    }

    @Override
    public void endMap() throws IOException {
        //writeEndElement();
        System.out.println("endMap");
        stack.pop();
        try {
            System.out.println("balance = "+(--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginKey() throws IOException {
        System.out.println("beginKey");
        System.out.println("balance = "+(++i));
        try {
            out.writeStartElement("element");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        name("key");
    }

    @Override
    public void endKey() throws IOException {
        System.out.println("endKey");
    }

    @Override
    public void beginValue() throws IOException {
        System.out.println("beginValue");
        name("value");
    }

    @Override
    public void endValue() throws IOException {
        System.out.println("endValue");
        try {
            System.out.println("balance = "+(--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void name(String name) throws IOException {
        System.out.println("name " +name);
        try {
            System.out.println("balance = "+(++i));
            out.writeStartElement(name);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void writeValue(String value){
        System.out.println("value "+value);
       // System.out.println("end Value "+value);
        try {
            if (!stack.isEmpty()) {
                byte x = stack.peek();
                if (x == NONEMPTY_LIST || x == NONEMPTY_SET) {
                    System.out.println("balance = " + (++i));
                    out.writeStartElement("element");
                }
            }
            out.writeCharacters(value.toString());
            System.out.println("balance = "+(--i));
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void value(boolean value) throws IOException {
        writeValue(String.valueOf(value));
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value);
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(String.valueOf(value));
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(String.valueOf(value));
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value));
    }

    @Override
    public void nullValue() throws IOException {
        writeValue("null");
    }

    @Override
    public Writer getResult() throws IOException {
        try {
            if (!isClosed) {
                System.out.println("balance = "+(--i));
                out.writeEndDocument();
                isClosed = true;
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public static void main(String[] args) throws Exception {
        XMLHandler x = new XMLHandler();
        x.out.writeStartDocument();
        x.out.writeStartElement("root");
        x.out.writeEndElement();
        x.out.writeEndDocument();
    }
}
