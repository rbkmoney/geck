package com.rbkmoney.kebab.kit.tbase.context;

import org.apache.thrift.meta_data.FieldValueMetaData;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class MapKeyElementContext implements ElementContext {

    private final FieldValueMetaData keyMetaData;

    private final MapElementContext parent;

    private Object key;

    public MapKeyElementContext(MapElementContext parent) {
        this.keyMetaData = parent.getKeyMetaData();
        this.parent = parent;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public MapElementContext getParent() {
        return parent;
    }

    public FieldValueMetaData getKeyMetaData() {
        return keyMetaData;
    }
}
