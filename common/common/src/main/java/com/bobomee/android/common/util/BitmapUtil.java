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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author：BoBoMEe Created at 2016/1/18.
 */
public class BitmapUtil {

    /**
     * 从SD卡上获取图片。如果不存在则返回null</br>
     *
     * @param filePath 图片的地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return 代表图片的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String filePath, int width, int height) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            if (inputStream != null && inputStream.available() > 0) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                    getScaleBitmapOptions(filePath, width, height));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据指定的宽高设置相关参数，避免出现OOM现象</br>
     *
     * @param filePath 图片得地址
     * @param width 期望图片的宽
     * @param height 期望图片的高
     * @return BitmapFactory.Options对象
     */
    private static BitmapFactory.Options getScaleBitmapOptions(String filePath, int width,
        int height) {
        InputStream inputStream = getBitmapStream(filePath);
        if (inputStream == null) {
            return null;
        }
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(inputStream, null, bmpFactoryOptions);

            int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / height);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / width);

            /*
             * If both of the ratios are greater than 1, one of the sides of the
             * image is greater than the screen
             */
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    // Height ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    // Width ratio is larger, scale according to it
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }

            // Decode it for real
            bmpFactoryOptions.inJustDecodeBounds = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭java 层的stream
            closeInputStream(inputStream);
        }

        return bmpFactoryOptions;
    }

    /**
     * 关闭输入流</br>
     *
     * @param inputStream 输入流
     */
    private static void closeInputStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url地址获取图片本地Stream</br>
     *
     * @param filePath 图片的地址
     * @return 本地图片的Stream，否则返回null
     */
    private static InputStream getBitmapStream(String filePath) {
        InputStream is = null;
        try {
            try {
                is = new FileInputStream(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (is == null || is.available() <= 0) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("BitmapUtil", "读取图片流出错" + e.toString());
        }
        return is;
    }
    
    public static File saveBitmap(String path,String bitName,Bitmap mBitmap){

        FileOutputStream fOut = null;
        try {
            File file = new File(path);
            if (!file.exists()) file.mkdirs();

            File f = new File(file.getAbsolutePath(), bitName + ".jpg");

            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            fOut.flush();
            mBitmap.recycle();

            return f;
        } catch (Exception e) {
            return null;
        } finally {
            IOUtil.closeQuietly(fOut);
        }
    }

}
