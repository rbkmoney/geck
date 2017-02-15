package com.rbkmoney.kebab.kit.json;

import com.rbkmoney.kebab.kit.tbase.TBaseProcessor;
import com.rbkmoney.kebab.test.TestObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.kebab.KebabUtil.getTestObject;

/**
 * Created by tolkonepiu on 15/02/2017.
 */
public class JsonHandlerTest {

    @Test
    public void jsonTest() throws JSONException, IOException {
        TestObject testObject = getTestObject();
        String json = new TBaseProcessor().process(testObject, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }

}
