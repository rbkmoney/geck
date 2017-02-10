package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.StructProcessor;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class TBaseProcessor implements StructProcessor<TBase> {

    @Override
    public <R> R process(TBase value, StructHandler<R> handler) throws IOException {
        if (value == null) {
            handler.nullValue();
        } else {
            writeStruct(value, handler);
        }

        return handler.getResult();
    }

    private void writeStruct(TBase value, StructHandler handler) throws IOException {
        TFieldIdEnum[] tFieldIdEnums = value.getFields();
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = value.getFieldMetaData();
        int size = getFieldsCount(value, tFieldIdEnums);

        handler.beginStruct(size);

        for (TFieldIdEnum tFieldIdEnum : tFieldIdEnums) {
            FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
            if (value.isSet(tFieldIdEnum)) {
                handler.name(tFieldIdEnum.getFieldName());
                write(value.getFieldValue(tFieldIdEnum), fieldMetaData.valueMetaData, handler);
            } else if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED) {
                throw new IllegalStateException(String.format("Field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
            }
        }
        handler.endStruct();
    }

    private int getFieldsCount(TBase value, TFieldIdEnum[] tFieldIdEnums) {
        int size = 0;
        for (TFieldIdEnum tFieldIdEnum : tFieldIdEnums) {
            if (value.isSet(tFieldIdEnum)) {
                size++;
            }
        }
        return size;
    }

    private void write(Object object, FieldValueMetaData fieldValueMetaData, StructHandler handler) throws IOException {
        if (object == null) {
            handler.nullValue();
            return;
        }

        ThriftType type = ThriftType.findByCode(fieldValueMetaData.getType());
        boolean isBinary = fieldValueMetaData.isBinary();

        if (isBinary) {
            handler.value((byte[]) object);
        } else {
            switch (type) {
                case BOOLEAN:
                    handler.value((boolean) object);
                    break;
                case STRING:
                    handler.value((String) object);
                    break;
                case BYTE:
                    handler.value((byte) object);
                    break;
                case SHORT:
                    handler.value((short) object);
                    break;
                case INTEGER:
                    handler.value((int) object);
                    break;
                case LONG:
                    handler.value((long) object);
                    break;
                case DOUBLE:
                    handler.value((double) object);
                    break;
                case ENUM:
                    handler.value(object.toString());
                    break;
                case LIST:
                    writeList((List) object, (ListMetaData) fieldValueMetaData, handler);
                    break;
                case SET:
                    writeSet((Set) object, (SetMetaData) fieldValueMetaData, handler);
                    break;
                case MAP:
                    writeMap((Map) object, (MapMetaData) fieldValueMetaData, handler);
                    break;
                case STRUCT:
                    writeStruct((TBase) object, handler);
                    break;
                default:
                    throw new IllegalStateException(String.format("Type '%s' not found", type));
            }
        }
    }

    private void writeSet(Set objectSet, SetMetaData metaData, StructHandler handler) throws IOException {
        handler.beginList(objectSet.size());
        for (Object object : objectSet) {
            write(object, metaData.getElementMetaData(), handler);
        }
        handler.endList();
    }

    private void writeList(List objectList, ListMetaData metaData, StructHandler handler) throws IOException {
        handler.beginList(objectList.size());
        for (Object object : objectList) {
            write(object, metaData.getElementMetaData(), handler);
        }
        handler.endList();
    }

    private void writeMap(Map objectMap, MapMetaData metaData, StructHandler handler) throws IOException {
        handler.beginMap(objectMap.size());
        for (Map.Entry entry : (Set<Map.Entry>) objectMap.entrySet()) {
            handler.beginKey();
            write(entry.getKey(), metaData.getKeyMetaData(), handler);
            handler.endKey();
            handler.beginValue();
            write(entry.getValue(), metaData.getValueMetaData(), handler);
            handler.endValue();
        }
        handler.endMap();
    }


}
