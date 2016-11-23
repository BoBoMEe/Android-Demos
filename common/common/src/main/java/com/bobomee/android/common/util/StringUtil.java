/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:27
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

import android.text.TextUtils;

import java.util.Random;

/**
 * Created on 16/5/16.下午3:28.
 * @author bobomee.
 * wbwjx115@gmail.com
 */
public class StringUtil {

    public static boolean isEmpty(String s) {
        return s == null || TextUtils.isEmpty(s);
    }

    //全部为空
    public static boolean isAllEmpty(String... strings) {
        for (String s : strings) {

            if (!isEmpty(s)) {
                //如果有一个不为空则返回false.
                return false;
            }

        }

        return true;
    }

    //有一个为空
    public static boolean isOneEmpty(String... strings) {
        for (String s : strings) {
            if (isEmpty(s)) {
                return true;
            }
        }

        return false;
    }

    private static char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static void setChars(char[] charsX) {
        chars = charsX;
    }

    //获取随机字符串
    public static String getRandomString() {

        Random random = new Random();
        //获取随机长度
        int length = random.nextInt(chars.length) + 1;

        char[] data = new char[length];

        for (int i = 0; i < length; i++) {
            //获取随机字符
            int index = random.nextInt(chars.length);
            data[i] = chars[index];
        }
        return new String(data);
    }

    public static String combinePath(String path1, String path2) {
        String result = null;
        if (TextUtils.isEmpty(path1))
            result = path2;
        else if (TextUtils.isEmpty(path2))
            result = path1;
        else {
            if (!path1.endsWith("/"))
                result = path1 + "/";
            else
                result = path1;
            if (path2.startsWith("/"))
                result += path2.substring(1);
            else
                result += path2;
        }
        return TextUtils.isEmpty(result) ? "" : result;
    }

}
