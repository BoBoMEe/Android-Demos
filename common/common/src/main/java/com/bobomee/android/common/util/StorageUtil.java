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

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created on 16/4/16.下午3:29.
 * @author bobomee.
 * wbwjx115@gmail.com
 */
public class StorageUtil {

    private StorageUtil() {
        // static usage.
    }

    private final static int STATE_UNKNOWN = -1;
    private final static int STATE_MOUNTED = 0;
    private final static int STATE_MOUNTED_READ_ONLY = 1;
    private final static int STATE_OTHERS = 2;
    // ------------------------------ dir related -------------------------------
    private final static Object sCacheDirLock = new Object();
    private static int sMonitoredExternalState = STATE_UNKNOWN;
    private final static Singleton<BroadcastReceiver, Void> sReceiver = new Singleton<BroadcastReceiver, Void>() {
        @Override
        protected BroadcastReceiver create(Void param) {
            return new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    onStorageStateChanged();
                }
            };
        }
    };
    private static volatile boolean sReceiverRegistered = false;

    /**
     * Whether external storage is readable.
     *
     * @param context application context.
     */
    public static boolean isExternalReadable(Context context) {
        return isExternalMounted(context) || isExternalMountedReadOnly(context);
    }

    /**
     * Whether external storage is writable.
     *
     * @param context application context.
     */
    public static boolean isExternalWritable(Context context) {
        return isExternalMounted(context);
    }

    /**
     * Get the external storage capability.
     *
     * @return External storage capability.
     */
    public static long getExternalCapability() {
        if (!isExternalReadable(null)) {
            return -1;
        }

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long allBlocks = stat.getBlockCount();
        return allBlocks * blockSize;
    }

    /**
     * Get the external storage remaining space.
     *
     * @return External storage remaining space.
     */
    public static long getExternalRemaining() {
        if (!isExternalReadable(null)) {
            return -1;
        }

        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    private static boolean isExternalMounted(Context context) {
        if (sMonitoredExternalState != STATE_UNKNOWN) {
            return sMonitoredExternalState == STATE_MOUNTED;
        }
        int state = retrieveExternalStorageState();
        if (registerReceiverIfNeeded(context)) {
            // update state when register succeed.
            sMonitoredExternalState = state;
        }
        return state == STATE_MOUNTED;
    }

    private static boolean isExternalMountedReadOnly(Context context) {
        if (sMonitoredExternalState != STATE_UNKNOWN) {
            return sMonitoredExternalState == STATE_MOUNTED_READ_ONLY;
        }
        int state = retrieveExternalStorageState();
        if (registerReceiverIfNeeded(context)) {
            // update state when register succeed.
            sMonitoredExternalState = state;
        }
        return state == STATE_MOUNTED_READ_ONLY;
    }

    /**
     * Get the internal storage remaining space.
     *
     * @return Internal storage remaining space.
     */
    public static long getInternalCapability() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long allBlocks = stat.getBlockCount();
        return allBlocks * blockSize;
    }

    /**
     * Get the internal storage capability.
     *
     * @return Internal storage capability.
     */
    public static long getInternalRemaining() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    static void onStorageStateChanged() {
        sMonitoredExternalState = retrieveExternalStorageState();
    }

    private static boolean registerReceiverIfNeeded(Context context) {
        if (sReceiverRegistered) {
            return true;
        }
        if (context == null || context.getApplicationContext() == null) {
            return false;
        }
        synchronized (sReceiver) {
            if (sReceiverRegistered) {
                return true;
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
            filter.addAction(Intent.ACTION_MEDIA_EJECT);
            filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
            filter.addAction(Intent.ACTION_MEDIA_REMOVED);
            filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
            filter.addDataScheme("file");
            context.getApplicationContext().registerReceiver(sReceiver.get(null), filter);
            sReceiverRegistered = true;
            return true;
        }
    }

    private static int retrieveExternalStorageState() {
        String externalState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalState)) {
            return STATE_MOUNTED;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalState)) {
            return STATE_MOUNTED_READ_ONLY;
        } else {
            return STATE_OTHERS;
        }
    }

    /**
     * get the available space
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsableSpace(File path) {
        if (path == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        } else {
            if (!path.exists()) {
                return 0;
            } else {
                final StatFs stats = new StatFs(path.getPath());
                return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
            }
        }

    }

    /**
     * |   ($rootDir)
     * +- /data                    -> Environment.getDataDirectory()
     * |   |
     * |   |   ($appDataDir)
     * |   +- data/$packageName
     * |       |
     * |       |   ($filesDir)
     * |       +- files            -> Context.getFilesDir() / Context.getFileStreamPath("")
     * |       |      |
     * |       |      +- file1     -> Context.getFileStreamPath("file1")
     * |       |
     * |       |   ($cacheDir)
     * |       +- cache            -> Context.getCacheDir()
     * |       |
     * |       +- app_$name        ->(Context.getDir(String name, int mode)
     * |
     * |   ($rootDir)
     * +- /storage/sdcard0         -> Environment.getExternalStorageDirectory()/ Environment.getExternalStoragePublicDirectory("")
     * |                 |
     * |                 +- dir1   -> Environment.getExternalStoragePublicDirectory("dir1")
     * |                 |
     * |                 |   ($appDataDir)
     * |                 +- Andorid/data/$packageName
     * |                                         |
     * |                                         | ($filesDir)
     * |                                         +- files                  -> Context.getExternalFilesDir("")
     * |                                         |    |
     * |                                         |    +- file1             -> Context.getExternalFilesDir("file1")
     * |                                         |    +- Music             -> Context.getExternalFilesDir(Environment.Music);
     * |                                         |    +- Picture           -> Context.getExternalFilesDir(Environment.Picture);
     * |                                         |    +- ...               -> Context.getExternalFilesDir(String type)
     * |                                         |
     * |                                         |  ($cacheDir)
     * |                                         +- cache                  -> Context.getExternalCacheDir()
     * |                                         |
     * |                                         +- ???
     * <p/>
     * <p/>
     * 1.  其中$appDataDir中的数据，在app卸载之后，会被系统删除。
     * <p/>
     * 2.  $appDataDir下的$cacheDir：
     * Context.getCacheDir()：机身内存不足时，文件会被删除
     * Context.getExternalCacheDir()：空间不足时，文件不会实时被删除，可能返回空对象,Context.getExternalFilesDir("")亦同
     * <p/>
     * 3. 内部存储中的$appDataDir是安全的，只有本应用可访问
     * 外部存储中的$appDataDir其他应用也可访问，但是$filesDir中的媒体文件，不会被当做媒体扫描出来，加到媒体库中。
     * <p/>
     * 4. 在内部存储中：通过  Context.getDir(String name, int mode) 可获取和  $filesDir  /  $cacheDir 同级的目录
     * 命名规则：app_ + name，通过Mode控制目录是私有还是共享
     * <p/>
     * <code>
     * Context.getDir("dir1", MODE_PRIVATE):
     * Context.getDir: /data/data/$packageName/app_dir1
     * </code>
     */

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalCacheDir(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File path = context.getExternalCacheDir();

            // In some case, even the sd card is mounted,
            // getExternalCacheDir will return null
            // may be it is nearly full.
            if (path != null) {
                return path;
            }
        }

        // Before Froyo or the path is null,
        // we need to construct the external cache folder ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    public static File getExternalFilesDir(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            File path = context.getExternalFilesDir("");

            // In some case, even the sd card is mounted,
            // getExternalCacheDir will return null
            // may be it is nearly full.
            if (path != null) {
                return path;
            }
        }

        // Before Froyo or the path is null,
        // we need to construct the external cache folder ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/files/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static File getCacheDir(Context context) {

        File cacheDir = context.getCacheDir();

        //判断SD卡是否可用
        if (isExternalMounted(context)) {

            // 获取SD卡可用空间
            File externalCacheDir = getExternalCacheDir(context);

            long externalUsableSpace = getUsableSpace(externalCacheDir);
            long cacheDirUsableSpace = getUsableSpace(cacheDir);

            if (externalUsableSpace < cacheDirUsableSpace) {
                return cacheDir;
            } else {
                return externalCacheDir;
            }

        } else {
            return cacheDir;
        }
    }

    public static File getFilesDir(Context context) {

        File filesDir = context.getFilesDir();

        //判断SD卡是否可用
        if (isExternalMounted(context)) {

            // 获取SD卡可用空间
            File externalFilesDir = getExternalFilesDir(context);

            long externalUsableSpace = getUsableSpace(externalFilesDir);
            long filesDirUsableSpace = getUsableSpace(filesDir);

            if (externalUsableSpace < filesDirUsableSpace) {
                return filesDir;
            } else {
                return externalFilesDir;
            }

        } else {
            return filesDir;
        }
    }


    public static void writeFiles(Context c, String fileName, String content) {
        writeFiles(c, fileName, content, Context.MODE_PRIVATE);
    }

    /**
     * 保存文件内容
     *
     * @param c
     * @param fileName 文件名称
     * @param content  内容
     */
    private static void writeFiles(Context c, String fileName, String content, int mode) {
        // 打开文件获取输出流，文件不存在则自动创建
        FileOutputStream fos = null;
        try {
            fos = c.openFileOutput(fileName, mode);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fos);
        }
    }

    /**
     * 读取文件内容
     *
     * @param c
     * @param fileName 文件名称
     * @return 返回文件内容
     */
    public static String readFiles(Context c, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        String content = null;
        try {
            fis = c.openFileInput(fileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            content = baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(fis);
            IOUtil.closeQuietly(baos);
        }


        return content;
    }


    /////////////////////****////////////////////////////////////
    /**
     * @return 公共下载文件夹
     */
    public static String getPublicDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

}
