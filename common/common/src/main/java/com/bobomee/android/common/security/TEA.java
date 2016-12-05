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
 * Implementation of the Tiny Encryption Algorithm (TEA).
 * The Tiny Encryption Algorithm is one of the fastest and most efficient
 * cryptographic algorithms in existence. It was developed by David Wheeler and
 * Roger Needham at the Computer Laboratory of Cambridge University.
 * <p/>
 * See http://www.cl.cam.ac.uk/ftp/users/djw3/tea.ps
 * <p/>
 * This software was written to provide simple encryption for J2ME.
 * The homepage for this software is http://winterwell.com/software/TEA.php
 * <p/>
 * (c) 2008 Joe Halliwell <joe.halliwell@gmail.com>
 * <p/>
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class TEA {
    private final static int SUGAR = 0x9E3779B9;
    private final static int CUPS = 32;
    private final static int UNSUGAR = 0xC6EF3720;

    private int[] S = new int[4];

    /**
     * Initialize the cipher for encryption or decryption.
     *
     * @param key a 16 byte (128-bit) key
     */
    public TEA(byte[] key) {
        if (key == null)
            throw new RuntimeException("Invalid key: Key was null");
        if (key.length < 16)
            throw new RuntimeException("Invalid key: Length was less than 16 bytes");
        for (int off = 0, i = 0; i < 4; i++) {
            S[i] = ((key[off++] & 0xff)) |
                    ((key[off++] & 0xff) << 8) |
                    ((key[off++] & 0xff) << 16) |
                    ((key[off++] & 0xff) << 24);
        }
    }

    public TEA(int[] key) {
        S = key;
    }

    /**
     * Encrypt an array of bytes.
     *
     * @param clear the cleartext to digest
     * @return the encrypted text
     */
    public byte[] encrypt(byte[] clear) {
        int paddedSize = ((clear.length / 8) + (((clear.length % 8) == 0) ? 0 : 1)) * 2;
        int[] buffer = new int[paddedSize + 1];
        buffer[0] = clear.length;
        pack(clear, buffer, 1);
        brew(buffer);
        return unpack(buffer, 0, buffer.length * 4);
    }

    /**
     * Decrypt an array of bytes.
     *
     * @param crypt the cipher text to decrypt
     * @return the decrypted text
     */
    public byte[] decrypt(byte[] crypt) {
        assert crypt.length % 4 == 0;
        assert (crypt.length / 4) % 2 == 1;
        int[] buffer = new int[crypt.length / 4];
        pack(crypt, buffer, 0);
        unbrew(buffer);
        return unpack(buffer, 1, buffer[0]);
    }

    void brew(int[] buf) {
        assert buf.length % 2 == 1;
        int i, v0, v1, sum, n;
        i = 1;
        while (i < buf.length) {
            n = CUPS;
            v0 = buf[i];
            v1 = buf[i + 1];
            sum = 0;
            while (n-- > 0) {
                sum += SUGAR;
                v0 += ((v1 << 4) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
                v1 += ((v0 << 4) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
            }
            buf[i] = v0;
            buf[i + 1] = v1;
            i += 2;
        }
    }

    void unbrew(int[] buf) {
        assert buf.length % 2 == 1;
        int i, v0, v1, sum, n;
        i = 1;
        while (i < buf.length) {
            n = CUPS;
            v0 = buf[i];
            v1 = buf[i + 1];
            sum = UNSUGAR;
            while (n-- > 0) {
                v1 -= ((v0 << 4) + S[2] ^ v0) + (sum ^ (v0 >>> 5)) + S[3];
                v0 -= ((v1 << 4) + S[0] ^ v1) + (sum ^ (v1 >>> 5)) + S[1];
                sum -= SUGAR;
            }
            buf[i] = v0;
            buf[i + 1] = v1;
            i += 2;
        }
    }

    void pack(byte[] src, int[] dest, int destOffset) {
        assert destOffset + (src.length / 4) <= dest.length;
        int i = 0, shift = 24;
        int j = destOffset;
        dest[j] = 0;
        while (i < src.length) {
            dest[j] |= ((src[i] & 0xff) << shift);
            if (shift == 0) {
                shift = 24;
                j++;
                if (j < dest.length) dest[j] = 0;
            } else {
                shift -= 8;
            }
            i++;
        }
    }

    byte[] unpack(int[] src, int srcOffset, int destLength) {
        assert destLength <= (src.length - srcOffset) * 4;
        byte[] dest = new byte[destLength];
        int i = srcOffset;
        int count = 0;
        for (int j = 0; j < destLength; j++) {
            dest[j] = (byte) ((src[i] >> (24 - (8 * count))) & 0xff);
            count++;
            if (count == 4) {
                count = 0;
                i++;
            }
        }
        return dest;
    }
}
