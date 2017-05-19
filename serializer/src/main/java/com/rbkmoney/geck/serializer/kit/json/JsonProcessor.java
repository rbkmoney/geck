package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.io.Writer;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by inalarsanukaev on 16.03.17.
 */
public class JsonProcessor implements StructProcessor<Writer> {
    @Override
    public <R> R process(Writer value, StructHandler<R> handler) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(value.toString());
        System.out.println(jsonNode.size());
        processNode(jsonNode, null, handler);
        return handler.getResult();
    }

    private void processNode(JsonNode jsonNode, String name, StructHandler handler) throws IOException {
        if (jsonNode != null) {
            if (name != null) {
                handler.name(name);
            }
            if (jsonNode.isNull()) {
                handler.nullValue();
            } else if (jsonNode.isBoolean()) {
                handler.value(jsonNode.booleanValue());
            } else if (jsonNode.isLong() || jsonNode.isInt()) {
                handler.value(jsonNode.longValue());
            } else if (jsonNode.isDouble()) {
                handler.value(jsonNode.doubleValue());
            } else if (jsonNode.isTextual()) {
                String value = jsonNode.textValue();
                int length = JsonHandler.BINARY.length();
                String data = value.substring(length);
                if (value.startsWith(JsonHandler.BINARY)) {
                    handler.value(Base64.getDecoder().decode(data));
                } else if (value.startsWith(JsonHandler.STRING)) {
                    handler.value(data);
                } else {
                    throw new BadFormatException("Incorrect text value. Must be starts with one of them: "+JsonHandler.STRING+", "+JsonHandler.BINARY);
                }
            } else if (jsonNode.isObject()) {
                handler.beginStruct(jsonNode.size());
                processChildStructNodes(jsonNode.fields(), handler);
                handler.endStruct();
            } else if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                if (!elements.hasNext()) {
                    throw new BadFormatException("Incorrect structure of array. First element should exist!");
                }
                String arrCode = elements.next().textValue();
                StructType arrType = StructType.valueOfKey(arrCode);
                switch (arrType) {
                    case LIST:
                        handler.beginList(jsonNode.size());
                        processChildArrNodes(elements, handler);
                        handler.endList();
                        break;
                    case SET:
                        handler.beginSet(jsonNode.size());
                        processChildArrNodes(elements, handler);
                        handler.endSet();
                        break;
                    case MAP:
                        handler.beginMap(jsonNode.size());
                        while (elements.hasNext()) {
                            JsonNode mapEntry = elements.next();
                            handler.beginKey();
                            processNode(mapEntry.get(JsonHandler.KEY), null, handler);
                            handler.endKey();
                            handler.beginValue();
                            processNode(mapEntry.get(JsonHandler.VALUE), null, handler);
                            handler.endValue();
                        }
                        handler.endMap();
                        break;
                    default:
                        new BadFormatException("Unknown type of node: " + arrType + ". Must be on of them : " + StructType.LIST + ", " + StructType.SET + ", " + StructType.MAP);
                }
            }
        }
    }

    private void processChildArrNodes(Iterator<JsonNode> elements, StructHandler handler) throws IOException {
        while (elements.hasNext()) {
            processNode(elements.next(), null, handler);
        }
    }

    private void processChildStructNodes(Iterator<Map.Entry<String, JsonNode>> fields, StructHandler handler) throws IOException {
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            processNode(next.getValue(), next.getKey(), handler);
        }
    }
}