package com.rbkmoney.geck.filter;

import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.condition.IsNullCondition;
import com.rbkmoney.geck.filter.rule.PathConditionRule;
import com.rbkmoney.geck.filter.test.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class PathConditionFilterTest {

    List<TestObject> testObjectList = new ArrayList<>();

    @Before
    public void setup() {
        TestObject testObject = new TestObject();
        testObject.setId(123);

        Ids ids = new Ids();
        ids.setMicroId((byte) 23);
        ids.setMiniId((short) 123);
        ids.setId(123);

        testObject.setOtherIds(ids);

        testObject.setStatus(Status.ok_status(new Ok()));
        testObject.setType(Type.BLACK);
        testObject.setNumbers(Arrays.asList(new Long[]{23L, 31L, 41L}));
        testObjectList.add(testObject);

        testObject = new TestObject();
        testObject.setId(123);

        ids = new Ids();
        ids.setMicroId((byte) 25);
        ids.setMiniId((short) 3232);
        ids.setId(123);

        testObject.setOtherIds(ids);

        Fail fail = new Fail();
        fail.setCode((byte) 14);
        testObject.setStatus(Status.fail_status(fail));
        testObject.setType(Type.GREEN);
        testObjectList.add(testObject);

        testObject = new TestObject();
        testObject.setId(1241343141323242343L);

        ids = new Ids();
        ids.setMicroId((byte) 26);
        ids.setMiniId((short) 3232);
        ids.setId(242342341);
        testObject.setOtherIds(ids);

        Unknown unknown = new Unknown();
        List<Type> listTypes = Arrays.asList(new Type[]{Type.BLACK, Type.GREEN});
        unknown.setUnknown(UnknownType.resultTypes(listTypes));
        unknown.setDescription("wtf!?!");
        testObject.setStatus(Status.unknown_status(unknown));
        testObject.setType(Type.BLACK);
        testObject.setNumbers(Arrays.asList(new Long[]{43L, 56L, 31L, 51L}));
        testObjectList.add(testObject);
    }

    @Test
    public void filterHappyCase() {
        Filter filter;

        PathConditionRule rule1 = new PathConditionRule("other_ids.micro_id");
        PathConditionRule rule2 = new PathConditionRule("other_ids.mini_id");

        Unknown unknown = new Unknown();
        List<Type> listTypes = Arrays.asList(new Type[]{Type.BLACK, Type.GREEN});
        unknown.setUnknown(UnknownType.resultTypes(listTypes));
        unknown.setDescription("wtf!?!");
        PathConditionRule rule3 = new PathConditionRule("status", new EqualsCondition(Status.ok_status(new Ok())).or(new EqualsCondition(Status.unknown_status(unknown))));
        PathConditionRule rule4 = new PathConditionRule("type", new EqualsCondition(Type.BLACK));
        PathConditionRule rule5 = new PathConditionRule("type", new EqualsCondition(Type.RED).not());
//        PathConditionRule rule6 = new PathConditionRule("numbers", new EqualsCondition(31L));
        filter = new PathConditionFilter(rule1, rule2, rule3, rule4, rule5);//, rule6);
        assertEquals(filter.match(testObjectList.get(0)), true);
        assertEquals(filter.match(testObjectList.get(1)), false);
        assertEquals(filter.match(testObjectList.get(2)), true);

        PathConditionRule isNull = new PathConditionRule("status.fail_status.description", new IsNullCondition());
        filter = new PathConditionFilter(isNull);
        assertEquals(filter.match(testObjectList.get(1)), true);

        PathConditionRule isNotNull = new PathConditionRule("status.unknown_status.description", new IsNullCondition().not());
        filter = new PathConditionFilter(isNotNull);
        assertEquals(filter.match(testObjectList.get(2)), true);

    }

    @Test
    public void filterUnhappyCase() {
        PathConditionRule rule1 = new PathConditionRule("kek.kek.sdfdsf.fsdf.vv.ff.ee.dd");
        PathConditionRule rule2 = new PathConditionRule("..213123.213");
        PathConditionRule rule3 = new PathConditionRule("");
        Filter filter = new PathConditionFilter(rule1, rule2, rule3);
        assertEquals(filter.match(testObjectList.get(0)), false);
    }

}
