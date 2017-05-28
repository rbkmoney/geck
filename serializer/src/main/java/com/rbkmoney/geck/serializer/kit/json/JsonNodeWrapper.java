package com.rbkmoney.geck.serializer.kit.json;

import com.rbkmoney.geck.common.stack.ObjectStack;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
interface JsonNodeWrapper {
    ArrayNodeWrapper addArray();
    ObjectNodeWrapper addObject(ObjectStack<String> names);
    void add(boolean value);
    void add(String value);
    void add(double value);
    void add(long value);
    void add(byte[] value);
    void addNull();
}
