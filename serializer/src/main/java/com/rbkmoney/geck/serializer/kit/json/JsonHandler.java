package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class JsonHandler implements StructHandler<JsonNode> {

    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String ESC_SYMBOL = "@";
    protected ObjectStack<String> names = new ObjectStack<>();
    protected ObjectStack<JsonNodeWrapper> nodes = new ObjectStack<>();
    private boolean pretty;
    protected ObjectNode rootNode;
    protected ObjectMapper mapper = new ObjectMapper();

    /**
     * Use only for print, not for json-processor
     * @return
     */
    public static JsonHandler newPrettyJsonInstance() {
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.pretty = true;
        return jsonHandler;
    }

    private void beginArray(StructType type) {
        ArrayNodeWrapper item = nodes.peek().addArray();
        nodes.push(item);
        if (!pretty) item.add(type.getKey());
    }

    @Override
    public void beginStruct(int size) throws IOException {
        if (nodes.isEmpty()) { // start handling
            rootNode = mapper.createObjectNode();
            nodes.push(new ObjectNodeWrapper(names, rootNode));
        } else {
            nodes.push(nodes.peek().addObject(names));
        }
    }

    @Override
    public void endStruct() throws IOException {
        nodes.pop();
    }

    @Override
    public void beginList(int size) throws IOException {
        beginArray(StructType.LIST);
    }

    @Override
    public void endList() throws IOException {
        nodes.pop();
    }

    @Override
    public void beginSet(int size) throws IOException {
        beginArray(StructType.SET);
    }

    @Override
    public void endSet() throws IOException {
        nodes.pop();
    }

    @Override
    public void beginMap(int size) throws IOException {
        beginArray(StructType.MAP);
    }

    @Override
    public void endMap() throws IOException {
        nodes.pop();
    }

    @Override
    public void beginKey() throws IOException {
        nodes.push(nodes.peek().addObject(names));
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
        nodes.pop();
    }

    @Override
    public void name(String name) throws IOException {
        names.push(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        nodes.peek().add(value);
    }

    @Override
    public void value(String value) throws IOException {
        if ((!pretty) && value.startsWith(ESC_SYMBOL)) {
            value = ESC_SYMBOL + value;
        }
        nodes.peek().add(value);
    }

    @Override
    public void value(double value) throws IOException {
        nodes.peek().add(value);
    }

    @Override
    public void value(long value) throws IOException {
        nodes.peek().add(value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        String s = (pretty ? "" : ESC_SYMBOL) + Base64.getEncoder().encodeToString(value);
        nodes.peek().add(s);
    }

    @Override
    public void nullValue() throws IOException {
        nodes.peek().addNull();
    }

    @Override
    public JsonNode getResult() throws IOException {
        if (!(nodes.isEmpty() && names.isEmpty())) {
            throw new BadFormatException("Something went wrong: stacks is not empty!");
        }
        return rootNode;
    }
}
