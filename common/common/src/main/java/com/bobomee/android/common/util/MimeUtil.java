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

/**
 * Created on 16/7/17.下午2:11.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class MimeUtil {
  private MimeUtil() {
  }

  public static boolean isVideo(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".mp4") || ext.endsWith(".avi") || ext.endsWith(".wmv") || ext.endsWith(".rmvb") || ext.endsWith(".mpg") || ext.endsWith(".mpeg") || ext.endsWith(".3gp");
  }

  public static boolean isImage(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".jpg") || ext.endsWith(".jpeg") || ext.endsWith(".png") || ext.endsWith(".gif") || ext.endsWith(".bmp");
  }

  public static boolean isAudio(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".mp3") || ext.endsWith(".wav") || ext.endsWith(".wma") || ext.endsWith(".amr") || ext.endsWith(".ogg");
  }

  public static boolean isPpt(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".ppt") || ext.endsWith(".pptx");
  }

  public static boolean isWord(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".doc") || ext.endsWith(".docx");
  }

  public static boolean isExcel(String filePath) {
    String ext = filePath.toLowerCase();
    return ext.endsWith(".xls") || ext.endsWith(".xlsx");
  }

  public static boolean isApk(String filePath) {
    return filePath.toLowerCase().endsWith(".apk");
  }

  public static boolean isPdf(String filePath) {
    return filePath.toLowerCase().endsWith(".pdf");
  }

  public static boolean isTxt(String filePath) {
    return filePath.toLowerCase().endsWith(".txt");
  }

  public static boolean isChm(String filePath) {
    return filePath.toLowerCase().endsWith(".chm");
  }
}
