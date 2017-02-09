package com.rbkmoney.kebab.kit.tbase;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.ThriftType;
import com.rbkmoney.kebab.exception.BadFormatException;
import com.rbkmoney.kebab.kit.tbase.context.CollectionElementContext;
import com.rbkmoney.kebab.kit.tbase.context.ElementContext;
import com.rbkmoney.kebab.kit.tbase.context.TBaseElementContext;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.meta_data.StructMetaData;

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

                stack.add(new TBaseElementContext(ThriftType.STRUCT, null, tBase));
            } else {
                ElementContext elementContext = stack.peek();

                if (elementContext.isTBaseElementContext()) {
                    TBaseElementContext tBaseElementContext = elementContext.asTBaseElementContext();

                    TBase value = tBaseElementContext.getValue();

                    FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, value);
                    ThriftType type = ThriftType.findByCode(valueMetaData.getType());
                    TBase child = ((StructMetaData) valueMetaData).getStructClass().newInstance();

                    value.setFieldValue(tFieldIdEnum, child);
                    stack.addFirst(new TBaseElementContext(type, valueMetaData, child));
                    return;
                }

                if (elementContext.isCollectionElementContext()) {
                    CollectionElementContext collectionElementContext = elementContext.asCollectionElementContext();

                    FieldValueMetaData valueMetaData = collectionElementContext.getElementMetaData();
                    TBase child = ((StructMetaData) valueMetaData).getStructClass().newInstance();

                    collectionElementContext.getValue().add(child);
                    stack.addFirst(new TBaseElementContext(ThriftType.STRUCT, valueMetaData, child));
                }
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
            //TODO add error message
            throw new BadFormatException();
        }

        TBaseElementContext tBaseElementContext = elementContext.asTBaseElementContext();

        TBase tBase = tBaseElementContext.getValue();
        //TODO check required fields

        result = (R) tBase;
    }

    @Override
    public void beginList(int size) throws IOException {
        ElementContext elementContext = stack.peek();

        if (elementContext.isTBaseElementContext()) {
            TBaseElementContext tBaseElementContext = elementContext.asTBaseElementContext();

            TBase value = tBaseElementContext.getValue();
            FieldValueMetaData valueMetaData = TBaseUtil.getValueMetaData(tFieldIdEnum, value);
            ThriftType type = ThriftType.findByCode(valueMetaData.getType());
            switch (type) {
                case LIST:
                    List list = new ArrayList<>(size);
                    value.setFieldValue(tFieldIdEnum, list);
                    stack.addFirst(new CollectionElementContext(type, valueMetaData, list));
                    break;
                case SET:
                    Set set = new HashSet(size);
                    value.setFieldValue(tFieldIdEnum, set);
                    stack.addFirst(new CollectionElementContext(type, valueMetaData, set));
                    break;
                default:
                    throw new BadFormatException(String.format("Field '%s' value expected '%s', actual collection", tFieldIdEnum.getFieldName(), type));
            }
            return;
        }

        if (elementContext.isCollectionElementContext()) {
            CollectionElementContext collectionElementContext = elementContext.asCollectionElementContext();
            FieldValueMetaData valueMetaData = collectionElementContext.getElementMetaData();
            ThriftType type = ThriftType.findByCode(valueMetaData.getType());
            switch (type) {
                case LIST:
                    List list = new ArrayList<>(size);
                    collectionElementContext.getValue().add(list);
                    stack.addFirst(new CollectionElementContext(type, valueMetaData, list));
                    break;
                case SET:
                    Set set = new HashSet(size);
                    collectionElementContext.getValue().add(set);
                    stack.addFirst(new CollectionElementContext(type, valueMetaData, set));
                    break;
                default:
                    throw new BadFormatException(String.format("Field '%s' value expected '%s', actual collection", tFieldIdEnum.getFieldName(), type));
            }
        }
    }

    @Override
    public void endList() throws IOException {
        if (stack.isEmpty()) {
            //TODO add message error
            throw new BadFormatException();
        }

        ElementContext context = stack.pop();

        if (!context.isCollectionElementContext()) {
            //TODO add message error
            throw new BadFormatException();
        }
    }

    @Override
    public void beginMap(int size) throws IOException {

    }

    @Override
    public void endMap() throws IOException {

    }

    @Override
    public void beginKey() throws IOException {

    }

    @Override
    public void endKey() throws IOException {

    }

    @Override
    public void beginValue() throws IOException {

    }

    @Override
    public void endValue() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name, "name must not be null");

        ElementContext elementContext = stack.peek();

        if (!elementContext.isTBaseElementContext()) {
            //TODO error description
            throw new BadFormatException();
        }

        TBase tBase = elementContext.asTBaseElementContext().getValue();

        tFieldIdEnum = TBaseUtil.getField(name, tBase);
    }

    @Override
    public void value(boolean value) throws IOException {
        value(value, ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        value(value, ThriftType.STRING);
    }

    @Override
    public void value(double value) throws IOException {
        value(value, ThriftType.DOUBLE);
    }

    @Override
    public void value(long value) throws IOException {
        ElementContext elementContext = stack.peek();
        ThriftType type;
        if (elementContext.isTBaseElementContext()) {
            type = TBaseUtil.getType(tFieldIdEnum, elementContext.asTBaseElementContext().getValue());
        } else {
            CollectionElementContext collectionElementContext = elementContext.asCollectionElementContext();
            FieldValueMetaData elementMetaData = collectionElementContext.getElementMetaData();
            type = ThriftType.findByCode(elementMetaData.getType());
        }

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
                throw new BadFormatException(String.format("Field '%s' value expected '%s', actual integer", tFieldIdEnum.getFieldName(), type));
        }

    }

    @Override
    public void value(byte[] value) throws IOException {
        value(value, ThriftType.BINARY);
    }

    private void value(Object value, ThriftType actualType) throws IOException {
        ElementContext elementContext = stack.peek();

        if (elementContext.isTBaseElementContext()) {

            TBaseElementContext tBaseElementContext = elementContext.asTBaseElementContext();
            TBase tBase = tBaseElementContext.getValue();

            ThriftType expectedType = TBaseUtil.getType(tFieldIdEnum, tBase);

            if (expectedType != actualType) {
                throw new BadFormatException(String.format("Field '%s' value expected '%s', actual '%s'", tFieldIdEnum.getFieldName(), expectedType, actualType));
            }
            tBase.setFieldValue(tFieldIdEnum, value);
            return;
        }

        if (elementContext.isCollectionElementContext()) {
            CollectionElementContext collectionElementContext = elementContext.asCollectionElementContext();
            collectionElementContext.getValue().add(value);
        }

    }

    @Override
    public void nullValue() throws IOException {
        if (stack.isEmpty()) {
            return;
        }

        ElementContext elementContext = stack.peek();

        if (elementContext.isTBaseElementContext()) {
            TBase tBase = elementContext.asTBaseElementContext().getValue();
            if (tBase != null) {
                FieldMetaData metaData = TBaseUtil.getMetaData(tFieldIdEnum, tBase);
                if (metaData.requirementType == TFieldRequirementType.REQUIRED) {
                    throw new BadFormatException(String.format("Field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
                }
                tBase.setFieldValue(tFieldIdEnum, null);
            }
        }
    }

    @Override
    public R getResult() throws IOException {
        return result;
    }


}
