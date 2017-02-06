package com.rbkmoney.kebab;

import com.rbkmoney.kebab.serializer.TBaseSerializer;
import com.rbkmoney.kebab.writer.JsonStructWriter;
import com.rbkmoney.kebab.writer.MsgPackWriter;
import org.apache.thrift.TBase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class Kebab<T extends TBase> {

    public String toJson(T src) {
        StringWriter writer = new StringWriter();
        Serializer serializer = new TBaseSerializer();
        try {
            serializer.write(new JsonStructWriter(writer), src);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return writer.toString();
    }

    public byte[] toMsgPack(T src, boolean useDict) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            MsgPackWriter writer = new MsgPackWriter(os, true, true);
            TBaseSerializer serializer = new TBaseSerializer();

            serializer.write(writer, src);
            writer.close();
            return os.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] write(T src, StructWriter writer) {
        Serializer serializer = new TBaseSerializer();
        try {
            serializer.write(writer, src);
            return new byte[0];
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
