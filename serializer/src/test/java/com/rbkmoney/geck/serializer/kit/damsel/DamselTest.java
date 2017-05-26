package com.rbkmoney.geck.serializer.kit.damsel;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.damsel.v113.domain.Invoice;
import com.rbkmoney.damsel.v113.payment_processing.*;
import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckTestUtil;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.mock.FixedValueGenerator;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.object.ObjectHandler;
import com.rbkmoney.geck.serializer.kit.object.ObjectProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.kit.xml.XMLHandler;
import com.rbkmoney.geck.serializer.kit.xml.XMLProcessor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.*;

/**
 * Created by inalarsanukaev on 22.02.17.
 */
public class DamselTest {
    @Test
    public void jsonInvoiceTest() throws JSONException, IOException {
        //com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted invoice = new MockTBaseProcessor().process(new com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted.class));
        com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted invoice = new MockTBaseProcessor().process(new com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted.class));
        String json = new TBaseProcessor().process(invoice, JsonHandler.newWriterInstance()).toString();
        System.out.println(json);
        new JSONObject(json);
    }
    @Test
    public void xmlInvoiceTest() throws Exception {
        com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted invoice = new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted.class));
        String xml = new TBaseProcessor().process(invoice, new XMLHandler()).toString();
        System.out.println(xml);
    }
    @Test
    public void testInvoiceMsgPack() throws IOException {
        InvoicePaymentStarted invoice = GeckTestUtil.getInvoicePaymentStarted();
        byte[] serializedData = new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new ObjectProcessor().process(
                        MsgPackProcessor.newBinaryInstance().process(
                                new TBaseProcessor().process(
                                        invoice1,
                                        MsgPackHandler.newBufferedInstance(true)),
                                new ObjectHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }
    @Test
    public void testInvoiceBackTransform2() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new ObjectProcessor().process(
                                new TBaseProcessor().process(
                                        invoice1,
                                        new ObjectHandler()),
                                MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(InvoicePaymentStarted.class)
                );
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void pathMatchingTest() throws IOException {
        List<com.rbkmoney.damsel.v113.payment_processing.Event> eventV113List = new ArrayList<>();

        //generated 10 "invoice created" events
        addV113InvoiceEvents(eventV113List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_created(new InvoiceCreated(new Invoice()))));

        //generated 10 "invoice payment started" events
        addV113InvoiceEvents(eventV113List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_payment_event(InvoicePaymentEvent.invoice_payment_started(new com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted()))));

        //generated 10 "invoice status changed" events
        addV113InvoiceEvents(eventV113List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_status_changed(new InvoiceStatusChanged())));

        //generated 10 "invoice payment status changed" events
        addV113InvoiceEvents(eventV113List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_payment_event(InvoicePaymentEvent.invoice_payment_status_changed(new com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStatusChanged()))));


        for (com.rbkmoney.damsel.v113.payment_processing.Event event113Thrift : eventV113List) {
            Object event113Jolt = new TBaseProcessor().process(event113Thrift, new ObjectHandler());
            List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream("/spec_event.json"));
            Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
            Object event130Jolt = chainr.transform(event113Jolt);
            try {
                new ObjectProcessor().process(event130Jolt, new TBaseHandler<>(com.rbkmoney.damsel.v130.payment_processing.Event.class));
            } catch (IOException ex) {
                System.out.println("v113:\n" + JsonUtils.toPrettyJsonString(event113Jolt));
                System.out.println("v130:\n" + JsonUtils.toPrettyJsonString(event130Jolt));
                throw ex;
            }
        }
    }

    private void addV113InvoiceEvents(List<com.rbkmoney.damsel.v113.payment_processing.Event> events, int count, EventPayload eventPayload) throws IOException {
        for (int i = 0; i < count; i++) {
            com.rbkmoney.damsel.v113.payment_processing.Event event113 = new com.rbkmoney.damsel.v113.payment_processing.Event();
            event113.setPayload(eventPayload);

            events.add(new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                    .process(event113,
                            new TBaseHandler<>(com.rbkmoney.damsel.v113.payment_processing.Event.class)));
        }
    }

    @Test
    public void test() throws IOException {
        com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted invoice_v130 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted.class));
        invoice_v130.cash_flow = null;
        invoice_v130.payment.trx.extra = new HashMap<>();
        String json_v130 = new TBaseProcessor().process(invoice_v130, JsonHandler.newWriterInstance()).toString();
        System.out.println(json_v130);
        Object inputJSON_v130 = JsonUtils.jsonToObject(json_v130);

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream("/spec_invoice.json"));
        Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

        Object transformedOutput = chainr.transform(inputJSON_v130);
        String transformedInvoice = JsonUtils.toJsonString(transformedOutput);
        System.out.println(transformedInvoice);

        com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted invoice_v113 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted.class));
        invoice_v113.cash_flow = null;
        String json_v113 = new TBaseProcessor().process(invoice_v113, JsonHandler.newWriterInstance()).toString();
        Object inputJSON_v113 = JsonUtils.jsonToObject(json_v113);
        System.out.println(json_v113);

        Assert.assertEquals(inputJSON_v113,transformedOutput);
    }
    @Test
    public void testXslt() throws IOException, TransformerException {
        com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted invoice_v130 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted.class));
        invoice_v130.cash_flow = null;
        invoice_v130.payment.trx.extra = new HashMap<>();
        DOMResult domResult_v130 = new TBaseProcessor().process(invoice_v130, new XMLHandler());
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(this.getClass().getResourceAsStream("/transform.xslt"));
        Transformer transformer = factory.newTransformer(xslt);
       // StringWriter string_v130 = new StringWriter();
        DOMResult domResult_t_v113 = new DOMResult();
        transformer.transform(new DOMSource(domResult_v130.getNode()), domResult_t_v113);
        com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted invoice_t_v113 =
                new XMLProcessor().process(domResult_t_v113, new TBaseHandler<>(com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted.class));
        //System.out.println(string_v130.toString());

        com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted invoice_v113 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted(),
                        new TBaseHandler<>(com.rbkmoney.damsel.v113.payment_processing.InvoicePaymentStarted.class));
        invoice_v113.cash_flow = null;

        Assert.assertEquals(invoice_t_v113, invoice_v113);
    }
}
