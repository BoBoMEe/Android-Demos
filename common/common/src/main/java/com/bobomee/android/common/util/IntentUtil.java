package com.bobomee.android.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/**
 * Created on 16/7/17.下午2:05.
 *
 * @author bobomee.
 *         wbwjx115@gmail.com
 */
public class IntentUtil {

  /**
   * 跳转拨号盘,需要权限
   * @param context
   * @param phoneNo
   */
  public static void actionDial(Context context,String phoneNo){
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
    intent.setAction(Intent.ACTION_DIAL);// 拨号盘
    context.startActivity(intent);
  }

  /**
   * 不跳转到拨号盘,直接拨打电话
   * @param context
   * @param phoneNo
   */
  public static void actionCall(Context context,String phoneNo){
    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
    intent.setAction(Intent.ACTION_CALL);// 直接拨号
    context.startActivity(intent);
  }

  private IntentUtil() {
  }

  public static Intent openFile(String filePath) {
    if (MimeUtil.isAudio(filePath)) {
      return getAudioFileIntent(filePath);
    } else if (MimeUtil.isVideo(filePath)) {
      return getVideoFileIntent(filePath);
    } else if (MimeUtil.isImage(filePath)) {
      return getImageFileIntent(filePath);
    } else if (MimeUtil.isApk(filePath)) {
      return getApkFileIntent(filePath);
    } else if (MimeUtil.isPpt(filePath)) {
      return getPptFileIntent(filePath);
    } else if (MimeUtil.isExcel(filePath)) {
      return getExcelFileIntent(filePath);
    } else if (MimeUtil.isWord(filePath)) {
      return getWordFileIntent(filePath);
    } else if (MimeUtil.isPdf(filePath)) {
      return getPdfFileIntent(filePath);
    } else if (MimeUtil.isChm(filePath)) {
      return getChmFileIntent(filePath);
    } else if (MimeUtil.isTxt(filePath)) {
      return getTextFileIntent(filePath, false);
    } else {
      return getAllIntent(filePath);
    }
  }

  public static Intent getAllIntent(String param) {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(android.content.Intent.ACTION_VIEW);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "*/*");
    return intent;
  }

  public static Intent getApkFileIntent(String param) {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction(android.content.Intent.ACTION_VIEW);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/vnd.android.package-archive");
    return intent;
  }

  public static Intent getVideoFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    intent.putExtra("configchange", 0);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "video/*");
    return intent;
  }

  public static Intent getAudioFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra("oneshot", 0);
    intent.putExtra("configchange", 0);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "audio/*");
    return intent;
  }

  public static Intent getHtmlFileIntent(String param) {
    Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setDataAndType(uri, "text/html");
    return intent;
  }

  public static Intent getImageFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "image/*");
    return intent;
  }

  public static Intent getPptFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
    return intent;
  }

  public static Intent getExcelFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/vnd.ms-excel");
    return intent;
  }

  public static Intent getWordFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/msword");
    return intent;
  }

  public static Intent getChmFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/x-chm");
    return intent;
  }

  public static Intent getTextFileIntent(String param, boolean paramBoolean) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    if (paramBoolean) {
      Uri uri1 = Uri.parse(param);
      intent.setDataAndType(uri1, "text/plain");
    } else {
      Uri uri2 = Uri.fromFile(new File(param));
      intent.setDataAndType(uri2, "text/plain");
    }
    return intent;
  }

  public static Intent getPdfFileIntent(String param) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = Uri.fromFile(new File(param));
    intent.setDataAndType(uri, "application/pdf");
    return intent;
  }

  public static Intent getTakePictureIntent(File imageFile) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
    return intent;
  }

  public static Intent getSelectFileIntent(String type, String title) {
    Intent intent = new Intent();
    intent.setType(type);
    intent.setAction(Intent.ACTION_GET_CONTENT);
    return Intent.createChooser(intent, title);
  }

  public static Intent getInstallIntent(Uri uri) {
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setDataAndType(uri, "application/vnd.android.package-archive");
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    return i;
  }

  /**
   * install apk file
   */
  public static void installApk(Context mContext, Uri uri) {
    Intent installIntent = getInstallIntent(uri);
    mContext.startActivity(installIntent);
  }

  public static boolean uninstallApk(Context context, String packageName) {
    if (TextUtils.isEmpty(packageName)) {
      return false;
    }
    Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(i);
    return true;
  }

  /**
   * make marketscore
   */
  public static void MarketScore(Context context) {

    try {
      Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception e) {
      Log.d("AppUtil", "Couldn't launch the market !");
    }

  }

  /**
   * 创建快捷方式
   * @param cxt
   * @param shortCutName
   * @param icon
   * @param cls 要启动的Activity
   */

  public static void createDeskShortCut(Context cxt, String shortCutName, int icon, Class<?> cls) {
    Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    shortcutIntent.putExtra("duplicate", false);
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
    Parcelable ico = Intent.ShortcutIconResource.fromContext(cxt.getApplicationContext(), icon);
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);
    Intent intent = new Intent(cxt, cls);
    intent.setAction("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.LAUNCHER");
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
    cxt.sendBroadcast(shortcutIntent);
  }

  /**
   * 创建快捷方式
   * @param ctx
   * @param shortCutName
   * @param iconId
   * @param presentIntent 点击的Intent
   */
  public static void createShortcut(Context ctx, String shortCutName, int iconId,
      Intent presentIntent) {
    Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
    shortcutIntent.putExtra("duplicate", false);
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
        Intent.ShortcutIconResource.fromContext(ctx, iconId));
    shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, presentIntent);
    ctx.sendBroadcast(shortcutIntent);
  }

}
