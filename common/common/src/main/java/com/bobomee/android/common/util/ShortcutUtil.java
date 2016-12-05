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

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

/**
 * 快捷方式工具类
 * <p>
 * Created by zhaiyifan on 2015/8/5.
 */
public final class ShortcutUtil {

    private final static String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private final static String ACTION_UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    private ShortcutUtil() {
        // static usage.
    }

    public static boolean installShortcut(Context context, int flags) {
        ApplicationInfo info = context.getApplicationInfo();
        if (info != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            intent.addFlags(flags);
            return installShortcut(context, intent, info.labelRes, info.icon);
        }
        return false;
    }

    public static boolean installShortcut(Context context, Intent shortcutIntent) {
        ApplicationInfo info = context.getApplicationInfo();
        return info != null && installShortcut(context, shortcutIntent, info.labelRes, info.icon);
    }

    public static boolean installShortcut(Context context, Intent shortcutIntent, int titleRes, int iconRes) {
        if (hasShortcut(context, titleRes)) {
            return false;
        }
        final Intent intent = generateShortcutIntent(context, shortcutIntent, titleRes, iconRes);
        intent.setAction(ACTION_INSTALL_SHORTCUT);
        context.sendBroadcast(intent, null);
        return true;
    }

    public static void uninstallShortcut(Context context, int flags) {
        ApplicationInfo info = context.getApplicationInfo();
        if (info != null) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            intent.addFlags(flags);
            uninstallShortcut(context, intent, info.labelRes, info.icon);
        }
    }

    public static void uninstallShortcut(Context context, Intent shortcutIntent) {
        ApplicationInfo info = context.getApplicationInfo();
        if (info != null) {
            uninstallShortcut(context, shortcutIntent, info.labelRes, info.icon);
        }
    }

    public static void uninstallShortcut(Context context, Intent shortcutIntent, int titleRes, int iconRes) {
        final Intent intent = generateShortcutIntent(context, shortcutIntent, titleRes, iconRes);
        intent.setAction(ACTION_UNINSTALL_SHORTCUT);
        context.sendBroadcast(intent, null);
    }

    public static boolean hasShortcut(Context context, int titleRes) {
        final String url;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        }

        Cursor cursor = null;
        try {
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(Uri.parse(url), new String[]{"title"}, "title=?", new String[]{context.getString(titleRes)}, null);
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            }
        } catch (Throwable e) {
            // ignore.
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    private static Intent generateShortcutIntent(Context context, Intent shortcutIntent, int titleRes, int iconRes) {
        final Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(titleRes));
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, iconRes));
        intent.putExtra("duplicate", false);
        return intent;
    }
}
