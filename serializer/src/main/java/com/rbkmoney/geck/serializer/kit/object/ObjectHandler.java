package com.rbkmoney.geck.serializer.kit.object;

import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;
import static com.rbkmoney.geck.serializer.kit.object.ObjectHandlerConstants.*;

/**
 * Created by vpankrashkin on 09.02.17.
 */
public class ObjectHandler implements StructHandler<Object> {
    private final ByteStack state = new ByteStack(nop);
    private final ObjectStack context = new ObjectStack(null);
    private Object result;

    @Override
    public void beginStruct(int size) throws IOException {
        internValue(new  LinkedHashMap((int) (size * 1.25)), startStruct, StructType.OTHER);
    }

    @Override
    public void endStruct() throws IOException {
        checkState(startStruct, state.pop());
        context.pop();
    }

    @Override
    public void beginList(int size) throws IOException {
        internValue(new ArrayList(size), startList, StructType.LIST);
    }

    @Override
    public void endList() throws IOException {
        checkState(startList, state.pop());
        context.pop();
    }

    @Override
    public void beginSet(int size) throws IOException {
        internValue(new HashSet((int) (size * 1.5)), startSet, StructType.SET);
    }

    @Override
    public void endSet() throws IOException {
        checkState(startSet, state.pop());
        context.pop();
    }

    @Override
    public void beginMap(int size) throws IOException {
        internValue(new ArrayList<>(), startMap, StructType.MAP);
    }

    @Override
    public void endMap() throws IOException {
        checkState(startMap, state.pop());
        context.pop();
    }

    @Override
    public void beginKey() throws IOException {
        checkState(startMap, state.peek());
        state.push(startMapKey);
        context.push(null);
    }

    @Override
    public void endKey() throws IOException {
        checkState(startMapKey, state.peek());
        state.push(endMapKey);
    }

    @Override
    public void beginValue() throws IOException {
        checkState(endMapKey, state.pop());
        state.push(startMapValue);
        context.push(null);
    }

    @Override
    public void endValue() throws IOException {
        checkState(startMapValue, state.pop());
        Object value = context.pop();
        checkState(startMapKey, state.pop());
        Object key = context.pop();
        checkState(startMap, state.peek());
        Map entryStruct = new LinkedHashMap();
        entryStruct.put(ObjectHandlerConstants.MAP_KEY, key);
        entryStruct.put(ObjectHandlerConstants.MAP_VALUE, value);
        ((List) context.peek()).add(entryStruct);
    }

    @Override
    public void name(String name) throws IOException {
        checkState(startStruct, state.peek());
        if (name.length() == 0) {
            throw new BadFormatException("Name cannot be empty");
        }
        state.push(pointName);
        context.push(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        internValue(value, nop, StructType.OTHER);
    }

    @Override
    public void value(String value) throws IOException {
        internValue(escapeString(value), nop, StructType.STRING);
    }

    @Override
    public void value(double value) throws IOException {
        internValue(value, nop, StructType.OTHER);
    }

    @Override
    public void value(long value) throws IOException {
        internValue(value, nop, StructType.OTHER);
    }

    @Override
    public void value(byte[] value) throws IOException {
        internValue(ByteBuffer.wrap(value), nop, StructType.OTHER);
    }

    @Override
    public void nullValue() throws IOException {
        internValue(null, nop, StructType.OTHER);
    }

    @Override
    public Object getResult() throws IOException {
        checkState(nop, state.peek());
        return result;
    }

    private void internValue(Object value, byte newState, StructType type) throws BadFormatException {
        if (state.peek() != pointName) {
            switch (type){
                case MAP:
                    ((List) value).add(MAP_MARK);
                    break;
             /*   case SET:
                    ((List) value).add(SET_MARK);
                    break;*/
                default:
            }
        }

        switch (state.peek()) {
            case nop:
                result = value;
                break;
            case pointName:
                state.pop();
                String name = (String) context.pop();
                if (newState == startMap) {
                    name = injectType(name, type);
                }
                ((Map) context.peek()).put(name, value);
                break;
            case startList:
                ((List)context.peek()).add(value);
                break;
            case startSet:
                ((Set)context.peek()).add(value);
                break;
            case startMapKey:
                context.pop();//remove default key val
                context.push(value);
                break;
            case startMapValue:
                context.pop();//remove default value
                context.push(value);
                break;
            default:
                throw new BadFormatException(String.format("Wrong type in intern value stack, actual: %d", state.peek()));
        }
        if (newState != nop) {
            state.push(newState);
            context.push(value);
        }
    }

    private String injectType(String name, StructType type) {
        return name + TYPE_DELIMITER + type.getKey();
    }

    private String escapeString(String string) {
        int i = 0;
        for (; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == ESCAPE_CHAR || c == TYPE_DELIMITER) {
                break;
            }
        }
        if (i < string.length()) {
            StringBuilder sb = new StringBuilder(string.length() + 1);
            sb.append(string, 0, i);
            boolean escape = false;
            for (; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (escape) {
                    sb.append(c);
                    escape = false;
                } else if (c == ESCAPE_CHAR) {
                    escape = true;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return string;
        }
    }

    private void checkState(byte expectedState, byte actualState) throws BadFormatException {
        if (actualState != expectedState) {
            throw new BadFormatException(String.format("Wrong type in stack, expected: %d, actual: %d", expectedState, actualState));
        }
    }
}
