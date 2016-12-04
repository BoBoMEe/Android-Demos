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

import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created on 16/5/16.下午3:30.
 * @author bobomee.
 * wbwjx115@gmail.com
 */
public class FileUtil {
    /**
     * 根据值读取
     */
    public static String readProperties(String filePath, String key, String defaultValue) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(filePath)) {
            return null;
        }
        String value = null;
        FileInputStream fis = null;
        File f = new File(filePath);
        try {
            if (!f.exists() || !f.isFile()) {
                f.createNewFile();
            }
            fis = new FileInputStream(f);
            Properties p = new Properties();
            p.load(fis);
            value = p.getProperty(key, defaultValue);
        } catch (IOException e) {
            LogUtil.e(e.toString());
        } finally {
            IOUtil.closeQuietly(fis);
        }
        return value;
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        Boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    public static void getAllFileNames(File file, ArrayList<String> arrayList) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files)
                    if (subFile.isDirectory())
                        getAllFileNames(subFile, arrayList);
                    else
                        arrayList.add(subFile.getAbsolutePath());
            }
        } else {
            arrayList.add(file.getAbsolutePath());
        }
    }

    public static boolean copyFolder(String oldPath, String newPath)
            throws Exception {
        boolean result = false;
        try {
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator))
                    temp = new File(oldPath + file[i]);
                else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath +
                            "/" + temp.getName().toString());
                    byte[] b = new byte[2048];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
            result = true;
        } catch (Exception e) {
            Log.e("mouee", "复制整个文件夹内容时出错！原文件夹：" + oldPath + ",新文件夹：" + newPath,
                    e);
            throw e;
        }
        return result;
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) {
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = in.read(buffer)) > 0) {
                out.write(buffer, 0, i);
                out.flush();
            }
            if (deleteSrc) {
                srcFile.delete();
            }
        } catch (Exception e) {
            LogUtil.e(e.toString());
            return false;
        } finally {
            IOUtil.closeQuietly(out);
            IOUtil.closeQuietly(in);
        }
        return true;
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 复制文件，可以选择是否删除源文件
     */
    public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        return copyFile(srcFile, destFile, deleteSrc);
    }


    /**
     * 根据url生成一个合法的文件名（即去掉一些不能作为文件名的非法字符）
     */
    public static String makeFileNameFromUrl(String url) {

        if (url == null) {
            return null;
        }
        //要先从原url中把域名去掉
        int start = url.indexOf("//");
        start = start == -1 ? 0 : start;
        start = url.indexOf('/', start + 2);
        start = start == -1 ? 0 : start;
        return url.substring(start).replaceAll("[^\\w\\-_]", "");//只保留部分字符
    }

    /**
     * Comparator of files.
     */
    public interface FileComparator {
        boolean equals(File lhs, File rhs);
    }

    /**
     * Simple file comparator which only depends on file length and modification time.
     */
    public final static FileComparator SIMPLE_COMPARATOR = new FileComparator() {
        @Override public boolean equals(File lhs, File rhs) {
           return  (lhs.length() == rhs.length()) && (lhs.lastModified() == rhs.lastModified());
        }
    };

    /**
     * 获取真实的文件路径
     *
     * @param fileName fileName
     * @return 真实的文件路径
     */
    public static String getCanonicalPath(String fileName) {
        try {
            return new File(fileName).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * 文件最近修改时间
     *
     * @param path 文件路径
     * @return 从1970年1月1日0点起，单位毫秒
     */
    public static long lastModified(String path) {
        if (StringUtil.isEmpty(path)) {
            return 0;
        }
        return new File(path).lastModified();
    }

    /**
     * 重命名文件
     *
     * @param srcPath 原名
     * @param dstPath 重命名后的文件名
     * @return 成功为true
     */
    public static boolean rename(String srcPath, String dstPath) {
        File file = new File(srcPath);
        return file.isFile() && file.renameTo(new File(dstPath));
    }

    /**
     * 合法化文件名
     * 替换文件名不允许出现的字符，比如{}/\:*?"<>以及无效或者不可视Unicode字符
     *
     * @param fileName 被合法化的文件名
     * @return 合法化后的文件名
     */
    public static String validateFileName(String fileName) {
        // {} \ / : * ? " < > |
        return fileName == null ? null : fileName.replaceAll("([{/\\\\:*?\"<>|}\\u0000-\\u001f\\uD7B0-\\uFFFF]+)", "");
    }


}
