package com.rbkmoney.kebab.kit.msgpack;

import com.rbkmoney.kebab.Kebab;
import com.rbkmoney.kebab.KebabUtil;
import com.rbkmoney.kebab.test.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by vpankrashkin on 08.02.17.
 */
public class MsgPackTest {
    Kebab kebab = new Kebab();
    @Test
    public void test() throws IOException {
        TestObject testObject = KebabUtil.getTestObject();
        byte[] serializedData = kebab.toMsgPack(testObject, true);
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }
}
