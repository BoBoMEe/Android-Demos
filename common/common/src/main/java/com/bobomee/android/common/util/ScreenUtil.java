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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取屏幕的宽高度
 *
 * @author BoBoMEe
 */
public class ScreenUtil {

  /**
   * shot the current screen ,with the status but the status is trans *
   *
   * @param ctx current activity
   */
  public static Bitmap shotActivity(Activity ctx) {

    View view = ctx.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();

    Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
        view.getMeasuredHeight());

    view.setDrawingCacheEnabled(false);
    view.destroyDrawingCache();

    return bp;
  }

  /**
   * shot the current screen ,with the status and navigationbar*
   */
  public static Bitmap ShotActivity$WithoutStatus$WithoutNavigationBar(Activity ctx) {
    int statusH = getStatusH(ctx);
    int navigationBarH = getNavigationBarHeight(ctx);

    View view = ctx.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();

    Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusH, view.getMeasuredWidth(),
        view.getMeasuredHeight() - statusH - navigationBarH);

    view.setDrawingCacheEnabled(false);
    view.destroyDrawingCache();

    return bp;
  }

  /**
   * 获得屏幕高度
   */
  public static int getScreenWidth(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    wm.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.widthPixels;
  }

  /**
   * get the height of screen *
   */
  public static int getScreenH(Context ctx) {
    int h = 0;
    if (Build.VERSION.SDK_INT > 13) {
      Point p = new Point();
      ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
      h = p.y;
    } else {
      h = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
          .getHeight();
    }
    return h;
  }

  /**
   * get the width of screen **
   */
  public static int getScreenW(Context ctx) {
    int w = 0;
    if (Build.VERSION.SDK_INT > 13) {
      Point p = new Point();
      ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
      w = p.x;
    } else {
      w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
          .getWidth();
    }
    return w;
  }

  /**
   * get the height of status *
   */
  public static int getStatusH(Activity ctx) {
    Rect s = new Rect();
    ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(s);
    return s.top;
  }

  /**
   * get the height of status *
   */
  public static int getStatusHeight(Context activity) {
    int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return resourceId > 0 ? activity.getResources().getDimensionPixelSize(resourceId) : 0;
  }

  /**
   * get the height of status *
   */
  public static int getStatusH(Context ctx) {
    int statusHeight = -1;
    try {
      Class<?> clazz = Class.forName("com.android.internal.R$dimen");
      Object object = clazz.newInstance();
      int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
      statusHeight = ctx.getResources().getDimensionPixelSize(height);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return statusHeight;
  }

  /**
   * get the height of title *
   */
  public static int getTitleH(Activity ctx) {
    int contentTop = ctx.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    return contentTop - getStatusH(ctx);
  }

  /**
   * get the height of NavigationBar
   */

  public static int getNavigationBarHeight(Activity mActivity) {
    Resources resources = mActivity.getResources();
    int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
    int height = resources.getDimensionPixelSize(resourceId);
    return height;
  }

  /**
   * http://stackoverflow.com/questions/12742343/android-get-screenshot-of-all-listview-items
   */
  public static Bitmap shotListView(ListView listview) {

    ListAdapter adapter = listview.getAdapter();
    int itemscount = adapter.getCount();
    int allitemsheight = 0;
    List<Bitmap> bmps = new ArrayList<Bitmap>();

    for (int i = 0; i < itemscount; i++) {

      View childView = adapter.getView(i, null, listview);
      childView.measure(
          View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

      childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
      childView.setDrawingCacheEnabled(true);
      childView.buildDrawingCache();
      bmps.add(childView.getDrawingCache());
      allitemsheight += childView.getMeasuredHeight();
    }

    Bitmap bigbitmap =
        Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
    Canvas bigcanvas = new Canvas(bigbitmap);

    Paint paint = new Paint();
    int iHeight = 0;

    for (int i = 0; i < bmps.size(); i++) {
      Bitmap bmp = bmps.get(i);
      bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
      iHeight += bmp.getHeight();

      bmp.recycle();
      bmp = null;
    }

    return bigbitmap;
  }

  /**
   * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
   */
  public static Bitmap shotRecyclerView(RecyclerView view) {
    RecyclerView.Adapter adapter = view.getAdapter();
    Bitmap bigBitmap = null;
    if (adapter != null) {
      int size = adapter.getItemCount();
      int height = 0;
      Paint paint = new Paint();
      int iHeight = 0;
      final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

      // Use 1/8th of the available memory for this memory cache.
      final int cacheSize = maxMemory / 8;
      LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
      for (int i = 0; i < size; i++) {
        RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
        adapter.onBindViewHolder(holder, i);
        holder.itemView.measure(
            View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
            holder.itemView.getMeasuredHeight());
        holder.itemView.setDrawingCacheEnabled(true);
        holder.itemView.buildDrawingCache();
        Bitmap drawingCache = holder.itemView.getDrawingCache();
        if (drawingCache != null) {

          bitmaCache.put(String.valueOf(i), drawingCache);
        }
        height += holder.itemView.getMeasuredHeight();
      }

      bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
      Canvas bigCanvas = new Canvas(bigBitmap);
      Drawable lBackground = view.getBackground();
      if (lBackground instanceof ColorDrawable) {
        ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
        int lColor = lColorDrawable.getColor();
        bigCanvas.drawColor(lColor);
      }

      for (int i = 0; i < size; i++) {
        Bitmap bitmap = bitmaCache.get(String.valueOf(i));
        bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
        iHeight += bitmap.getHeight();
        bitmap.recycle();
      }
    }
    return bigBitmap;
  }

  /**
   * http://blog.csdn.net/lyy1104/article/details/40048329
   */
  public static Bitmap shotScrollView(ScrollView scrollView) {
    int h = 0;
    Bitmap bitmap = null;
    for (int i = 0; i < scrollView.getChildCount(); i++) {
      h += scrollView.getChildAt(i).getHeight();
      scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
    }
    bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
    final Canvas canvas = new Canvas(bitmap);
    scrollView.draw(canvas);
    return bitmap;
  }

  /**
   * http://stackoverflow.com/questions/9791714/take-a-screenshot-of-a-whole-view
   */
  public static Bitmap shotView(View v, int width, int height) {
    Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(b);
    v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
    v.draw(c);
    return b;
  }
}
