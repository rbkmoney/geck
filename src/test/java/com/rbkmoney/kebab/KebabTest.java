package com.rbkmoney.kebab;

import com.rbkmoney.kebab.test.Fail;
import com.rbkmoney.kebab.test.Ids;
import com.rbkmoney.kebab.test.Status;
import com.rbkmoney.kebab.test.TestObject;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class KebabTest {
    Kebab kebab = new Kebab();

    @Test
    public void kebabTesting() {
        TestObject testObject = getTestObject();


        kebab.toJson(testObject);
    }

    @Test
    public void msgPackTest() throws Exception {
        TestObject testObject = getTestObject();
        byte[] msgPack = kebab.toMsgPack(testObject, true);
        byte[] tCompact = new TSerializer(new TCompactProtocol.Factory()).serialize(testObject);
        byte[] tBinary = new TSerializer(new TBinaryProtocol.Factory()).serialize(testObject);
        System.out.println("MsgPack:"+msgPack.length);
        System.out.println("Compact:"+tCompact.length);
        System.out.println("Binary:"+tBinary.length);

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream(msgPack.length);
        GZIPOutputStream gzip1 = new GZIPOutputStream(bos1);
        gzip1.write(msgPack);
        gzip1.close();
        System.out.println("GZip:MsgPack:" + bos1.toByteArray().length);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream(tBinary.length);
        GZIPOutputStream gzip2 = new GZIPOutputStream(bos2);
        gzip2.write(tBinary);
        gzip2.close();
        System.out.println("GZip:Binary:" + bos2.toByteArray().length);


    }

    @Test
    public void msgPackTestList() throws Exception{
        TestObject testObject = getTestObject();
        List<Status> lists = Collections.nCopies(1000, Status.unknown(new Unknown("SomeData")));
        testObject.setStatuses(lists);

        byte[] msgPack = kebab.toMsgPack(testObject, true);
        byte[] tCompact = new TSerializer(new TCompactProtocol.Factory()).serialize(testObject);
        byte[] tBinary = new TSerializer(new TBinaryProtocol.Factory()).serialize(testObject);
        System.out.println("MsgPack:"+msgPack.length);
        System.out.println("Compact:"+tCompact.length);
        System.out.println("Binary:"+tBinary.length);

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream(msgPack.length);
        GZIPOutputStream gzip1 = new GZIPOutputStream(bos1);
        gzip1.write(msgPack);
        gzip1.close();
        System.out.println("GZip:MsgPack:" + bos1.toByteArray().length);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream(tBinary.length);
        GZIPOutputStream gzip2 = new GZIPOutputStream(bos2);
        gzip2.write(tBinary);
        gzip2.close();
        System.out.println("GZip:Binary:" + bos2.toByteArray().length);
    }

    @Test
    public void testPerformance() {
        boolean useDict = true;
        TestObject testObject = getTestObject(100, () -> Status.unknown(new Unknown("SomeData")));
        //warmup
        WriterStub writerStub = new WriterStub();
        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        IntConsumer stubConsumer = i -> kebab.write(testObject, writerStub);
        IntConsumer msgPackConsumer = i -> kebab.toMsgPack(testObject, useDict);
        IntConsumer tWriter  = i -> {
            try {
                serializer.serialize(testObject);
            } catch (TException e) {
                e.printStackTrace();
            }
        };
        IntConsumer consumer = tWriter;
        IntStream.range(0, 100000).forEach(consumer);

        long startTime = System.currentTimeMillis();
        IntStream.range(0, 100000).forEach(consumer);
        System.out.println("Time:"+(System.currentTimeMillis() - startTime));
        System.out.println("неопределенность".getBytes().length);
        System.out.println(Base64.getEncoder().withoutPadding().encode("неопределенность".getBytes()).length);
    }

    private TestObject getTestObject(int statusCount, Supplier<Status> statusGen) {
        TestObject testObject = getTestObject();
        List<Status> lists = Collections.nCopies(statusCount, statusGen.get());
        testObject.setStatuses(lists);
        return testObject;
    }

    private TestObject getTestObject() {
        TestObject testObject = new TestObject();
        testObject.setDescription("kek");
        testObject.setValue(2.32);

        Ids ids = new Ids();
        ids.setBigId(2141214124L);
        ids.setId(12312);
        ids.setMiniId((short) 2334);
        ids.setMicroId((byte) 127);

        testObject.setIds(ids);

        testObject.setData(new byte[]{4, 2});

        testObject.setNumbers(Arrays.asList(1, 2, 3, 4, 5));

        Set<String> suk = new HashSet<>(Arrays.asList("kek1", "kek2"));

        testObject.setFuck(Arrays.asList(suk, suk, suk));

        Fail fail = new Fail();

        fail.setReasons(new HashSet<>(Arrays.asList("kek1", "kek2")));

        Map<String, Integer> map = new HashMap<>();
        map.put("kek1", 455);
        map.put("kek2", 564);
        map.put("kek3", 565);
        //map.put(null, 666);
        //map.put("null", null);
        testObject.setMaps(map);

        testObject.setStatus(Status.fail(new Fail(fail)));
        return testObject;
    }

}
