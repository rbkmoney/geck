package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rbkmoney.geck.common.stack.ObjectStack;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
public class ObjectNodeWrapper implements JsonNodeWrapper {
    private ObjectStack<String> names;
    private ObjectNode node;

    public ObjectNodeWrapper(ObjectStack<String> names, ObjectNode node) {
        this.names = names;
        this.node = node;
    }

    @Override
    public ArrayNodeWrapper addArray() {
        return new ArrayNodeWrapper(node.putArray(names.pop()));
    }

    @Override
    public ObjectNodeWrapper addObject(ObjectStack<String> names) {
        return new ObjectNodeWrapper(names, node.putObject(names.pop()));
    }

    @Override
    public void add(boolean value) {
        node.put(names.pop(), value);
    }

    @Override
    public void add(String value) {
        node.put(names.pop(), value);
    }

    @Override
    public void add(double value) {
        node.put(names.pop(), value);
    }

    @Override
    public void add(long value) {
        node.put(names.pop(), value);
    }

    @Override
    public void add( byte[] value) {
        node.put(names.pop(), value);
    }

    @Override
    public void addNull() {
        node.putNull(names.pop());
    }
}
