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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/7/17.下午2:18.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class MediaScanner {
  private static String AUDIO_PATH = MediaStore.Audio.AudioColumns.DATA;
  private static String AUDIO_DISPLAYHNAME = MediaStore.Audio.AudioColumns.DISPLAY_NAME;
  private static String AUDIO_COLUMN_STRS[] = { AUDIO_PATH, AUDIO_DISPLAYHNAME };

  private static String VIDEO_PATH = MediaStore.Video.VideoColumns.DATA;
  private static String VIDEO_DISPLAYHNAME = MediaStore.Video.VideoColumns.DISPLAY_NAME;
  private static String VIDEO_COLUMN_STRS[] = { VIDEO_PATH, VIDEO_DISPLAYHNAME };

  private static String IMAGE_PATH = MediaStore.Images.ImageColumns.DATA;
  private static String IMAGE_DISPLAYHNAME = MediaStore.Images.ImageColumns.DISPLAY_NAME;
  private static String IMAGE_COLUMN_STRS[] = { IMAGE_PATH, IMAGE_DISPLAYHNAME };

  private MediaScanner() {
  }

  public static List<MediaFile> scanAudio(Context context) {
    return scanMedia(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, AUDIO_COLUMN_STRS,
        AUDIO_DISPLAYHNAME, AUDIO_DISPLAYHNAME, AUDIO_PATH);
  }

  public static List<MediaFile> scanVideo(Context context) {
    return scanMedia(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_COLUMN_STRS,
        VIDEO_DISPLAYHNAME, VIDEO_DISPLAYHNAME, VIDEO_PATH);
  }

  public static List<MediaFile> scanImage(Context context) {
    return scanMedia(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_COLUMN_STRS,
        IMAGE_DISPLAYHNAME, IMAGE_DISPLAYHNAME, IMAGE_PATH);
  }

  public static List<MediaFile> scanMedia(Context context, Uri uri, String[] projection,
      String sortOrder, String nameColumn, String pathColumn) {
    List<MediaFile> results = new ArrayList<MediaFile>();
    Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
    if (cursor != null && cursor.getCount() != 0) {
      int nameIndex = cursor.getColumnIndexOrThrow(nameColumn);
      int dirIndex = cursor.getColumnIndexOrThrow(pathColumn);
      while (cursor.moveToNext()) {
        results.add(new MediaFile(cursor.getString(nameIndex), cursor.getString(dirIndex)));
      }
      cursor.close();
    }
    return results;
  }

  static class MediaFile {
    public String name;
    public String path;

    public MediaFile(String name, String path) {
      this.name = name;
      this.path = path;
    }
  }
}
