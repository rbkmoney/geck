package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.EventFlags;
import com.rbkmoney.geck.serializer.kit.StructType;
import static com.rbkmoney.geck.serializer.kit.xml.XMLConstants.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by inalarsanukaev on 14.03.17.
 */
public class XMLHandler implements StructHandler<DOMResult> {

    private ByteStack stack = new ByteStack();
    private DOMResult result;
    private XMLStreamWriter out;
    private DocumentBuilder documentBuilder;
    private XMLOutputFactory xmlOutputFactory;

    {
        init();
    }

    private void init() {
        try {
            result = new DOMResult(getDocumentBuilder().newDocument());
            out = getXmlOutputFactory().createXMLStreamWriter(result);
            out.writeStartDocument();
            out.writeStartElement(ROOT);
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
    }

    private XMLOutputFactory getXmlOutputFactory() {
        if (xmlOutputFactory == null) {
            xmlOutputFactory = XMLOutputFactory.newInstance();
        }
        return xmlOutputFactory;
    }

    private DocumentBuilder getDocumentBuilder() {
        if (documentBuilder == null) {
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException("Unknown error", e);
            }
        }
        return documentBuilder;
    }

    private void writeStartElement(String type) {
        try {
            if (!stack.isEmpty()) {
                byte x = stack.peek();
                if (x == EventFlags.startList || x == EventFlags.startSet) {
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
        stack.push(EventFlags.startStruct);
    }

    @Override
    public void endStruct() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginList(int size) throws IOException {
        writeStartElement(StructType.LIST.getKey());
        stack.push(EventFlags.startList);
    }

    @Override
    public void endList() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginSet(int size) throws IOException {
        writeStartElement(StructType.SET.getKey());
        stack.push(EventFlags.startSet);
    }

    @Override
    public void endSet() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginMap(int size) throws IOException {
        stack.push(EventFlags.startMap);
        try {
            out.writeAttribute(ATTRIBUTE_TYPE, StructType.MAP.getKey());
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
        System.out.println("nullValue");
        writeStartElement( null);
        try {
            out.writeAttribute("xsi", "http://www.w3.org/2001/XMLSchema-instance", "nil", "true");
            out.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DOMResult getResult() throws IOException {
        try {
            out.writeEndDocument();
            out.flush();
        } catch (XMLStreamException e) {
            throw new RuntimeException("Unknown error", e);
        }
        DOMResult readyResult = result;
        init();
        return readyResult;
    }
}
