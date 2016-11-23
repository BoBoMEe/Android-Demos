package com.bobomee.android.designpatterns.template;

import android.util.Log;

/**
 * Created on 16/8/26.下午11:38.
 *
 * @author bobomee.
 * @description:
 */
public abstract class AbsComputer {

  private static final String TAG = "AbsComputer";
  protected void powerOn(){
    Log.d(TAG, "powerOn: "+ "开启电源");
  }
  protected void checkHardware(){
    Log.d(TAG, "checkHardware: "+ "硬件检查");
  }
  protected void loadOs(){
    Log.d(TAG, "loadOs: "+ "载入操作系统");
  }

  protected void login(){
    Log.d(TAG, "login: "+"进入系统");
  }

  public final void start(){
    Log.d(TAG, "start: ----->开机开始");
    powerOn();
    checkHardware();
    loadOs();
    loadOs();
    Log.d(TAG, "start: ---->开机结束");
  }
}
