package com.rbkmoney.kebab.thrift;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class ThriftNull extends ThriftElement {

    public static final ThriftNull INSTANCE = new ThriftNull();

    private ThriftNull() {

    }

    @Override
    public int hashCode() {
        return ThriftNull.class.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other instanceof ThriftNull;
    }

}
