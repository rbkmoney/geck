package com.rbkmoney.kebab.writer;

import com.rbkmoney.kebab.exception.BadFormatException;

import java.nio.charset.Charset;

/**
 * Created by vpankrashkin on 03.02.17.
 */
public final class StringUtil {
    public static final Charset CHARSET = Charset.forName("UTF-8");

    static byte[] compressAsciiString(String str) throws BadFormatException {
        byte[] bytes = new byte[(int)Math.ceil((str.length()*5+1) / 8.)];
        bytes[0] = (byte) 0x80;
        int bitIndex = 1;
        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if ((c & 0x80 ) != 0) {
                throw new BadFormatException("Only ASCII symbols're expected");
            }

            if (c < 0x5F || c > 0x7D) {
                return toAsciiBytes(str);
            }
            byte cb = (byte) ((c - 0x5F) + 1);
            //System.out.println("Char: "+c + ", bytes: "+ intToString(c, 8, 8) + ", cb_bytes: "+intToString(cb & 0xFF, 8, 8));
            //System.out.println("BeforeBits: "+ intToString(bytes[bitIndex / 8], 8, 8));

            bytes[bitIndex / 8] |= ((cb & 0b10000) >> 4) << (7 - (bitIndex++ % 8));
            //System.out.println("Bidx: "+bitIndex+", Bits: "+ intToString(bytes[(bitIndex-1) / 8], 8, 8) + ", |cbits: "+ intToString(orb, 8, 8));

            bytes[bitIndex / 8] |= ((cb & 0b01000) >> 3) << (7 - (bitIndex++ % 8));
            //System.out.println("Bidx: "+bitIndex+", Bits: "+ intToString(bytes[(bitIndex-1) / 8], 8, 8) + ", |cbits: "+ intToString(orb, 8, 8));

            bytes[bitIndex / 8] |= ((cb & 0b00100) >> 2) << (7 - (bitIndex++ % 8));
            //System.out.println("Bidx: "+bitIndex+", Bits: "+ intToString(bytes[(bitIndex-1) / 8], 8, 8) + ", |cbits: "+ intToString(orb, 8, 8));

            bytes[bitIndex / 8] |= ((cb & 0b00010) >> 1) << (7 - (bitIndex++ % 8));
            //System.out.println("Bidx: "+bitIndex+", Bits: "+ intToString(bytes[(bitIndex-1) / 8], 8, 8) + ", |cbits: "+ intToString(orb, 8, 8));

            bytes[bitIndex / 8] |= ((cb & 0b00001) >> 0) << (7 - (bitIndex++ % 8));
            //System.out.println("Bidx: "+bitIndex+", Bits: "+ intToString(bytes[(bitIndex-1) / 8], 8, 8) + ", |cbits: "+ intToString(orb, 8, 8));
        }
        return bytes;
    }

    static String decompressAsciiString(byte[] bytes) throws BadFormatException {
        if ((bytes[0] & 0x80) != 0x80) {
            return fromAsciiBytes(bytes);
        }

        char[] chars = new char[(bytes.length * 8 - 1) / 5];
        int maxBits = chars.length * 5 + 1;
        int cLength = 0;

        for (int bitIndex = 1; bitIndex < maxBits;) {
            char c = 0;
            c |= (bytes[bitIndex / 8] >> (7 - (bitIndex++ % 8)) & 1) << 4;
            c |= (bytes[bitIndex / 8] >> (7 - (bitIndex++ % 8)) & 1) << 3;
            c |= (bytes[bitIndex / 8] >> (7 - (bitIndex++ % 8)) & 1) << 2;
            c |= (bytes[bitIndex / 8] >> (7 - (bitIndex++ % 8)) & 1) << 1;
            c |= (bytes[bitIndex / 8] >> (7 - (bitIndex++ % 8)) & 1) << 0;
            if (c != 0) {
                cLength = (bitIndex-1) / 5 ;
                chars[cLength - 1] = (char) ((c + 0x5F) - 1);
            } else {
                cLength = (bitIndex-1) / 5 - 1;
            }
        }
        return new String(chars, 0, cLength);
    }

    static byte[] toAsciiBytes(final String str) {
        final byte[] bytes = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            bytes[i] = (byte) str.charAt(i);
        }
        return bytes;
    }

    static String fromAsciiBytes(byte[] bytes) {
        return new String(bytes, CHARSET);
    }

    static boolean isAsciiString(String str) {
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) > 0x7F) {
                return false;
            }
        }
        return true;
    }


    public static String intToString(int number, int groupSize, int maxBits) {
        StringBuilder result = new StringBuilder();

        for(int i = maxBits - 1; i >= 0 ; i--) {
            int mask = 1 << i;
            result.append((number & mask) != 0 ? "1" : "0");

            if (i % groupSize == 0 && i > 0)
                result.append(" ");
        }
        return result.toString();
    }
}
