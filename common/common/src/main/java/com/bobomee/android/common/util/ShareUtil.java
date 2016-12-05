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
import android.content.Intent;
import android.net.Uri;

/**
 * Created on 16/7/10.上午12:02.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class ShareUtil {

  public static void shareFile(Context context, String filePath, String title) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    Uri uri = Uri.parse("file://" + filePath);
    intent.setType("*/*");
    intent.putExtra(Intent.EXTRA_STREAM, uri);
    context.startActivity(Intent.createChooser(intent, title));
  }

  public static void shareText(Context context, String extraText, String title) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, title);
    intent.putExtra(Intent.EXTRA_TEXT, extraText);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(Intent.createChooser(intent, title));
  }

  public static void shareImage(Context context, Uri uri, String title) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
    shareIntent.setType("image/jpeg");
    context.startActivity(Intent.createChooser(shareIntent, title));
  }
}
