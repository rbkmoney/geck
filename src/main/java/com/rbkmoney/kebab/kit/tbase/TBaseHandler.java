package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.exception.BadFormatException;
import com.rbkmoney.kebab.kit.tbase.context.*;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseHandler<R extends TBase> implements StructHandler<R> {

    private final Class<R> parentClass;

    private LinkedList<ElementContext> stack = new LinkedList<>();

    private TFieldIdEnum tFieldIdEnum;

    private R result;

    public TBaseHandler(Class<R> parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public void beginStruct(int size) throws IOException {
        try {
            if (stack.isEmpty()) {
                TBase tBase = parentClass.newInstance();

                stack.add(new TBaseElementContext(tBase));
            } else {
                ElementContext elementContext = stack.peek();

                FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, elementContext);
                TBase child = ((StructMetaData) valueMetaData).getStructClass().newInstance();
                saveValueInElementContext(elementContext, child);
                stack.addFirst(new TBaseElementContext(child));
            }

        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IOException(ex);
        }

    }

    @Override
    public void endStruct() throws IOException {
        if (stack.isEmpty()) {
            throw new BadFormatException();
        }

        ElementContext elementContext = stack.pop();

        if (!elementContext.isTBaseElementContext()) {
            throw new BadFormatException("'BeginStruct' not found");
        }

        TBase tBase = ((TBaseElementContext) elementContext).getValue();
        checkRequiredFields(tBase);

        result = (R) tBase;
    }

    private void checkRequiredFields(TBase tBase) throws BadFormatException {
        Map<TFieldIdEnum, FieldMetaData> fieldValueMetaDataMap = tBase.getFieldMetaData();
        for (TFieldIdEnum field : tBase.getFields()) {
            FieldMetaData metaData = fieldValueMetaDataMap.get(field);
            if (metaData.requirementType == TFieldRequirementType.REQUIRED && !tBase.isSet(field)) {
                throw new BadFormatException(String.format("Field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
            }
        }
    }

    @Override
    public void beginList(int size) throws IOException {
        ElementContext elementContext = stack.peek();

        FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, elementContext);
        ThriftType type = ThriftType.findByCode(valueMetaData.getType());

        switch (type) {
            case LIST:
                List list = new ArrayList(size);
                saveValueInElementContext(elementContext, list);
                stack.addFirst(new CollectionElementContext(valueMetaData, list));
                break;
            case SET:
                Set set = new LinkedHashSet(size);
                saveValueInElementContext(elementContext, set);
                stack.addFirst(new CollectionElementContext(valueMetaData, set));
                break;
            default:
                throw new BadFormatException(String.format("Field '%s' value expected '%s', actual collection", tFieldIdEnum.getFieldName(), type));
        }
    }

    @Override
    public void endList() throws IOException {
        if (stack.isEmpty()) {
            throw new BadFormatException("Stack is empty");
        }

        ElementContext context = stack.pop();

        if (!context.isCollectionElementContext()) {
            throw new BadFormatException("'BeginList' not found");
        }
    }

    @Override
    public void beginMap(int size) throws IOException {
        ElementContext elementContext = stack.peek();

        FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, elementContext);

        Map map = new HashMap(size);
        saveValueInElementContext(elementContext, map);
        stack.addFirst(new MapElementContext((MapMetaData) valueMetaData, map));
    }

    @Override
    public void endMap() throws IOException {
        if (stack.isEmpty()) {
            throw new BadFormatException("Stack is empty");
        }

        ElementContext context = stack.pop();

        if (!context.isMapElementContext()) {
            throw new BadFormatException("'beginMap' not found");
        }
    }

    @Override
    public void beginKey() throws IOException {
        ElementContext elementContext = stack.peek();

        if (!elementContext.isMapElementContext()) {
            throw new BadFormatException();
        }

        MapElementContext parent = (MapElementContext) elementContext;

        stack.addFirst(new MapKeyElementContext(parent));
    }

    @Override
    public void endKey() throws IOException {
        ElementContext elementContext = stack.peek();

        if (!elementContext.isMapKeyElementContext()) {
            throw new BadFormatException("'beginKey' not found");
        }
    }

    @Override
    public void beginValue() throws IOException {
        ElementContext elementContext = stack.peek();

        if (!elementContext.isMapKeyElementContext()) {
            throw new BadFormatException("'beginKey' not found");
        }

        MapKeyElementContext mapKeyElementContext = (MapKeyElementContext) elementContext;

        stack.addFirst(new MapValueElementContext(mapKeyElementContext.getParent()));

    }

    @Override
    public void endValue() throws IOException {
        ElementContext elementContext = stack.pop();

        if (!elementContext.isMapValueElementContext()) {
            throw new BadFormatException("'beginValue' not found");
        }

        Object value = ((MapValueElementContext) elementContext).getValue();

        elementContext = stack.pop();

        if (!elementContext.isMapKeyElementContext()) {
            throw new BadFormatException("'beginKey' not found");
        }

        Object key = ((MapKeyElementContext) elementContext).getKey();


        elementContext = stack.peek();

        if (!elementContext.isMapElementContext()) {
            throw new BadFormatException("'beginMap' not found");
        }

        ((MapElementContext) elementContext).getValue().put(key, value);
    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name, "name must not be null");

        ElementContext elementContext = stack.peek();

        if (!elementContext.isTBaseElementContext()) {
            throw new BadFormatException("'name' must be caused only in struct");
        }

        TBase tBase = ((TBaseElementContext) elementContext).getValue();

        tFieldIdEnum = TBaseUtil.getField(name, tBase);
    }

    @Override
    public void value(boolean value) throws IOException {
        value(value, ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        ElementContext elementContext = stack.peek();
        FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, elementContext);
        ThriftType type = ThriftType.findByCode(valueMetaData.getType());

        switch (type) {
            case STRING:
                value(value, ThriftType.STRING);
                break;
            case ENUM:
                Class enumClass = ((EnumMetaData) valueMetaData).getEnumClass();
                value(Enum.valueOf(enumClass, value), ThriftType.ENUM);
                break;
        }
    }

    @Override
    public void value(double value) throws IOException {
        value(value, ThriftType.DOUBLE);
    }

    @Override
    public void value(byte[] value) throws IOException {
        value(value, ThriftType.BINARY);
    }

    @Override
    public void value(long value) throws IOException {
        ElementContext elementContext = stack.peek();
        ThriftType type = TBaseUtil.getType(tFieldIdEnum, elementContext);

        switch (type) {
            case BYTE:
                value((byte) value, ThriftType.BYTE);
                break;
            case SHORT:
                value((short) value, ThriftType.SHORT);
                break;
            case INTEGER:
                value((int) value, ThriftType.INTEGER);
                break;
            case LONG:
                value(value, ThriftType.LONG);
                break;
            default:
                throw new BadFormatException(String.format("value expected '%s', actual integer", type));
        }

    }

    private void value(Object value, ThriftType actualType) throws IOException {
        ElementContext elementContext = stack.peek();

        ThriftType expectedType = TBaseUtil.getType(tFieldIdEnum, elementContext);

        if (expectedType != actualType) {
            throw new BadFormatException(String.format("Value expected '%s', actual '%s'", expectedType, actualType));
        }

        saveValueInElementContext(elementContext, value);
    }

    private void saveValueInElementContext(ElementContext elementContext, Object value) throws IOException {
        if (elementContext.isTBaseElementContext()) {
            if (tFieldIdEnum == null) {
                throw new BadFormatException("'name' not found");
            }
            ((TBaseElementContext) elementContext).getValue().setFieldValue(tFieldIdEnum, value);
            tFieldIdEnum = null;
        }

        if (tFieldIdEnum != null) {
            throw new BadFormatException("'name' must be null");
        }

        if (elementContext.isCollectionElementContext()) {
            ((CollectionElementContext) elementContext).getValue().add(value);
        }

        if (elementContext.isMapKeyElementContext()) {
            ((MapKeyElementContext) elementContext).setKey(value);
        }

        if (elementContext.isMapValueElementContext()) {
            ((MapValueElementContext) elementContext).setValue(value);
        }
    }

    @Override
    public void nullValue() throws IOException {
        if (stack.isEmpty()) {
            return;
        }

        ElementContext elementContext = stack.peek();
        saveValueInElementContext(elementContext, null);
    }

    @Override
    public R getResult() throws IOException {
        return result;
    }

}
