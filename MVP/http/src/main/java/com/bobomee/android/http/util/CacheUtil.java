/*
 *
 *    Copyright (C) 2016 BoBoMEe(wbwjx115@gmail.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bobomee.android.http.util;

import com.bobomee.android.common.util.StorageUtil;
import com.bobomee.android.common.util.UIUtil;

import java.io.File;

import okhttp3.Cache;

/**
 * Created by bobomee on 16/5/15.
 */
public class CacheUtil {

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    private static File getCacheDir() {
        //设置缓存路径
        final File baseDir = StorageUtil.getCacheDir(UIUtil.getContext());
        final File cacheDir = new File(baseDir, "HttpResponseCache");
        return cacheDir;
    }

    public static Cache getCache() {
        return new Cache(getCacheDir(), HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }
}
