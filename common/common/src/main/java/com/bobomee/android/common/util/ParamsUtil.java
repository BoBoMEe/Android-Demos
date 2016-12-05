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

package com.bobomee.android.common.util;

import android.text.TextUtils;

import java.util.Map;

/**
 * @author：BoBoMEe
 * Created at 2016/1/18.
 */
public class ParamsUtil {

    public static String getUrl(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (params == null || params.size() == 0) {
            return url;
        }


        StringBuilder sb = new StringBuilder(url);//封装path

        if (!url.contains("?")) {
            sb.append("?");
        }

        int i = 0;
        int size = params.size();

        for (String key : params.keySet()) {
            String value = params.get(key);
            ++i;
            if (TextUtils.isEmpty(value)) {
                continue;
            }

            sb.append(key);
            sb.append("=");
            sb.append(value);

            if (i == size) {
                break;
            }

            sb.append("&");
        }

        return sb.toString();
    }
}
