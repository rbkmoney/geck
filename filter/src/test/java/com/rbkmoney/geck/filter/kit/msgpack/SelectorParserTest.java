package com.rbkmoney.geck.filter.kit.msgpack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.condition.IsNullCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;
import com.rbkmoney.geck.filter.test.filter.*;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import org.apache.thrift.TBase;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by vpankrashkin on 26.09.17.
 */
public class SelectorParserTest {

    @Test
    public void test1LvlParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("id", new ConditionRule(new EqualsCondition(1L))), preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlNotNullParserParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.paid", new ConditionRule(new IsNullCondition().not())), preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.canceled.details", new ConditionRule(new EqualsCondition("fail"))), prepareCanceledSample1("fail", ""));
        assertEquals(1, rules.size());
    }

    @Test
    public void testAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("*", new ConditionRule(new EqualsCondition("i_details_p"))), preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlMiddleAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("status.*.value", new ConditionRule(new EqualsCondition("pvalue"))), preparePaidSample1(""));
        assertEquals(1, rules.size());
    }

    @Test
    public void test3LvlTailAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("data.*", new ConditionRule(new EqualsCondition("data_val"))), preparePaidSample1("data_val"));
        assertEquals(1, rules.size());
    }

    @Test
    public void test2LvlAnyNameParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("lvl_data.*.*", new ConditionRule(new EqualsCondition("lvl22val2"))), preparePaidSample1("data_val"));
        assertEquals(1, rules.size());
    }

    @Test
    public void testAnyElemParser() throws IOException {
        List<Rule> rules = applyRules(new SelectorParser().parse("data_coll.data_list.[*].data_val", new ConditionRule(new EqualsCondition("1"))), preparePaidSample2("value", true, 2));
        assertEquals(1, rules.size());
    }

    List<Rule> applyRules(Selector.Config[] configs, byte[] data) throws IOException {
        StructVisitor visitor = new StructVisitor(() -> configs);
        FilteringHandler handler = new FilteringHandler(visitor);

        List<Rule> rules = MsgPackProcessor.newBinaryInstance().process(data, handler);
        return rules;
    }

    byte[] preparePaidSample1(String dataVal) throws IOException {
        Invoice invoice = createInvoice(1,"i_details_p", dataVal, iPaidStatus("123"));
        printJson(invoice);
       return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    byte[] preparePaidSample2(String dataVal, boolean useList, int collCount) throws IOException {
        Invoice invoice = createInvoice(1,"i_details_p", dataVal, iPaidStatus("123"));
        setIDataCollection(invoice, useList, collCount);
        printJson(invoice);
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    byte[] prepareCanceledSample1(String details, String dataVal) throws IOException {
        Invoice invoice = createInvoice(1,"i_details_c", dataVal,  iCanceledStatus(details));
        printJson(invoice);
        return new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance());
    }

    private void printJson(TBase object) throws IOException {
        JsonNode node = new TBaseProcessor().process(object, new JsonHandler(true));

        System.out.printf("%nData: %n %s %n =========================================================%n", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(node));
    }

    private void setIDataCollection(Invoice invoice, boolean useList, int elemCount) {
        Collection<IData> collection = useList ? new ArrayList<>() : new HashSet<>();
        IntStream.range(0, elemCount).forEach( i -> collection.add(new IData("" + i)));
        invoice.setDataColl(useList ? IDataCollection.data_list((List<IData>) collection) : IDataCollection.data_set((Set<IData>) collection));
    }


    private static Invoice createInvoice(int invId, String details, String dataVal, InvoiceStatus invoiceStatus) {
        Invoice invoice = new Invoice(invId, invoiceStatus,
                new IData(dataVal) {{setDataOptVal("opt_data");}},
                new ILvlData() {{
                    setLvl2Data1(new ILvl2Data("lvl21val1", "lvl21val2"));
                    setLvl2Data2(new ILvl2Data("lvl22val1", "lvl22val2"));
        }}
        );
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
