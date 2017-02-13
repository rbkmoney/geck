package com.rbkmoney.kebab.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;

import java.util.Random;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class RandomUtil {

    public final static Random RANDOM = new Random();

    public static byte randomByte() {
        return (byte) randomNumber(Byte.SIZE);
    }

    public static short randomShort() {
        return (short) randomNumber(Short.SIZE);
    }

    public static int randomUnsignedNumber(int bitsSize, int maxValue) {
        return randomNumber(bitsSize) & maxValue;
    }

    public static int randomNumber(int bitSize) {
        int value = RANDOM.nextInt();
        for (int n = Integer.SIZE / bitSize; --n > 0; value >>= bitSize);
        return value;
    }

    public static int randomInt() {
        return RANDOM.nextInt();
    }

    public static int randomInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static long randomLong() {
        return RANDOM.nextLong();
    }

    public static double randomDouble() {
        return RANDOM.nextDouble();
    }

    public static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }

    public static byte[] randomByteArray(int maxSize) {
        int size = RANDOM.nextInt(maxSize);
        byte[] byteArray = new byte[size];
        RANDOM.nextBytes(byteArray);
        return byteArray;
    }

    public static String randomString(int maxLength) {
        int size = RANDOM.nextInt(maxLength);
        char[] value = new char[size];
        for (int i = 0; i < size; i++) {
            value[i] = (char) randomUnsignedNumber(Character.SIZE, Character.MAX_VALUE);
        }
        return new String(value);
    }

    public static TEnum randomTEnum(Class<? extends TEnum> enumClass) {
        if (enumClass.isEnum()) {
            TEnum[] enums = enumClass.getEnumConstants();
            int element = RANDOM.nextInt(enums.length);
            return enums[element];
        }
        return null;
    }

    public static TFieldIdEnum randomField(TBase tBase) {
        TFieldIdEnum[] fields = tBase.getFields();
        int element = RANDOM.nextInt(fields.length);
        return fields[element];
    }

}
