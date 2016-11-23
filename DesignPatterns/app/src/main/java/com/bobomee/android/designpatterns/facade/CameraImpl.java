package com.bobomee.android.designpatterns.facade;

import android.util.Log;

/**
 * Created on 16/9/4.下午8:42.
 *
 * @author bobomee.
 * @description:
 */
public class CameraImpl implements Camera {

  private static final String TAG = "CameraImpl";

  @Override public void open() {
    Log.d(TAG, "open: -->打开相机");
  }

  @Override public void takePhoto() {
    Log.d(TAG, "takePhoto: -->拍照");
  }

  @Override public void close() {
    Log.d(TAG, "close: -->关闭相机");
  }
}
