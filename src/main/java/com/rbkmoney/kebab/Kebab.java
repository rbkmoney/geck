package com.rbkmoney.kebab;

import com.rbkmoney.kebab.kit.json.JsonHandler;
import com.rbkmoney.kebab.kit.msgpack.MsgPackHandler;
import com.rbkmoney.kebab.kit.tbase.TBaseProcessor;
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
            TBaseProcessor structProcessor = new TBaseProcessor();
            JsonHandler jsonHandler = new JsonHandler(writer);
            return  structProcessor.process(src, jsonHandler);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] toMsgPack(TBase src, boolean useDict) {
        try {
            MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance(useDict);
            TBaseProcessor serializer = new TBaseProcessor();

            return serializer.process(src, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] write(T src, StructHandler writer) {
        StructProcessor structProcessor = new TBaseStructProcessor();
        try {
            return structProcessor.process(src, writer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
