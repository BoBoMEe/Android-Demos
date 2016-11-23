package com.bobomee.android.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩解压工具类
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class ZipUtil {

  public static void zip(InputStream is, OutputStream os) {
    GZIPOutputStream gzip = null;
    try {
      gzip = new GZIPOutputStream(os);
      byte[] buf = new byte[1024];
      int len;
      while ((len = is.read(buf)) != -1) {
        gzip.write(buf, 0, len);
        gzip.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(is);
      IOUtil.closeQuietly(gzip);
    }
  }

  public static void unzip(InputStream is, OutputStream os) {
    GZIPInputStream gzip = null;
    try {
      gzip = new GZIPInputStream(is);
      byte[] buf = new byte[1024];
      int len;
      while ((len = gzip.read(buf)) != -1) {
        os.write(buf, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtil.closeQuietly(gzip);
      IOUtil.closeQuietly(os);
    }
  }
}
