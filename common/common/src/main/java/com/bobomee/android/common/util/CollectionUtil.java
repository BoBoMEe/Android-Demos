/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:19
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

import java.util.Collections;
import java.util.List;

/**
 * @author：BoBoMEe Created at 2016/1/14.
 */
public class CollectionUtil {

    public static String listToString(List<String> stringList) {
        if (stringList == null || stringList.size() == 0) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 比较两个list的元素是否相同 **
     */
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a== null || b == null || a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);

        for (int i = 0; i < a.size(); i++)
            if (!a.get(i).equals(b.get(i)))
                return false;
        return true;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    //全部为空
    public static boolean isAllEmpty(List... lists) {
        for (List list : lists) {
            if (!isEmpty(list)) {
                return false;
            }
        }

        return true;
    }

    //有一个为空
    public static boolean isOneEmpty(List... lists) {
        for (List list : lists) {
            if (isEmpty(list)) {
                return true;
            }
        }

        return false;
    }

}
