package com.rbkmoney.kebab.kit.tbase.context;

import com.rbkmoney.kebab.ThriftType;
import org.apache.thrift.TBase;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.StructMetaData;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class TBaseElementContext implements ElementContext<TBase> {

    private final ThriftType type;

    private final FieldValueMetaData valueMetaData;

    private TBase tBase;

    public TBaseElementContext(ThriftType type, FieldValueMetaData valueMetaData, TBase tBase) {
        this.type = type;
        this.valueMetaData = valueMetaData;
        this.tBase = tBase;
    }

    @Override
    public ThriftType getType() {
        return type;
    }

    @Override
    public StructMetaData getMetaData() {
        return (StructMetaData) valueMetaData;
    }

    @Override
    public TBase getValue() {
        return tBase;
    }

    @Override
    public String toString() {
        return "TBaseElementContext{" +
                "tBase=" + tBase +
                '}';
    }
}
