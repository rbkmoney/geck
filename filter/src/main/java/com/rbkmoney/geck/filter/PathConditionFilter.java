package com.rbkmoney.geck.filter;

import com.rbkmoney.geck.common.util.TBaseUtil;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.geck.filter.rule.PathConditionRule;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TUnion;

import java.util.Collection;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class PathConditionFilter implements Filter<TBase> {

    private final PathConditionRule[] rules;

    public PathConditionFilter(PathConditionRule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(TBase value) {
        for (PathConditionRule rule : rules) {
            Parser parser = rule.getParser();
            if (!match(value, parser, rule.getConditions())) {
                return false;
            }
        }

        return true;
    }

    public boolean match(Object value, Parser parser, Condition... conditions) {
        for (int item = 0; item < parser.size(); item++) {
            if (value instanceof TBase) {
                TBase tBase = TypeUtil.convertType(TBase.class, value);
                TFieldIdEnum tFieldIdEnum = TBaseUtil.getField(parser.getItem(item), tBase);
                if (tFieldIdEnum == null ||
                        ((tBase instanceof TUnion) && !tBase.isSet(tFieldIdEnum))) {
                    return false;
                }
                value = tBase.getFieldValue(tFieldIdEnum);
            } else if (value instanceof Collection) {
                Collection collection = TypeUtil.convertType(Collection.class, value);
                boolean result = false;
                for (Object collectionItem : collection) {
                    result |= match(collectionItem, parser.getSubParser(item), conditions);
                }
                return result;
            }
        }

        for (Condition condition : conditions) {
            if (!condition.accept(value)) {
                return false;
            }
        }
        return true;
    }

}
