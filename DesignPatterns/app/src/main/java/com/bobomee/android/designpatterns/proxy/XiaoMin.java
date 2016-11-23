package com.bobomee.android.designpatterns.proxy;

import android.util.Log;

/**
 * Created on 16/8/27.下午10:41.
 *
 * @author bobomee.
 * @description:
 */
public class XiaoMin implements ILawsuit {

  private static final String TAG = "XiaoMin";

  @Override public void submit() {
    Log.d(TAG, "submit: -->" + "老板拖欠工资,特此申请仲裁");
  }

  @Override public void burden() {
    Log.d(TAG, "burden: -->" + "小明提供了合同书和过去一年的工资流水");
  }

  @Override public void defend() {
    Log.d(TAG, "defend: -->" + "证据确凿,不需要辩护什么了...");
  }

  @Override public void finish() {
    Log.d(TAG, "finish: -->" + "诉讼成功!判决老板七日内结算工资");
  }
}
