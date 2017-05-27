package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
public class ObjectNodeWrapper implements JsonNodeWrapper {
    private ObjectNode node;

    public ObjectNodeWrapper(ObjectNode node) {
        this.node = node;
    }

    @Override
    public ArrayNodeWrapper addArray(String name) {
        return new ArrayNodeWrapper(node.putArray(name));
    }

    @Override
    public ObjectNodeWrapper addObject(String name) {
        return new ObjectNodeWrapper(node.putObject(name));
    }

    @Override
    public void add(String name, boolean value) {
        node.put(name, value);
    }

    @Override
    public void add(String name, String value) {
        node.put(name, value);
    }

    @Override
    public void add(String name, double value) {
        node.put(name, value);
    }

    @Override
    public void add(String name, long value) {
        node.put(name, value);
    }

    @Override
    public void add(String name, byte[] value) {
        node.put(name, value);
    }

    @Override
    public void addNull(String name) {
        node.putNull(name);
    }
}
