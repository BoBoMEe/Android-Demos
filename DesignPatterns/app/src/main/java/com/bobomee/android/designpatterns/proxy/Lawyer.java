package com.bobomee.android.designpatterns.proxy;

/**
 * Created on 16/8/27.下午10:44.
 *
 * @author bobomee.
 * @description:
 */
public class Lawyer implements ILawsuit {
  private ILawsuit mILawsuit;//持有一个具体的被代理者的对象

  public Lawyer(ILawsuit _ILawsuit) {
    mILawsuit = _ILawsuit;
  }

  @Override public void submit() {
    mILawsuit.submit();
  }

  @Override public void burden() {
    mILawsuit.burden();
  }

  @Override public void defend() {
    mILawsuit.defend();
  }

  @Override public void finish() {
    mILawsuit.finish();
  }
}
