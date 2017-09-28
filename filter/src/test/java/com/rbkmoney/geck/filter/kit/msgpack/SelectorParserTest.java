package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.condition.IsNullCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;
import com.rbkmoney.geck.filter.test.filter.*;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by vpankrashkin on 26.09.17.
 */
public class SelectorParserTest {

    @Test
    public void test1LvlParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("id", new ConditionRule(new EqualsCondition(1L))), preparePaidSample1(null));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlNotNullParserParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.paid", new ConditionRule(new IsNullCondition().not())), preparePaidSample1(null));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.canceled.details", new ConditionRule(new EqualsCondition("fail"))), prepareCanceledSample1("fail", null));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void testAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("*", new ConditionRule(new EqualsCondition("i_details_p"))), preparePaidSample1(null));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlMiddleAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.*.value", new ConditionRule(new EqualsCondition("pvalue"))), preparePaidSample1(null));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlTailAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("data.*", new ConditionRule(new EqualsCondition("data_val"))), preparePaidSample1("data_val"));
        Assert.assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.*.*", new ConditionRule(new EqualsCondition("123"))), preparePaidSample1("data_val"));
        Assert.assertEquals(1, rules.size());
    }

    List<Rule> applyRules(Map.Entry<Selector, Selector.Context[]> selectorEntry, byte[] data) throws IOException {
        StructVisitor visitor = new StructVisitor(selectorEntry.getKey(), () -> selectorEntry.getValue());
        FilteringHandler handler = new FilteringHandler(visitor);

        List<Rule> rules = MsgPackProcessor.newBinaryInstance().process(data, handler);
        return rules;
    }

    byte[] preparePaidSample1(String dataVal) throws IOException {
        Invoice invoice = createInvoice(1,"i_details_p", dataVal, iPaidStatus("123"));
       return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    byte[] prepareCanceledSample1(String details, String dataVal) throws IOException {
        Invoice invoice = createInvoice(1,"i_details_c", dataVal,  iCanceledStatus(details));
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }


    private static Invoice createInvoice(int invId, String details, String dataVal, InvoiceStatus invoiceStatus) {
        Invoice invoice = new Invoice(invId, invoiceStatus, new IData(dataVal) {{setDataOptVal("opt_data");}});
        invoice.setIDetails(details);
        return invoice;
    }

    private static InvoiceStatus iPaidStatus(String date) {
        return InvoiceStatus.paid(new IStatusPaid(date){{setValue("pvalue");}});
    }

    private static InvoiceStatus iCanceledStatus(String details) {
        return InvoiceStatus.canceled(new IStatusCanceled(details){{setValue("cvalue");}});
    }

}
