package com.rbkmoney.kebab.kit.tbase.context;

import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.ListMetaData;
import org.apache.thrift.meta_data.SetMetaData;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class CollectionElementContext implements ElementContext {

    private final FieldValueMetaData valueMetaData;

    private Collection collection;

    public CollectionElementContext(FieldValueMetaData valueMetaData, Collection collection) {
        this.valueMetaData = valueMetaData;
        this.collection = collection;
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
