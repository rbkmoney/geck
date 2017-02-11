package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.ByteStack;
import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.exception.BadFormatException;
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

    final static byte STRUCT = 1;

    final static byte LIST = 2;

    final static byte SET = 3;

    final static byte MAP = 4;

    final static byte KEY = 5;

    final static byte VALUE = 6;

    private final Class<R> parentClass;

    private ByteStack stateStack = new ByteStack();

    private LinkedList elementStack = new LinkedList();

    private LinkedList<FieldValueMetaData> valueMetaDataStack = new LinkedList<>();

    private TFieldIdEnum tFieldIdEnum;

    private R result;

    public TBaseHandler(Class<R> parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public void beginStruct(int size) throws IOException {
        try {
            if (stateStack.isEmpty()) {
                TBase tBase = parentClass.newInstance();
                addElement(STRUCT, tBase);
            } else {
                FieldValueMetaData valueMetaData = getChildValueMetaData();
                TBase child = ((StructMetaData) valueMetaData).getStructClass().newInstance();
                saveValue(child, elementStack.peek());
                addElement(STRUCT, child, valueMetaData);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IOException(ex);
        }
    }

    private void addElement(byte state, Object value) {
        addElement(state, value, null);
    }

    private void addElement(byte state, Object value, FieldValueMetaData fieldValueMetaData) {
        stateStack.push(state);
        elementStack.addFirst(value);
        valueMetaDataStack.addFirst(fieldValueMetaData);
    }

    @Override
    public void endStruct() throws IOException {
        TBase tBase = (TBase) end(STRUCT);
        checkRequiredFields(tBase);
        valueMetaDataStack.pop();

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
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case LIST:
                List list = new ArrayList(size);
                saveValue(list, elementStack.peek());
                addElement(LIST, list, valueMetaData);
                break;
            case SET:
                Set set = new HashSet(size);
                saveValue(set, elementStack.peek());
                addElement(SET, set, valueMetaData);
                break;
            default:
                throw new BadFormatException(String.format("value expected '%s', actual collection", type));
        }
    }

    private FieldValueMetaData getChildValueMetaData() {
        FieldValueMetaData valueMetaData = valueMetaDataStack.peek();
        switch (stateStack.peek()) {
            case STRUCT:
                return TBaseUtil.getValueMetaData(tFieldIdEnum, (TBase) elementStack.peek());
            case LIST:
                return ((ListMetaData) valueMetaData).getElementMetaData();
            case SET:
                return ((SetMetaData) valueMetaData).getElementMetaData();
            case KEY:
                return ((MapMetaData) valueMetaData).getKeyMetaData();
            case VALUE:
                return ((MapMetaData) valueMetaData).getValueMetaData();
            default:
                return valueMetaData;
        }
    }

    @Override
    public void endList() throws IOException {
        end(LIST, SET);
        valueMetaDataStack.pop();
    }

    @Override
    public void beginMap(int size) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();

        Map map = new HashMap(size);
        saveValue(map, elementStack.peek());
        addElement(MAP, map, valueMetaData);
    }

    @Override
    public void endMap() throws IOException {
        end(MAP);
        valueMetaDataStack.pop();
    }

    private Object end(byte... requiredStates) throws IOException {
        byte state = stateStack.pop();
        for (byte requireState : requiredStates) {
            if (state == requireState) {
                return elementStack.pop();
            }
        }
        throw new BadFormatException("incorrect state " + state);
    }

    @Override
    public void beginKey() throws IOException {
        stateStack.push(KEY);
    }

    @Override
    public void endKey() throws IOException {
        byte state = stateStack.peek();
        if (state != KEY) {
            throw new BadFormatException("incorrect state " + state);
        }
    }

    @Override
    public void beginValue() throws IOException {
        stateStack.push(VALUE);
    }

    @Override
    public void endValue() throws IOException {

        Object value = end(VALUE);
        Object key = end(KEY);

        ((Map) elementStack.peek()).put(key, value);
    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name, "name must not be null");

        if (tFieldIdEnum != null) {
            throw new BadFormatException("'name' have was caused");
        }

        TBase tBase = (TBase) elementStack.peek();

        tFieldIdEnum = TBaseUtil.getField(name, tBase);
    }

    @Override
    public void value(boolean value) throws IOException {
        value(value, ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

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
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case BYTE:
                value((byte) value, valueMetaData, ThriftType.BYTE);
                break;
            case SHORT:
                value((short) value, valueMetaData, ThriftType.SHORT);
                break;
            case INTEGER:
                value((int) value, valueMetaData, ThriftType.INTEGER);
                break;
            case LONG:
                value(value, valueMetaData, ThriftType.LONG);
                break;
            default:
                throw new BadFormatException(String.format("incorrect type of value: expected '%s', actual integer", type));
        }

    }

    private void value(Object value, ThriftType actualType) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        value(value, valueMetaData, actualType);
    }

    private void value(Object value, FieldValueMetaData valueMetaData, ThriftType actualType) throws IOException {
        ThriftType expectedType = ThriftType.findByMetaData(valueMetaData);
        if (expectedType != actualType) {
            throw new BadFormatException(String.format("incorrect type of value: expected '%s', actual '%s'", expectedType, actualType));
        }

        saveValue(value, elementStack.peek());
    }

    private void saveValue(Object value, Object parent) throws IOException {
        switch (stateStack.peek()) {
            case STRUCT:
                ((TBase) parent).setFieldValue(tFieldIdEnum, value);
                tFieldIdEnum = null;
                break;
            case LIST:
            case SET:
                ((Collection) parent).add(value);
                break;
            case KEY:
            case VALUE:
                elementStack.addFirst(value);
                break;
        }
    }

    @Override
    public void nullValue() throws IOException {
        if (!stateStack.isEmpty()) {
            saveValue(null, elementStack.peek());
        }
    }

    @Override
    public R getResult() throws IOException {
        return result;
    }

}
