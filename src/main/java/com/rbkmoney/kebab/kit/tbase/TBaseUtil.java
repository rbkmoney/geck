package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.ThriftType;
import com.rbkmoney.kebab.kit.tbase.context.CollectionElementContext;
import com.rbkmoney.kebab.kit.tbase.context.ElementContext;
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

    public static FieldValueMetaData getValueMetaData(TFieldIdEnum tFieldIdEnum, ElementContext elementContext) {
        if (elementContext.isTBaseElementContext()) {
            return getValueMetaData(tFieldIdEnum, (TBase) elementContext.getValue());
        } else {
            CollectionElementContext collectionElementContext = elementContext.asCollectionElementContext();
            return collectionElementContext.getElementMetaData();
        }
    }

    public static ThriftType getType(TFieldIdEnum tFieldIdEnum, ElementContext context) {
        if (context.isTBaseElementContext()) {
            return getType(tFieldIdEnum, (TBase) context.getValue());
        } else {
            return getType(context.asCollectionElementContext().getElementMetaData());
        }
    }

    public static ThriftType getType(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        FieldValueMetaData valueMetaData = getValueMetaData(tFieldIdEnum, tBase);
        return getType(valueMetaData);
    }

    public static ThriftType getType(FieldValueMetaData valueMetaData) {
        if (valueMetaData.isBinary()) {
            return ThriftType.BINARY;
        }
        return ThriftType.findByCode(valueMetaData.getType());
    }

}
