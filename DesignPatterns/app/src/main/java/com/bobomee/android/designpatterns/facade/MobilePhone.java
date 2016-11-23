package com.bobomee.android.designpatterns.facade;

import android.util.Log;

/**
 * Created on 16/9/1.下午11:49.
 *
 * @author bobomee.
 * @description:
 */
public class MobilePhone {
  private static final String TAG = "MobilePhone";

  private Phone mPhone = new PhoneImpl();
  private Camera mCamera = new CameraImpl();

  public void dail() {
    mPhone.dail();
  }

  public void videoChat() {
    Log.d(TAG, "videoChat: -->视频聊天");
    mCamera.open();
    mPhone.dail();
  }

  public void hangUp() {
    mPhone.hangup();
  }

  public void takePhoto() {
    mCamera.open();
    mCamera.takePhoto();
  }

  public void closeCamera() {
    mCamera.close();
  }
}
