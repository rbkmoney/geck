package com.rbkmoney.kebab.kit.tbase.context;

import com.rbkmoney.kebab.ThriftType;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.ListMetaData;
import org.apache.thrift.meta_data.SetMetaData;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class CollectionElementContext implements ElementContext<Collection> {

    private final ThriftType type;

    private final FieldValueMetaData valueMetaData;

    private Collection collection;

    public CollectionElementContext(ThriftType type, FieldValueMetaData valueMetaData, Collection collection) {
        this.type = type;
        this.valueMetaData = valueMetaData;
        this.collection = collection;
    }

    @Override
    public ThriftType getType() {
        return type;
    }

    @Override
    public FieldValueMetaData getMetaData() {
        return valueMetaData;
    }

    public FieldValueMetaData getElementMetaData() {
        if (isList()) {
            return getListMetaData().getElementMetaData();
        }
        if (isSet()) {
            return getSetMetaData().getElementMetaData();
        }
        throw new IllegalStateException("Unknown collection");
    }

    public boolean isList() {
        return collection instanceof List;
    }

    public boolean isSet() {
        return collection instanceof Set;
    }

    public ListMetaData getListMetaData() {
        return (ListMetaData) valueMetaData;
    }

    public SetMetaData getSetMetaData() {
        return (SetMetaData) valueMetaData;
    }

    @Override
    public Collection getValue() {
        return collection;
    }

    @Override
    public String toString() {
        return "CollectionElementContext{" +
                "collection=" + collection +
                '}';
    }
}
