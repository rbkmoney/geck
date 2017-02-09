package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.ThriftType;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;

import java.util.Map;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseUtil {

    public static TFieldIdEnum getField(String name, TBase tBase) {
        for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
            if (tFieldIdEnum.getFieldName().equals(name)) {
                return tFieldIdEnum;
            }
        }
        throw new IllegalStateException(String.format("Field '%s' not found", name));
    }

    public static FieldMetaData getMetaData(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tBase.getFieldMetaData();
        return fieldMetaDataMap.get(tFieldIdEnum);
    }

    public static FieldValueMetaData getValueMetaData(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        return getMetaData(tFieldIdEnum, tBase).valueMetaData;
    }

    public static ThriftType getType(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        FieldValueMetaData valueMetaData = getValueMetaData(tFieldIdEnum, tBase);
        if (valueMetaData.isBinary()) {
            return ThriftType.BINARY;
        }
        return ThriftType.findByCode(valueMetaData.getType());
    }

}
