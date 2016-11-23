package com.bobomee.android.androidanimations.view;

import android.graphics.Matrix;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created on 16/9/10.下午10:22.
 *
 * @author bobomee.
 * @description
 */
public class CustomAnimation extends Animation {
  private int mCenterWidth;
  private int mCenterHeight;

  @Override
  public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);
    setDuration(1000);// 设置默认时长
    setFillAfter(true);// 动画结束后保留状态
    setInterpolator(new AccelerateInterpolator());// 设置默认插值器
    mCenterWidth = width / 2;
    mCenterHeight = height / 2;
  }

  @Override
  protected void applyTransformation(float interpolatedTime, Transformation t) {
    final Matrix matrix = t.getMatrix();
    matrix.preScale(1, 1 - interpolatedTime, mCenterWidth, mCenterHeight);
  }
}
