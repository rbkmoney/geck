package com.rbkmoney.kebab.thrift;

import java.util.Arrays;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class ThriftBinary extends ThriftElement {

    private final byte[] value;

    public ThriftBinary(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftBinary that = (ThriftBinary) o;

        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }
}
