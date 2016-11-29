package com.bobomee.android.common.util;

import android.util.Base64;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

  /**
   * 使用gzip进行压缩
   */
  public static String gzip(String primStr) {
    if (primStr == null || primStr.length() == 0) {
      return primStr;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    GZIPOutputStream gzip = null;
    try {
      gzip = new GZIPOutputStream(out);
      gzip.write(primStr.getBytes());
      return Arrays.toString(Base64.encode(out.toByteArray(), 0));
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } finally {
      IOUtil.closeQuietly(gzip);
    }
  }

  /**
   * <p>
   * Description:使用gzip进行解压缩
   * </p>
   */
  public static String gunzip(String compressedStr) {
    if (compressedStr == null) {
      return null;
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ByteArrayInputStream in = null;
    GZIPInputStream ginzip = null;
    byte[] compressed = null;
    String decompressed = null;
    try {
      compressed = Base64.decode(compressedStr,0);
      in = new ByteArrayInputStream(compressed);
      ginzip = new GZIPInputStream(in);

      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = ginzip.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(ginzip);
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(out);
    }

    return decompressed;
  }

  /**
   * 使用zip进行压缩
   *
   * @param str 压缩前的文本
   * @return 返回压缩后的文本
   */
  public static final String zip(String str) {
    if (str == null) return null;
    byte[] compressed;
    ByteArrayOutputStream out = null;
    ZipOutputStream zout = null;
    String compressedStr = null;
    try {
      out = new ByteArrayOutputStream();
      zout = new ZipOutputStream(out);
      zout.putNextEntry(new ZipEntry("0"));
      zout.write(str.getBytes());
      zout.closeEntry();
      compressed = out.toByteArray();
      compressedStr = Arrays.toString(Base64.encode(compressed, 0));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(zout);
      IOUtil.closeQuietly(out);
    }
    return compressedStr;
  }

  /**
   * 使用zip进行解压缩
   *
   * @param compressedStr 压缩后的文本
   * @return 解压后的字符串
   */
  public static final String unzip(String compressedStr) {
    if (compressedStr == null) {
      return null;
    }

    ByteArrayOutputStream out = null;
    ByteArrayInputStream in = null;
    ZipInputStream zin = null;
    String decompressed = null;
    try {
      byte[] compressed = Base64.decode(compressedStr,0);
      out = new ByteArrayOutputStream();
      in = new ByteArrayInputStream(compressed);
      zin = new ZipInputStream(in);
      zin.getNextEntry();
      byte[] buffer = new byte[1024];
      int offset = -1;
      while ((offset = zin.read(buffer)) != -1) {
        out.write(buffer, 0, offset);
      }
      decompressed = out.toString();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(zin);
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(out);
    }
    return decompressed;
  }

  /**
   * 压缩文件,文件夹
   *
   * @param srcFileString 要压缩的文件/文件夹名字
   * @param zipFileString 指定压缩的目的和名字
   * @throws Exception
   */
  public static void ZipFolder(String srcFileString, String zipFileString) {
    Log.v("XZip", "ZipFolder(String, String)");

    ZipOutputStream outZip = null;

    try {
      //创建Zip包
      outZip = new ZipOutputStream(new FileOutputStream(zipFileString));

      //打开要输出的文件
      File file = new File(srcFileString);

      //压缩
      ZipFiles(file.getParent() + File.separator, file.getName(), outZip);

      outZip.finish();
    } catch (Exception e) {
    } finally {
      //完成,关闭
      IOUtil.closeQuietly(outZip);
    }
  }//end of func

  /**
   * 压缩文件
   *
   * @throws Exception
   */
  private static void ZipFiles(String folderString, String fileString,
      ZipOutputStream zipOutputSteam) {

    if (zipOutputSteam == null) return;

    File file = new File(folderString + fileString);

    try {
      //判断是不是文件
      if (file.isFile()) {

        ZipEntry zipEntry = new ZipEntry(fileString);
        java.io.FileInputStream inputStream = new java.io.FileInputStream(file);
        zipOutputSteam.putNextEntry(zipEntry);

        int len;
        byte[] buffer = new byte[4096];

        while ((len = inputStream.read(buffer)) != -1) {
          zipOutputSteam.write(buffer, 0, len);
        }

        zipOutputSteam.closeEntry();
      } else {

        //文件夹的方式,获取文件夹下的子文件
        String fileList[] = file.list();

        //如果没有子文件, 则添加进去即可
        if (fileList.length <= 0) {
          ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
          zipOutputSteam.putNextEntry(zipEntry);
          zipOutputSteam.closeEntry();
        }

        //如果有子文件, 遍历子文件
        for (int i = 0; i < fileList.length; i++) {
          ZipFiles(folderString, fileString + File.separator + fileList[i], zipOutputSteam);
        }//end of for
      }//end of if
    } catch (Exception e) {
    }
  }//end of func

  /**
   * 解压一个压缩文档 到指定位置
   *
   * @param zipFileString 压缩包的名字
   * @param outPathString 指定的路径
   * @return isSuccess
   */
  public static boolean UnZipFolder(String zipFileString, String outPathString) {
    ZipInputStream inZip = null;
    try {
      inZip = new ZipInputStream(new java.io.FileInputStream(zipFileString));
      ZipEntry zipEntry;
      String szName;
      File file1 = new File(outPathString);
      if (!file1.exists()) {
        file1.mkdirs();
      }
      //long maxDelay = 0;
      while ((zipEntry = inZip.getNextEntry()) != null) {
        szName = zipEntry.getName();

        if (zipEntry.isDirectory()) {

          // get the folder name of the widget
          szName = szName.substring(0, szName.length() - 1);
          File folder = new File(outPathString + File.separator + szName);
          folder.mkdirs();
        } else {

          File file = new File(outPathString + File.separator + szName);
          file.createNewFile();
          // get the output stream of the file
          FileOutputStream out = new FileOutputStream(file);
          int len;
          byte[] buffer = new byte[1024];
          // read (len) bytes into buffer
          while ((len = inZip.read(buffer)) != -1) {
            // write (len) byte from buffer at the position 0
            out.write(buffer, 0, len);
            out.flush();
          }
          out.close();
        }
      }
      return true;
    } // end of while
    catch (IOException e) {
      e.printStackTrace();
      FileUtil.deleteDirectory(outPathString);
    } finally {
      IOUtil.closeQuietly(inZip);
    }
    return false;
  }// end of func

  /**
   * 解压zip输入流到输出文件夹
   *
   * @param zipFileName 输入流
   * @param outputDirectory 目标文件夹
   */
  public static void unzip(InputStream zipFileName, String outputDirectory) {
    try {
      ZipInputStream in = new ZipInputStream(zipFileName);
      // 获取ZipInputStream中的ZipEntry条目，一个zip文件中可能包含多个ZipEntry，
      // 当getNextEntry方法的返回值为null，则代表ZipInputStream中没有下一个ZipEntry，
      // 输入流读取完成；
      ZipEntry entry = in.getNextEntry();

      long maxDelay = 0;
      while (entry != null) {
        // 创建以zip包文件名为目录名的根目录
        File file = new File(outputDirectory);
        file.mkdirs();
        if (entry.isDirectory()) {
          String name = entry.getName();
          name = name.substring(0, name.length() - 1);

          file = new File(outputDirectory + File.separator + name);
          file.mkdir();
        } else {

          file = new File(outputDirectory + entry.getName());
          file.createNewFile();
          FileOutputStream out = new FileOutputStream(file);

          int len;
          byte[] buffer = new byte[4096];
          long start = System.currentTimeMillis();
          while ((len = in.read(buffer)) != -1) {
            // write (len) byte from buffer at the position 0
            out.write(buffer, 0, len);
            out.flush();
          }
          out.close();
          long delay = System.currentTimeMillis() - start;
          if (delay > maxDelay) maxDelay = delay;
        }
        // 读取下一个ZipEntry
        entry = in.getNextEntry();
      }
      Log.d("hl", "max delay is " + maxDelay);
      in.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}