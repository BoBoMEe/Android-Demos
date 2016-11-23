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

import android.database.Cursor;
import java.io.Closeable;
import java.io.IOException;

/**
 * Author: wyouflf
 * Date: 13-8-26
 * Time: 下午6:02
 */
public class IOUtil {

    private IOUtil() {
    }

    public static void closeQuietly(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void closeQuietly(Cursor... cursors) {
        if (null == cursors || cursors.length <= 0) {
            return;
        }
        for (Closeable cr : cursors) {
            try {
                if (null == cr) {
                    continue;
                }
                cr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
