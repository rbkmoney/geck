package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class JsonHandler1 implements StructHandler<JsonNode> {

    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String ESC_SYMBOL = "@";
    protected ObjectStack<String> names = new ObjectStack<>();
    protected ObjectStack<JsonNodeWrapper> nodes = new ObjectStack<>();
    protected boolean pretty;
    protected ObjectNode rootNode;
    protected JsonNodeWrapper currNode;
    protected ObjectMapper mapper = new ObjectMapper();

    protected void setPretty(boolean pretty) {
        this.pretty = pretty;
    }

    /**
     * Use only for print, not for json-processor
     * @return
     */
    public static JsonHandler1 newPrettyJsonInstance(){
        JsonHandler1 jsonHandler1 = new JsonHandler1();
        jsonHandler1.setPretty(true);
        return jsonHandler1;
    }

    private void beginArray(StructType type) {
        ArrayNodeWrapper item = nodes.peek().addArray();
        nodes.push(item);
        if (!pretty) item.add(type.getKey());
    }

    @Override
    public void beginStruct(int size) throws IOException {
        if (currNode == null) { // start handling
            rootNode = mapper.createObjectNode();
            currNode = new ObjectNodeWrapper(names, rootNode);
            nodes.push(currNode);
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
            value = ESC_SYMBOL+value;
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
        currNode = null;
        return rootNode;
    }
}
