/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:24
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 16/5/16.下午3:30.
 * @author bobomee.
 * wbwjx115@gmail.com
 */
public class EncodeUtil {

    /**
     * 只encode中文
     */
    public static String encode(String sourceString) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            String s1 = matcher.group();
            sourceString = sourceString.replace(s1, urlEncode(s1));
        }

        return sourceString;
    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(e.toString());
            return URLEncoder.encode(s);
        }
    }

    public static String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return URLDecoder.decode(s);
        }
    }

}
