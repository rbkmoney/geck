package com.rbkmoney.kebab.kit.object;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.kebab.Kebab;
import com.rbkmoney.kebab.KebabUtil;
import com.rbkmoney.kebab.test.TestObject;
import org.junit.Test;

import java.util.List;

/**
 * Created by vpankrashkin on 09.02.17.
 */
public class ObjectHandlerTest {

    @Test
    public void test() {
        Object inputJSON = JsonUtils.jsonToObject(this.getClass().getResourceAsStream("../../../../../input1.json" ));
        System.out.println(inputJSON.toString());

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream( "../../../../../spec1.json" ));
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );
        //((Map)((Map)((Map) inputJSON).get("rating")).get("primary")).put("values", new byte[]{1, 2, 3});

        Object transformedOutput = chainr.transform( inputJSON );
        System.out.println( JsonUtils.toJsonString( transformedOutput ) );
    }

    @Test
    public void testHandler() {
        Kebab kebab = new Kebab();
        TestObject testObject = KebabUtil.getTestObject();
        Object result = kebab.write(testObject, new ObjectHandler());
        new ObjectProcessor().process(result, )
        System.out.println(result);
    }


}
