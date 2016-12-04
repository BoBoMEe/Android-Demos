package com.bobomee.android.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密工具
 */
public class CryptoUtil {

    private CryptoUtil() {
    }

    /**
     * byte数组转为16进制字符串
     */
    public static String bytesToHexString(byte[] input) {
        StringBuffer sb = new StringBuffer(input.length);
        String sTemp;
        for (byte anInput : input) {
            sTemp = Integer.toHexString(0xFF & anInput);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 16进制字符串转为byte数组
     */
    public static byte[] hexStringToBytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (hexToByte(achar[pos]) << 4 | hexToByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte hexToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    //---------------------------------------------------------------------------
    public static class AES {
        public static final String apikey = "BaweTw.lc!)61K{9^5";

        /**
         * 使用AES加密，使用utf8编码
         *
         * @throws Exception
         */
        public static String encrypt(String key, String clearText) throws Exception {
            byte[] rawKey = getRawKey(key);
            byte[] result = encrypt(rawKey, clearText.getBytes("UTF-8"));
            return bytesToHexString(result);
        }

        /**
         * 使用AES加密
         *
         * @throws Exception
         */
        public static byte[] encrypt(byte[] key, byte[] clearText) throws Exception {
            SecretKeySpec skeySpec = new SecretKeySpec(key, 0, Math.min(key.length, Cipher.getMaxAllowedKeyLength("AES") / 8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(key, 0, cipher.getBlockSize());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParam);
            return cipher.doFinal(clearText);
        }

        /**
         * 使用AES解密
         *
         * @throws Exception
         */
        public static String decrypt(String key, String encryptedText) throws Exception {
            byte[] rawKey = getRawKey(key);
            byte[] enc = hexStringToBytes(encryptedText);
            byte[] result = decrypt(rawKey, enc);
            return new String(result, "UTF-8");
        }

        /**
         * 使用AES解密
         *
         * @throws Exception
         */
        public static byte[] decrypt(byte[] key, byte[] encryptedText) throws Exception {
            SecretKeySpec skeySpec = new SecretKeySpec(key, 0, Math.min(key.length, Cipher.getMaxAllowedKeyLength("AES") / 8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(key, 0, cipher.getBlockSize());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParam);
            return cipher.doFinal(encryptedText);
        }

        private static byte[] getRawKey(String key) throws Exception {
            return MessageDigest.getInstance("MD5").digest(key.getBytes("UTF-8"));
        }
    }

    public static class MD5 {
        /**
         * MD5加密，使用uft8编码
         */
        public static byte[] toMd5(String input) {
            try {
                return toMd5(input, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return new byte[0];
            }
        }

        /**
         * MD5加密
         *
         * @throws UnsupportedEncodingException
         */
        public static byte[] toMd5(String input, String charsetName) throws UnsupportedEncodingException {
            byte[] data = input.getBytes(charsetName);
            return toMd5(data);
        }

        /**
         * MD5加密
         */
        public static byte[] toMd5(byte[] data) {
            byte[] messageDigest;
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance("MD5");
                digest.update(data);
                messageDigest = digest.digest();
                return messageDigest;
            } catch (NoSuchAlgorithmException e) {
                return new byte[0];
            }
        }

        /**
         * java.security.MessageDigest类中有MD5 SHA等加密算法的实现
         */
        public static String toMD5(String s) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return "";
            }
            md.update(s.getBytes()); //MD5加密算法只是对字符数组而不是字符串进行加密计算，得到要加密的对象
            byte[] bs = md.digest();   //进行加密运算并返回字符数组
            StringBuffer sb = new StringBuffer();
            for (byte b : bs) {    //字节数组转换成十六进制字符串，形成最终的密文
                int v = b & 0xff;
                if (v < 16) {
                    sb.append(0);
                }
                sb.append(Integer.toHexString(v));
            }
            return sb.toString();
        }

        /**
         * MD5加密 32位
         */
        public static String toMd532(String sourceStr) {
            String result = "";
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(sourceStr.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (byte aB : b) {
                    i = aB;
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                result = buf.toString().toLowerCase();
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e);
            }
            return result;
        }
    }

    public static class Base64 {

        /**
         * 转为Base64字符串
         */
        public static String toBase64(byte[] input) {
            return android.util.Base64.encodeToString(input, android.util.Base64.DEFAULT);
        }

        /**
         * 转为Base64字符串
         */
        public static String toBase64(byte[] input, int flags) {
            return android.util.Base64.encodeToString(input, flags);
        }

        /**
         * 将Base64数据解码
         */
        public static byte[] fromBase64(byte[] input) {
            return android.util.Base64.decode(input, android.util.Base64.DEFAULT);
        }

        /**
         * 将Base64数据解码
         */
        public static byte[] fromBase64(byte[] input, int flags) {
            return android.util.Base64.decode(input, flags);
        }

        /**
         * 将Base64字符串解码
         */
        public static byte[] fromBase64String(String input) {
            return android.util.Base64.decode(input, android.util.Base64.DEFAULT);
        }

        /**
         * 将Base64字符串解码
         */
        public static byte[] fromBase64String(String input, int flags) {
            return android.util.Base64.decode(input, flags);
        }
    }
}
