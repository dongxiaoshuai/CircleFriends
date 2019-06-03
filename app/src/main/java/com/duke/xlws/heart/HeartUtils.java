package com.duke.xlws.heart;

/**
 * 创建：duke
 * 注释：心率数据转化工具类
 * 版本大小：17
 * 版本名称：v2.1.4
 * 日期：2018/6/20.
 */


public class HeartUtils {
    public static int avg;

    /***
     * 向外提供的方法 最终将16进制字符转化为10进制short 数组
     *
     * @param data
     * @return
     */
    public static short[] parse(String data) {
        int max = 0;
        int min = 0;
        short[] shorts = HeartUtils.HexStringToShort(data);
        short[] shorts1 = new short[shorts.length / 2];
        for (int t = 0, len = shorts1.length; t < len; t++) {
            shorts1[t] = (short) ((shorts[t * 2] & 0xff) | (shorts[t * 2 + 1] & 0xff) << 8);

            if (shorts1[t] > max) {
                max = shorts1[t];
            }

            if (shorts1[t] < min) {
                min = shorts1[t];
            }
        }

        avg = (max + min) / 2;
        return shorts1;
    }

    /***********************内部方法********************************************/

    /**
     * 将16进制字符 转化为10进制short数组
     *
     * @param src
     * @return
     */
    private static short[] HexStringToShort(String src) {
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
