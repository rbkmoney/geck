package com.rbkmoney.geck.serializer.kit.json;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckTestUtil;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by iarsanukaev on 15/05/2017.
 */
public class JsonTest {

    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new JsonProcessor().process(
                        new TBaseProcessor().process(invoice1, new JsonHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void jsonKebabTest() throws Exception {
        TestObject testObject = new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>( TestObject.class));
        JsonHandler handler = new JsonHandler();
        String json1 = new TBaseProcessor().process(testObject, handler).toString();
        System.out.println(json1);
        //test re-use handler
        String json2 = new TBaseProcessor().process(testObject, handler).toString();
        System.out.println(json2);
        Assert.assertEquals(json1, json2);
    }
}
