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

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SPUtil {

    private SPUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "config";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return defaultObject;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            apply(editor);
        }
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            apply(editor);
        }
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    private static final Method sApplyMethod = findApplyMethod();

    /**
     * 反射查找apply的方法
     *
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Method findApplyMethod() {
        try {
            Class clz = SharedPreferences.Editor.class;
            return clz.getMethod("apply");
        } catch (NoSuchMethodException e) {
        }

        return null;
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    public static void apply(SharedPreferences.Editor editor) {
        try {
            if (sApplyMethod != null) {
                sApplyMethod.invoke(editor);
                return;
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        editor.commit();
    }

    public static void putMap(Context context, HashMap<String, Object> entrys) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();

        Iterator<Entry<String, Object>> iter = entrys.entrySet().iterator();

        while (iter.hasNext()) {
            Entry<String, Object> set = iter.next();
            String key = set.getKey();
            Object val = set.getValue();

            if (val instanceof String) {
                editor.putString(key, (String) val);
            } else if (val instanceof Integer) {
                editor.putInt(key, (Integer) val);
            } else if (val instanceof Long) {
                editor.putLong(key, (Long) val);
            } else if (val instanceof Boolean) {
                editor.putBoolean(key, (Boolean) val);
            } else if (val instanceof Float) {
                editor.putFloat(key, (Float) val);
            } else if (sp.contains(key)) {
                editor.remove(key);
            }
        }

        apply(editor);
    }

}
