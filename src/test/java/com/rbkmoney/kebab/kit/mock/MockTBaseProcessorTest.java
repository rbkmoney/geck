package com.rbkmoney.kebab.kit.mock;

import com.rbkmoney.kebab.kit.tbase.TBaseHandler;
import com.rbkmoney.kebab.kit.tbase.ThriftType;
import com.rbkmoney.kebab.test.TestObject;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.FieldMetaData;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class MockTBaseProcessorTest {

    @Test
    public void requiredFieldsOnlyTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.REQUIRED_ONLY).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(checkFields(testObject, MockMode.REQUIRED_ONLY));
        assertFalse(checkFields(testObject, MockMode.ALL));
    }

    @Test
    public void allFieldsTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.ALL).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(checkFields(testObject, MockMode.ALL));
    }

    public boolean checkFields(TBase tBase, MockMode mode) {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tBase.getFieldMetaData();
        boolean check = true;
        if (tBase instanceof TUnion) {
            TUnion tUnion = (TUnion) tBase;
            check &= tUnion.isSet();
            FieldMetaData fieldMetaData = fieldMetaDataMap.get(tUnion.getSetField());
            if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                check &= checkFields((TBase) tUnion.getFieldValue(), mode);
            }
        } else {
            for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
                FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
                if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED
                        || mode == MockMode.ALL) {
                    check &= tBase.isSet(tFieldIdEnum);
                    if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                        check &= checkFields((TBase) tBase.getFieldValue(tFieldIdEnum), mode);
                    }
                }
            }

        }
        return check;
    }
}
