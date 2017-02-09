package com.rbkmoney.kebab.kit.tbase.context;

import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.MapMetaData;

import java.util.Map;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class MapElementContext implements ElementContext {

    private final MapMetaData mapMetaData;

    private Map map;

    public MapElementContext(MapMetaData mapMetaData, Map map) {
        this.mapMetaData = mapMetaData;
        this.map = map;
    }

    public Map getValue() {
        return map;
    }

    public FieldValueMetaData getKeyMetaData() {
        return mapMetaData.getKeyMetaData();
    }

    public FieldValueMetaData getValueMetaData() {
        return mapMetaData.getValueMetaData();
    }
}
