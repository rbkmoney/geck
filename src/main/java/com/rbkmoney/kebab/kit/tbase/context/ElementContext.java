package com.rbkmoney.kebab.kit.tbase.context;

import com.rbkmoney.kebab.ThriftType;
import org.apache.thrift.meta_data.FieldValueMetaData;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public interface ElementContext<T> {

    ThriftType getType();

    FieldValueMetaData getMetaData();

    T getValue();

    default boolean isTBaseElementContext() {
        return this instanceof TBaseElementContext;
    }

    default TBaseElementContext asTBaseElementContext() {
        return (TBaseElementContext) this;
    }

    default boolean isCollectionElementContext() {
        return this instanceof CollectionElementContext;
    }

    default CollectionElementContext asCollectionElementContext() {
        return (CollectionElementContext) this;
    }
}
