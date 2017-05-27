package com.rbkmoney.geck.serializer.kit.json;

/**
 * Created by inalarsanukaev on 28.05.17.
 */
interface JsonNodeWrapper {
    ArrayNodeWrapper addArray(String name);
    ObjectNodeWrapper addObject(String name);
    void add(String name, boolean value);
    void add(String name, String value);
    void add(String name, double value);
    void add(String name, long value);
    void add(String name, byte[] value);
    void addNull(String name);
}
