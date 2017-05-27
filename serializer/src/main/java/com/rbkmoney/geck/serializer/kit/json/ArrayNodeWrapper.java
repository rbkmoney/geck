package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
public class ArrayNodeWrapper implements JsonNodeWrapper {
    private ArrayNode node;

    public ArrayNodeWrapper(ArrayNode node) {
        this.node = node;
    }

    @Override
    public ArrayNodeWrapper addArray(String name) {
        return new ArrayNodeWrapper(node.addArray());
    }

    @Override
    public ObjectNodeWrapper addObject(String name) {
        return new ObjectNodeWrapper(node.addObject());
    }

    @Override
    public void add(String name, boolean value) {
        node.add(value);
    }

    @Override
    public void add(String name, String value) {
        node.add(value);
    }

    @Override
    public void add(String name, double value) {
        node.add(value);
    }

    @Override
    public void add(String name, long value) {
        node.add(value);
    }

    @Override
    public void add(String name, byte[] value) {
        node.add(value);
    }

    @Override
    public void addNull(String name) {
        node.addNull();
    }
}
