package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.rbkmoney.geck.common.stack.ObjectStack;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
public class ArrayNodeWrapper implements JsonNodeWrapper {
    private ArrayNode node;

    public ArrayNodeWrapper(ArrayNode node) {
        this.node = node;
    }

    @Override
    public ArrayNodeWrapper addArray() {
        return new ArrayNodeWrapper(node.addArray());
    }

    @Override
    public ObjectNodeWrapper addObject(ObjectStack<String> names) {
        return new ObjectNodeWrapper(names, node.addObject());
    }

    @Override
    public void add(boolean value) {
        node.add(value);
    }

    @Override
    public void add(String value) {
        node.add(value);
    }

    @Override
    public void add(double value) {
        node.add(value);
    }

    @Override
    public void add(long value) {
        node.add(value);
    }

    @Override
    public void add(byte[] value) {
        node.add(value);
    }

    @Override
    public void addNull() {
        node.addNull();
    }
}
