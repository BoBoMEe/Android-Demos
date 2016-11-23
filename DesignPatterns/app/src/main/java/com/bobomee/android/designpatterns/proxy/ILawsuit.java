package com.bobomee.android.designpatterns.proxy;

/**
 * Created on 16/8/27.下午10:39.
 *
 * @author bobomee.
 * @description:
 */
public interface ILawsuit {
  //提交申请
  void submit();

  //进行举证
  void burden();

  //开始辩护
  void defend();

  //诉讼完成
  void finish();
}
