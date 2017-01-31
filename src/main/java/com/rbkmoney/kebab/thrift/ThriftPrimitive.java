package com.rbkmoney.kebab.thrift;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class ThriftPrimitive extends ThriftElement {

    public static final List<ThriftType> PRIMITIVE_TYPES = Arrays.asList(
            ThriftType.INTEGER, ThriftType.SHORT, ThriftType.LONG,
            ThriftType.BYTE, ThriftType.DOUBLE, ThriftType.BYTE,
            ThriftType.STRING, ThriftType.BOOLEAN
    );

    public static boolean isPrimitive(ThriftType type) {
        return PRIMITIVE_TYPES.contains(type);
    }

    private final Object value;

    public ThriftPrimitive(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    public boolean getAsBoolean() {
        return (boolean) value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public String getAsString() {
        return (String) value;
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public Number getAsNumber() {
        return (Number) value;
    }

    public boolean isInteger() {
        return value instanceof Integer;
    }

    public int getAsInteger() {
        return getAsNumber().intValue();
    }

    public boolean isShort() {
        return value instanceof Short;
    }

    public short getAsShort() {
        return getAsNumber().shortValue();
    }

    public boolean isByte() {
        return value instanceof Byte;
    }

    public byte getAsByteValue() {
        return getAsNumber().byteValue();
    }

    public boolean isDouble() {
        return value instanceof Double;
    }

    public double getAsDouble() {
        return getAsNumber().doubleValue();
    }

    public boolean isLong() {
        return value instanceof Long;
    }

    public long getAsLong() {
        return getAsNumber().longValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftPrimitive that = (ThriftPrimitive) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
