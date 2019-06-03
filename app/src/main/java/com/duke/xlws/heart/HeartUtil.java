package com.duke.xlws.heart;

import android.util.Log;

/**
 * 创建：duke
 * 注释：
 * 版本大小：12
 * 版本名称：v2.0.1
 * 日期：2018/icon_head/30.
 */


public class HeartUtil {


    public static short[] HexStringToShort(String src) {
        int len = src.length();
        short[] srcShort1 = new short[len];
        short[] srcShort2 = new short[len / 2];
        for (int i = 0; i < len; i++) {
            srcShort1[i] = charToShort(src.charAt(i));
        }
        for (int i = 0, j = 0; i < len / 2; i++, j++) {
            srcShort2[j] = (short) (srcShort1[i * 2] * 16 + srcShort1[i * 2 + 1] * 1);
        }
        return srcShort2;
    }


    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static short charToShort(char c) {
        return (short) "0123456789ABCDEF".indexOf(c);
    }
}
