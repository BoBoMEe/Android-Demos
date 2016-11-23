/*
 * Android_Common. lastModified by bobomee at 2016.5.16 11:1
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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import java.io.FileOutputStream;
import java.io.IOException;

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
    public static Bitmap shotScreen(Activity ctx) {

        View view = ctx.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, getScreenW(ctx), getScreenH(ctx));

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bp;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    /**
     * shot the current screen ,with the status *
     */
    public static Bitmap ShotWithoutStatus(Activity ctx) {
        int statusH = getStatusH(ctx);

        View view = ctx.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusH, getScreenW(ctx), getScreenH(ctx) - statusH);

        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();

        return bp;

    }

    /**
     * get the height of screen *
     */
    public static int getScreenH(Context ctx) {
        int h = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            h = p.y;
        } else {
            h = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
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
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getWidth();
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
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
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
        int contentTop = ctx.getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusH(ctx);
    }

    /**
     * save pic to sdcard
     */
    public static void savePic(Bitmap b, String filePath) {
        if (null != b && !TextUtils.isEmpty(filePath)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(filePath);
                b.compress(Bitmap.CompressFormat.PNG, 60, fos);
                fos.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                IOUtil.closeQuietly(fos);
            }
        }
    }

    /**
     * convert the View to a bitmap *
     */
    public static Bitmap getViewBitmap(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();

        Bitmap bp = ScreenUtil.getViewBp(v);

        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();

        return bp;
    }


    private static Bitmap getViewBp(View v) {
        if (null == v) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(MeasureSpec.makeMeasureSpec(v.getWidth(),
                    MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                    v.getHeight(), MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        return b;
    }

    private static Bitmap getViewBpWithoutBottom(View v) {
        if (null == v) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(MeasureSpec.makeMeasureSpec(v.getWidth(),
                    MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                    v.getHeight(), MeasureSpec.EXACTLY));

            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }

        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(),
                v.getMeasuredHeight() - v.getPaddingBottom());

        return bp;
    }

    /**
     * get the bitmap of a scrollView *
     */
    public static Bitmap getViewBitmap(Context ctx, ScrollView sv) {
        if (null == sv) {
            return null;
        }
        // enable something
        sv.setVerticalScrollBarEnabled(false);
        sv.setVerticalFadingEdgeEnabled(false);
        sv.scrollTo(0, 0);
        sv.setDrawingCacheEnabled(true);
        sv.buildDrawingCache(true);
        Bitmap b = getViewBpWithoutBottom(sv);

        /**
         * vh : the height of the scrollView that is visible <BR>
         * th : the total height of the scrollView <BR>
         **/
        int vh = sv.getHeight();// 1230
        int th = sv.getChildAt(0).getHeight();// 2560

        /** the total height is more than one screen */
        if (th > vh) {
            int w = getScreenW(ctx);
            int absVh = vh - sv.getPaddingTop() - sv.getPaddingBottom();
            do {
                int restHeight = th - vh;
                Bitmap temp = null;
                if (restHeight <= absVh) {
                    sv.scrollBy(0, restHeight);
                    vh += restHeight;
                    temp = getViewBp(sv);
                } else {
                    sv.scrollBy(0, absVh);
                    vh += absVh;
                    temp = getViewBpWithoutBottom(sv);
                }
                b = mergeBitmap(vh, w, temp, 0, sv.getScrollY(), b, 0, 0);
            } while (vh < th);
        }

        // restore somthing
        sv.scrollTo(0, 0);
        sv.setVerticalScrollBarEnabled(true);
        sv.setVerticalFadingEdgeEnabled(true);
        sv.setDrawingCacheEnabled(false);
        sv.destroyDrawingCache();
        return b;
    }

    public static Bitmap mergeBitmap(int newImageH, int newIamgeW,
                                     Bitmap background, float backX, float backY, Bitmap foreground,
                                     float foreX, float foreY) {
        if (null == background || null == foreground) {
            return null;
        }
        // create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newbmp = Bitmap.createBitmap(newIamgeW, newImageH,
                Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        // draw bg into
        cv.drawBitmap(background, backX, backY, null);
        // draw fg into
        cv.drawBitmap(foreground, foreX, foreY, null);
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储

        return newbmp;
    }

}
