/*
 * Android_Common. lastModified by bobomee at 2016.4.2 10:46
 *
 * Copyright (C) 2016 bobomee
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bobomee.android.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Locale;

public class MD5Util {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 获取String的Md5 值
	 */
	public static String getStringMD5(String instr) {

		byte[] buffer = instr.getBytes();
		return getMessageDigest(buffer).toUpperCase(Locale.ENGLISH);
	}

	public final static String getMessageDigest(byte[] buffer) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s= new String(str);
		} catch (Exception e) {
		}

		return s;
	}
	/**
	 * 获取文件的md5值，
	 *
	 * @param path 文件的路径
	 * @return null文件不存在
	 */
	public static String getFileMd5(String path) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = fis.read(buffer)) != -1) {
				digest.update(buffer, 0, len);
			}
			byte[] result = digest.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff;
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0" + hex);
				} else {
					sb.append(hex);
				}
			}
			fis.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
