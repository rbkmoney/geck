package com.rbkmoney.geck.serializer.kit.object;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.geck.serializer.Geck;
import com.rbkmoney.geck.serializer.GeckUtil;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.BinaryTest;
import com.rbkmoney.geck.serializer.test.TestObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.rbkmoney.geck.serializer.GeckUtil.getTestObject;
import static org.junit.Assert.assertEquals;

/**
 * Created by vpankrashkin on 09.02.17.
 */
@Ignore
public class ObjectHandlerTest {

    @Test
    public void test() {
        Object inputJSON = JsonUtils.jsonToObject(this.getClass().getResourceAsStream("/input1.json" ));
        System.out.println(inputJSON.toString());

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream( "/spec1.json" ));
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        //((Map)((Map)((Map) inputJSON).get("rating")).get("primary")).put("values", new HashSet(){{add(2); add(3);}});
        System.out.println( JsonUtils.toJsonString( inputJSON ) );

        Object transformedOutput = chainr.transform( inputJSON );
        System.out.println( JsonUtils.toJsonString( transformedOutput ) );
    }

    @Test
    public void testHandler() throws IOException {
        Geck geck = new Geck();
        TestObject testObject = GeckUtil.getTestObject();
        Object result = geck.write(testObject, new ObjectHandler());
        TestObject restoredTestObject = new ObjectProcessor().process(result, new TBaseHandler<>(TestObject.class));
        assertEquals(testObject, restoredTestObject);
        System.out.println(result);
    }

    @Test
    public void binaryTest() throws IOException {
        BinaryTest binaryTest = new MockTBaseProcessor().process(new BinaryTest(), new TBaseHandler<>(BinaryTest.class));
        Object src = new TBaseProcessor().process(binaryTest, new ObjectHandler());
        Object dst =
                new TBaseProcessor().process(new ObjectProcessor().process(src, new TBaseHandler<>(BinaryTest.class)), new ObjectHandler());
        assertEquals(src, dst);
    }

    @Test
    public void objectTest() throws IOException {
        TestObject testObject1 = getTestObject();
        Object src = new TBaseProcessor().process(testObject1, new ObjectHandler());
        System.out.println(JsonUtils.toJsonString(src));
        Object dst =
                new TBaseProcessor().process(new ObjectProcessor().process(src, new TBaseHandler<>(TestObject.class)), new ObjectHandler());
        System.out.println(JsonUtils.toJsonString(dst));
        assertEquals(src, dst);

    }


}
