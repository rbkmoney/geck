package com.rbkmoney.kebab;

import com.rbkmoney.kebab.handler.JsonStructHandler;
import com.rbkmoney.kebab.kit.msgpack.MsgPackHandler;
import com.rbkmoney.kebab.processor.TBaseStructProcessor;
import org.apache.thrift.TBase;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class Kebab {

    public boolean remove() {
        return true;
    }

    public String toJson(TBase src) {
        try {
            StringWriter writer = new StringWriter();
            TBaseStructProcessor structProcessor = new TBaseStructProcessor();
            JsonStructHandler jsonStructHandler = new JsonStructHandler(writer);
            return  structProcessor.process(src, jsonStructHandler);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] toMsgPack(TBase src, boolean useDict) {
        try {
            MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance(useDict);
            TBaseStructProcessor serializer = new TBaseStructProcessor();

            return serializer.process(src, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <R> R write(TBase src, StructHandler<R> writer) {
        StructProcessor<TBase> structProcessor = new TBaseStructProcessor();
        try {
            return structProcessor.process(src, writer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
