package com.bobomee.android.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Created on 16/5/26.下午3:14.
 * @author bobomee.
 * wbwjx115@gmail.com
 */
public class FileSizeUtil {
  public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
  public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
  public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
  public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

  /**
   * 获取文件指定文件的指定单位的大小
   *
   * @param filePath 文件路径
   * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
   * @return double值的大小
   */
  public static String getFileOrFilesSize(String filePath, int sizeType) {
    File file = new File(filePath);
    long blockSize = 0;
    try {
      if (file.isDirectory()) {
        blockSize = getFileSizes(file);
      } else {
        blockSize = getFileSize(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return FormetFileSize(blockSize, sizeType);
  }

  /**
   * 调用此方法自动计算指定文件或指定文件夹的大小
   *
   * @param filePath 文件路径
   * @return 计算好的带B、KB、MB、GB的字符串
   */
  public static String getAutoFileOrFilesSize(String filePath) {
    File file = new File(filePath);
    long blockSize = 0;
    try {
      if (file.isDirectory()) {
        blockSize = getFileSizes(file);
      } else {
        blockSize = getFileSize(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return FormetFileSize(blockSize);
  }

  /**
   * 获取指定文件大小
   *
   * @throws Exception
   */
  private static long getFileSize(File file) throws Exception {
    long size = 0;
    if (file.exists()) {
      FileInputStream fis = null;
      fis = new FileInputStream(file);
      size = fis.available();
    } else {
      file.createNewFile();
    }
    return size;
  }

  /**
   * 获取指定文件夹
   *
   * @throws Exception
   */
  private static long getFileSizes(File f) throws Exception {
    long size = 0;
    File flist[] = f.listFiles();
    for (int i = 0; i < flist.length; i++) {
      if (flist[i].isDirectory()) {
        size = size + getFileSizes(flist[i]);
      } else {
        size = size + getFileSize(flist[i]);
      }
    }
    return size;
  }

  /**
   * 转换文件大小
   */
  private static String FormetFileSize(long fileS) {
    DecimalFormat df = new DecimalFormat("#.00");
    String fileSizeString = "";
    String wrongSize = "0B";
    if (fileS == 0) {
      return wrongSize;
    }
    if (fileS < 1024) {
      fileSizeString = df.format((double) fileS) + "B";
    } else if (fileS < 1048576) {
      fileSizeString = df.format((double) fileS / 1024) + "KB";
    } else if (fileS < 1073741824) {
      fileSizeString = df.format((double) fileS / 1048576) + "MB";
    } else {
      fileSizeString = df.format((double) fileS / 1073741824) + "GB";
    }
    return fileSizeString;
  }

  /**
   * 转换文件大小,指定转换的类型
   */
  public static String FormetFileSize(long fileS, int sizeType) {
    DecimalFormat df = new DecimalFormat("#.00");
    double fileSizeLong = 0;
    String fileSize = "M";
    switch (sizeType) {
      case SIZETYPE_B:
        fileSizeLong = Double.valueOf(df.format((double) fileS));
        fileSize = fileSizeLong+"B";
        break;
      case SIZETYPE_KB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
        fileSize = fileSizeLong+"KB";
        break;
      case SIZETYPE_MB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
        fileSize = fileSizeLong+"MB";
        break;
      case SIZETYPE_GB:
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
        fileSize = fileSizeLong+"GB";
        break;
      default:
        break;
    }
    return fileSize;
  }
}
