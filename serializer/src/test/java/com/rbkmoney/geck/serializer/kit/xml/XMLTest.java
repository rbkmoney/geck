package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckUtil;
import com.rbkmoney.geck.serializer.kit.mock.FixedValueGenerator;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by inalarsanukaev on 17.03.17.
 */
public class XMLTest {
    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new XMLProcessor().process(
                        new TBaseProcessor().process(invoice1, new XMLHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }
    @Test
    public void xmlKebabTest() throws Exception {
        TestObject invoice = new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new TestObject(), new TBaseHandler<>(TestObject.class));
        XMLHandler handler = new XMLHandler();
        String xml = new TBaseProcessor().process(invoice, handler).toString();
        //test re-use handler
        new TBaseProcessor().process(invoice, handler);
        System.out.println(xml);
    }
}
