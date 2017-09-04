package com.rbkmoney.geck.serializer.kit.msgpack;

import com.rbkmoney.geck.serializer.kit.msgpack.supply.NoFilterMsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.Status;
import com.rbkmoney.geck.serializer.test.TestObject;
import com.rbkmoney.geck.serializer.test.Unknown;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rbkmoney.geck.serializer.GeckTestUtil.getTestObject;

public class TestFilterMsgPackProcessor {
    @Test
    @Ignore
    public void testPerformance() throws IOException {
        MsgPackProcessor<byte[]> fp = MsgPackProcessor.newBinaryInstance();
        MsgPackProcessor<byte[]> nfp = NoFilterMsgPackProcessor.newBinaryInstance();
        TBaseProcessor tbp = new TBaseProcessor();
        MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance();
        for (int i = 0; i < 10000; i++) {
            TestObject testObject1 = getTestObject(100, j -> Status.unknown(new Unknown("unknown")));
            tbp.process(testObject1, handler);
            tbp.process(testObject1, handler);
        }
        List<byte[]> list = IntStream.range(0, 10000).mapToObj(i -> {
            try {
                return tbp.process(getTestObject(100, j -> Status.unknown(new Unknown("unknown"))), handler);
            } catch (IOException e) {
                return null;
            }}).collect(Collectors.toList());

        long start = System.currentTimeMillis();
        iterate(nfp, handler, list, 1);
        long time = System.currentTimeMillis() - start;
        System.out.println("NoFilter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(fp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("Filter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(nfp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("NoFilter Time(ms):"+time);

        start = System.currentTimeMillis();
        iterate(fp, handler, list, 1);
        time = System.currentTimeMillis() - start;
        System.out.println("Filter Time(ms):"+time);


    }

    private void iterate(MsgPackProcessor processor, MsgPackHandler handler, List<byte[]> elements, int iterCount) throws IOException {
        for (int i = 0; i < iterCount; i++)
            for (byte[] bytes : elements)
                processor.process(bytes, handler);
    }
}
