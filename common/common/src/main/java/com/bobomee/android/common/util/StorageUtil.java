/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:5
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

import android.annotation.TargetApi;
import android.content.Context;
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

    /**
     * Check if the primary "external" storage device is available.
     *
     * @return
     */
    public static boolean hasSDCardMounted() {
        String state = Environment.getExternalStorageState();
        if (state != null && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
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
        if (hasSDCardMounted()) {

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
        if (hasSDCardMounted()) {

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
