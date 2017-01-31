package com.rbkmoney.kebab.thrift;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class ThriftMapEntry extends ThriftElement {

    private final ThriftElement key;

    private final ThriftElement value;

    public ThriftMapEntry(ThriftElement key, ThriftElement value) {
        this.key = key;
        this.value = value;
    }

    public ThriftElement getKey() {
        return key;
    }

    public ThriftElement getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftMapEntry mapEntry = (ThriftMapEntry) o;

        if (key != null ? !key.equals(mapEntry.key) : mapEntry.key != null) return false;
        return value != null ? value.equals(mapEntry.value) : mapEntry.value == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
