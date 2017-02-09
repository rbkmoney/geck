package com.rbkmoney.kebab.kit.tbase.context;

import org.apache.thrift.meta_data.FieldValueMetaData;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class MapValueElementContext implements ElementContext {

    private final FieldValueMetaData valueMetaData;

    private final MapElementContext parent;

    private Object value;

    public MapValueElementContext(MapElementContext parent) {
        this.valueMetaData = parent.getValueMetaData();
        this.parent = parent;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MapElementContext getParent() {
        return parent;
    }

    public FieldValueMetaData getValueMetaData() {
        return valueMetaData;
    }
}
