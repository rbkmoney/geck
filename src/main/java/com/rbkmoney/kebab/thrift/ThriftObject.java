package com.rbkmoney.kebab.thrift;

import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.FieldMetaData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class ThriftObject extends ThriftElement {

    private final List<TFieldIdEnum> fields;

    private final Map<TFieldIdEnum, FieldMetaData> metaDataMap;

    private Map<TFieldIdEnum, ThriftElement> childs = new HashMap<>();

    public ThriftObject(TFieldIdEnum[] fields, Map<TFieldIdEnum, FieldMetaData> metaDataMap) {
        this(Arrays.asList(fields), metaDataMap);
    }

    public ThriftObject(List<TFieldIdEnum> fields, Map<TFieldIdEnum, FieldMetaData> metaDataMap) {
        this.fields = fields;
        this.metaDataMap = metaDataMap;
    }

    private TFieldIdEnum getField(String fieldName) {
        return fields.stream()
                .filter(t -> t.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Field '%s' not found", fieldName)));
    }

    public boolean has(String childName) {
        TFieldIdEnum tFieldIdEnum = getField(childName);
        return has(tFieldIdEnum);
    }

    public boolean has(TFieldIdEnum tFieldIdEnum) {
        return childs.containsKey(tFieldIdEnum);
    }

    public boolean hasChilds() {
        return !childs.isEmpty();
    }

    public ThriftElement get(String childName) {
        TFieldIdEnum tFieldIdEnum = getField(childName);
        ThriftElement element = childs.get(tFieldIdEnum);
        if ((element == null || element.isThriftNull()) && isChildRequired(tFieldIdEnum)) {
            throw new IllegalStateException(String.format("Field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
        }
        return element;
    }

    public void add(String childName, ThriftElement value) {
        add(getField(childName), value);
    }

    private boolean isChildRequired(TFieldIdEnum tFieldIdEnum) {
        return metaDataMap.get(tFieldIdEnum).requirementType == TFieldRequirementType.REQUIRED;
    }

    public void add(TFieldIdEnum childName, ThriftElement value) {
        if (value == null) {
            if (isChildRequired(childName)) {
                throw new IllegalStateException(String.format("Field '%s' is required and must not be null", childName.getFieldName()));
            }
            value = ThriftNull.INSTANCE;
        }
        childs.put(childName, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftObject that = (ThriftObject) o;

        if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;
        if (metaDataMap != null ? !metaDataMap.equals(that.metaDataMap) : that.metaDataMap != null) return false;
        return childs != null ? childs.equals(that.childs) : that.childs == null;
    }

    @Override
    public int hashCode() {
        int result = fields != null ? fields.hashCode() : 0;
        result = 31 * result + (metaDataMap != null ? metaDataMap.hashCode() : 0);
        result = 31 * result + (childs != null ? childs.hashCode() : 0);
        return result;
    }
}
