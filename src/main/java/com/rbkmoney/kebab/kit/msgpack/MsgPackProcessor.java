package com.rbkmoney.kebab.kit.msgpack;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.StructProcessor;
import com.rbkmoney.kebab.exception.BadFormatException;
import gnu.trove.map.hash.TCharObjectHashMap;
import org.msgpack.core.ExtensionTypeHeader;
import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.Value;
import org.msgpack.value.ValueType;

import java.io.IOException;

import static com.rbkmoney.kebab.kit.msgpack.MsgPackFlags.*;
import static com.rbkmoney.kebab.kit.msgpack.StringUtil.fromAsciiBytes;

/**
 * Created by vpankrashkin on 07.02.17.
 */
public abstract class MsgPackProcessor<S> implements StructProcessor<S> {
    private final TCharObjectHashMap<String> dictionary;
    private final char noDictEntryValue = 0;

    public MsgPackProcessor() {
        this.dictionary = new TCharObjectHashMap<>(64, 0.5f, noDictEntryValue) ;
    }

    @Override
    public <R> R process(S value, StructHandler<R> handler) throws IOException {
        MessageUnpacker unpacker = getUnpacker(value);
        try {
            processStart(unpacker, handler);
        } finally {
            releaseUnpacker(unpacker, value);
        }
        return handler.getResult();
    }

    private void processStart(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        if (unpacker.hasNext()) {
            processStruct(unpacker, handler, getExtensionTypeHeader(startStruct, unpacker));
        }
    }

    private void processStruct(MessageUnpacker unpacker, StructHandler handler, ExtensionTypeHeader typeHeader) throws IOException {
        int length = typeHeader.getLength();
        handler.beginStruct(length);
        for (int i = 0; i < length; ++i) {
            processName(unpacker, handler);
            processValue(unpacker, handler);
        }
        handler.endStruct();
    }

    private void processName(MessageUnpacker unpacker, StructHandler handler) throws IOException {
        MessageFormat format = unpacker.getNextFormat();
        String name;
        switch (format.getValueType()) {
            case STRING:
                name = unpacker.unpackString();
                break;
            case EXTENSION:
                ExtensionTypeHeader header = unpacker.unpackExtensionTypeHeader();
                switch (header.getType()) {
                    case pointDictionary:
                        byte[] data = unpacker.readPayload(header.getLength());
                        int key = unpacker.unpackInt();
                        name = putInDictionary(key, data);
                        break;
                    case pointDictionaryRef:
                        name = getFromDictionary(unpacker.unpackInt());
                        break;
                    default:
                        throw new BadFormatException("Bad extension type: "+ header.getType() + " [dict point or ref expected]");
                }
                break;
            default:
                throw new BadFormatException("Bad format type: "+ format + " [str or ext expected]");
        }
        handler.name(name);
    }

    private void processValue(MessageUnpacker unpacker, StructHandler handler, MessageFormat format, ExtensionTypeHeader typeHeader) throws IOException {
        switch (format.getValueType()) {
            case BOOLEAN:
                handler.value(unpacker.unpackBoolean());
                break;
            case INTEGER:
                Value value = unpacker.unpackValue();

            case STRING:
                handler.value(unpacker.unpackString());
                break;
            case NIL:
                handler.nullValue();
                break;
            case ARRAY:
                processList(unpacker, handler, format);
                break;
            case MAP:
                processMap(unpacker, handler, format);
                break;
            case BINARY:
                handler.value(unpacker.readPayload(unpacker.unpackBinaryHeader()));
                break;

        }
    }

    private void processList(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) {

    }

    private void processMap(MessageUnpacker unpacker, StructHandler handler, MessageFormat format) {

    }

    private String putInDictionary(int key, byte[] data) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: "+ key);
        }
        String str = fromAsciiBytes(data);
        if (dictionary.putIfAbsent((char) key, str) != null) {
            throw new BadFormatException("Dictionary key is already set: " + key);
        }
        return str;
    }

    private String getFromDictionary(int key) throws BadFormatException {
        if (key > Character.MAX_VALUE) {
            throw new BadFormatException("Dictionary key is too long: "+ key);
        }
        String str = dictionary.get((char) key);
        if (str == null) {
            throw new BadFormatException("Not found dictionary key: " + key);
        }
        return str;
    }

    private ExtensionTypeHeader getExtensionTypeHeader(int expectedType, MessageUnpacker unpacker) throws IOException {
        getHeader(ValueType.EXTENSION, unpacker);
        ExtensionTypeHeader extHeader = unpacker.unpackExtensionTypeHeader();
        if (extHeader.getType() == expectedType) {
            return extHeader;
        }
        throwBadFormat("wrong extension type", startStruct, extHeader);
        return null;
    }

    private MessageFormat getHeader(ValueType expectedType, MessageUnpacker unpacker) throws IOException {
        MessageFormat format = unpacker.getNextFormat();
        if (format.getValueType() == expectedType) {
            return format;
        }
        throwBadFormat("wrong format type", expectedType, format);
        return null;
    }

    protected abstract MessageUnpacker getUnpacker(S value);

    protected abstract MessageUnpacker releaseUnpacker(MessageUnpacker unpacker, S value);

    private static void throwBadFormat(String message, ValueType expectedType, MessageFormat actualFormat) throws BadFormatException {
        throw new BadFormatException("MsgPack bad format: " + message + ", expected type: " + expectedType + ", actual format: " + actualFormat);
    }

    private static void throwBadFormat(String message, int expectedType, ExtensionTypeHeader actualType) throws BadFormatException {
        throw new BadFormatException("MsgPack bad format: " + message + ", expected type: " + expectedType + ", actual type: " + actualType);
    }
}
