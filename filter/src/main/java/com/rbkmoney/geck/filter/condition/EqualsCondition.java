package com.rbkmoney.geck.filter.condition;

import com.rbkmoney.geck.filter.Condition;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class EqualsCondition implements Condition {

    private final Function<Object, Boolean> comparator;

    public EqualsCondition(Object value) {
        this.comparator = selectBestFor(value);
    }

    @Override
    public boolean accept(Object t) {
        return comparator.apply(t).booleanValue();
    }

    public static Function<Object, Boolean> selectBestFor(Object value) {
        if (value instanceof Double) {
            return new DoubleComparator((Double) value);
        } else if (value instanceof Float) {
            return new DoubleComparator((Float) value);
        } else if (value instanceof Number) {
            return new LongComparator(((Number) value).longValue());
        } else if (value instanceof byte[]) {
            return new BytesComparator((byte[]) value);
        } else {
            return o -> value.equals(o);
        }
    }

    public static class BytesComparator implements Function<Object, Boolean> {
        private final byte[] refValue;

        public BytesComparator(byte[] refValue) {
            byte[] refValueCopy = new byte[refValue.length];
            System.arraycopy(refValue, 0, refValueCopy, 0, refValueCopy.length);
            this.refValue = refValueCopy;
        }

        @Override
        public Boolean apply(Object o) {
            if (o instanceof byte[]) {
                return Arrays.equals(refValue, (byte[]) o);
            }
            return false;
        }
    }

    public static class DoubleComparator implements Function<Object, Boolean> {
        private final Double refValue;

        public DoubleComparator(double refValue) {
            this.refValue = refValue;
        }

        @Override
        public Boolean apply(Object o) {
            if (o instanceof Double) {
                return refValue.equals(((Double) o).doubleValue());
            } else if (o instanceof Float) {
                return refValue.equals(((Float) o).doubleValue());
            } else if (o instanceof Number) {
                return refValue.equals(((Number) o).doubleValue());
            }
            return false;
        }
    }

    public static class LongComparator implements Function<Object, Boolean> {
        private final long refValue;
        private final BigDecimal refBDValue;

        public LongComparator(long refValue) {
            this.refValue = refValue;
            this.refBDValue = BigDecimal.valueOf(refValue);
        }

        @Override
        public Boolean apply(Object o) {
            if (o instanceof Double) {
                return new BigDecimal(((Double) o).doubleValue()).compareTo(refBDValue) == 0;
            } else if (o instanceof Float) {
                return new BigDecimal(((Float) o).floatValue()).compareTo(refBDValue) == 0;
            } else if (o instanceof Number) {
                return refValue == ((Number) o).longValue();
            }
            return false;
        }
    }
}
