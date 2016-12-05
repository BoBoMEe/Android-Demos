/*
 * Copyright (C) 2016.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.common.security;

/**
 * CRC64
 * <p/>
 * Created by zhaiyifan on 2015/8/3.
 */
public class CRC64 {

    private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
    private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

    private static long[] sCrcTable = new long[256];

    static {
        // http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
        long part;
        for (int i = 0; i < 256; i++) {
            part = i;
            for (int j = 0; j < 8; j++) {
                long x = ((int) part & 1) != 0 ? POLY64REV : 0;
                part = (part >> 1) ^ x;
            }
            sCrcTable[i] = part;
        }
    }

    private CRC64() {
        // static usage.
    }

    /**
     * A function that returns a 64-bit crc for byte array
     *
     * @param buffer input byte array.
     * @return a 64-bit crc value
     */
    public static long getValue(byte[] buffer) {
        long crc = INITIALCRC;
        for (byte aBuffer : buffer) {
            crc = sCrcTable[(((int) crc) ^ aBuffer) & 0xff] ^ (crc >> 8);
        }
        return crc;
    }

    /**
     * A function that returns a 64-bit crc for string.
     *
     * @param in input string.
     * @return a 64-bit crc value.
     */
    public static long getValue(String in) {
        if (in == null || in.length() == 0) {
            return 0;
        }
        return getValue(getBytes(in));
    }

    private static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        char[] charArray = in.toCharArray();
        for (char ch : charArray) {
            result[output++] = (byte) (ch & 0xFF);
            result[output++] = (byte) (ch >> 8);
        }
        return result;
    }
}
