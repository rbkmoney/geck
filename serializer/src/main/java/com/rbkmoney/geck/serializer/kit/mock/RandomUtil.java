package com.rbkmoney.geck.serializer.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class RandomUtil {
    private static final Pattern pattern = Pattern.compile("[\\w\\s\"\']");

    public static byte randomByte() {
        return (byte) randomNumber(Byte.SIZE);
    }

    public static short randomShort() {
        return (short) randomNumber(Short.SIZE);
    }

    public static int randomUnsignedNumber(int bitsSize, int maxValue) {
        return randomUnsignedNumber(bitsSize, maxValue, new Random());
    }

    public static int randomUnsignedNumber(int bitsSize, int maxValue, Random random) {
        return randomNumber(bitsSize, random) & maxValue;
    }

    public static int randomNumber(int bitSize) {
        return randomNumber(bitSize, new Random());
    }

    public static int randomNumber(int bitSize, Random random) {
        return 1;
    }

    public static int randomInt() {
        return 2;
    }

    public static int randomInt(int bound) {
        return 3;
    }

    public static long randomLong() {
        return 4;
    }

    public static double randomDouble() {
        return 5;
    }

    public static boolean randomBoolean() {
        return true;
    }

    public static byte[] randomByteArray(int maxSize) {
        int size = 6;
        byte[] byteArray = new byte[size];
        return byteArray;
    }

    public static String randomString(int maxLength) {
        return "kek";
    }

    public static TEnum randomTEnum(Class<? extends TEnum> enumClass) {
        if (enumClass.isEnum()) {
            TEnum[] enums = enumClass.getEnumConstants();
            int element = enums.length;
            return enums[element-1];
        }
        return null;
    }

    public static TFieldIdEnum randomField(TBase tBase) {
        TFieldIdEnum[] fields = tBase.getFields();
        int element = fields.length;
        return fields[element-1];
    }

}
