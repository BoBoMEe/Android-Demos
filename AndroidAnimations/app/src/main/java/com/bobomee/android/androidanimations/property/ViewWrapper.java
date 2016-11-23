package com.bobomee.android.androidanimations.property;

import android.view.View;

/**
 * Created on 16/9/10.下午10:55.
 *
 * @author bobomee.
 * @description
 */
public class ViewWrapper {
  private View target; //目标对象
  private int maxWidth; //最长宽度值

  public ViewWrapper(View target, int maxWidth) {
    this.target = target;
    this.maxWidth = maxWidth;
  }

  public int getWidth() {
    return target.getLayoutParams().width;
  }

  public void setWidth(int widthValue) {
    //widthValue的值从100到20变化
    target.getLayoutParams().width = maxWidth * widthValue / 100;
    target.requestLayout();
  }
}
