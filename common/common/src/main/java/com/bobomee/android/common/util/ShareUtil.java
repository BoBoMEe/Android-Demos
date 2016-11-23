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
