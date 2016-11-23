package com.bobomee.android.androidanimations.view;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

/**
 * Created on 16/9/10.下午10:25.
 *
 * @author bobomee.
 * @description
 */
public class Custom3DAnimation extends Animation {
  private int mCenterWidth;
  private int mCenterHeight;
  private Camera mCamera = new Camera();
  private float mRotateY = 0.0f;

  @Override
  public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);
    setDuration(2000);// 设置默认时长
    setFillAfter(true);// 动画结束后保留状态
    setInterpolator(new BounceInterpolator());// 设置默认插值器
    mCenterWidth = width / 2;
    mCenterHeight = height / 2;
  }

  // 暴露接口-设置旋转角度
  public void setRotateY(float rotateY) {
    mRotateY = rotateY;
  }

  @Override
  protected void applyTransformation( float interpolatedTime, Transformation t) {
    final Matrix matrix = t.getMatrix();
    mCamera.save();
    mCamera.rotateY(mRotateY * interpolatedTime);// 使用Camera设置旋转的角度
    mCamera.getMatrix(matrix);// 将旋转变换作用到matrix上
    mCamera.restore();
    // 通过pre方法设置矩阵作用前的偏移量来改变旋转中心
    matrix.preTranslate(mCenterWidth, mCenterHeight);
    matrix.postTranslate(-mCenterWidth, -mCenterHeight);
  }
}
