package com.rbkmoney.geck.serializer.kit.damsel;

import com.rbkmoney.damsel.domain.Invoice;
import com.rbkmoney.geck.serializer.GeckUtil;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by inalarsanukaev on 22.02.17.
 */
public class DamselTest {
    @Test
    public void jsonDamselTest() throws JSONException, IOException {
        Invoice invoice = GeckUtil.getInvoice();
        String json = new TBaseProcessor().process(invoice, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }
    @Test
    public void testMsgPack() throws IOException {
        Invoice invoice = GeckUtil.getInvoice();
        byte[] serializedData = new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testBackTransform() throws IOException {
        Invoice invoice1 = GeckUtil.getInvoice();
        Invoice invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new TBaseProcessor().process(invoice1, MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(Invoice.class));

        String invoiceS1 = invoice1.toString();
        String invoiceS2 = invoice2.toString();
        System.out.println(invoiceS1);
        System.out.println(invoiceS2);
        //короче тут задница
        for (int i = 0; i < invoiceS1.length(); ++i) {
            if (invoiceS1.charAt(i) != invoiceS2.charAt(i)) {
                throw new RuntimeException(i + " " + invoiceS1.charAt(i) + " " + invoiceS2.charAt(i));
            }
         }
        Assert.assertEquals(invoice1, invoice2);
        Assert.assertEquals(invoiceS1, invoiceS2);
    }
}
