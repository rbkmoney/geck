package com.rbkmoney.kebab;

import com.rbkmoney.kebab.handler.JsonStructHandler;
import com.rbkmoney.kebab.handler.MsgPackHandler;
import com.rbkmoney.kebab.processor.TBaseStructProcessor;
import org.apache.thrift.TBase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class Kebab<T extends TBase> {

    public String toJson(T src) {
        try {
            StringWriter writer = new StringWriter();
            TBaseStructProcessor structProcessor = new TBaseStructProcessor();
            JsonStructHandler jsonStructHandler = new JsonStructHandler(writer);
            return  structProcessor.process(src, jsonStructHandler);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] toMsgPack(T src, boolean useDict) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MsgPackHandler handler = new MsgPackHandler(os, true, true);
            TBaseStructProcessor serializer = new TBaseStructProcessor();

            serializer.process(src, handler);
            return os.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] write(T src, StructHandler writer) {
        StructProcessor structProcessor = new TBaseStructProcessor();
        try {
            structProcessor.process(src, writer);
            return new byte[0];
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
