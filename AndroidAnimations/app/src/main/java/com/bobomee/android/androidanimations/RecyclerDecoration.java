package com.bobomee.android.androidanimations;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created on 16/9/10.下午2:45.
 *
 * @author bobomee.
 * @description
 */
public class RecyclerDecoration extends RecyclerView.ItemDecoration {
  private Drawable mDivider;
  private int mOrientation;

  //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
  public static final int[] ATRRS = new int[] {
      android.R.attr.listDivider
  };

  public RecyclerDecoration(Context context, int orientation) {
    final TypedArray ta = context.obtainStyledAttributes(ATRRS);
    this.mDivider = ta.getDrawable(0);
    ta.recycle();
    setOrientation(orientation);
  }

  //设置屏幕的方向
  public void setOrientation(int orientation) {
    if (orientation != OrientationHelper.HORIZONTAL && orientation != OrientationHelper.VERTICAL) {
      throw new IllegalArgumentException("invalid orientation");
    }
    mOrientation = orientation;
  }

  @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    if (mOrientation == OrientationHelper.HORIZONTAL) {
      drawVerticalLine(c, parent, state);
    } else {
      drawHorizontalLine(c, parent, state);
    }
  }

  //画横线, 这里的parent其实是显示在屏幕显示的这部分
  public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();
    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);

      //获得child的布局信息
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = top + mDivider.getIntrinsicHeight();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
      //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
    }
  }

  //画竖线
  public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
    int top = parent.getPaddingTop();
    int bottom = parent.getHeight() - parent.getPaddingBottom();
    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);

      //获得child的布局信息
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int left = child.getRight() + params.rightMargin;
      final int right = left + mDivider.getIntrinsicWidth();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
    }
  }

  //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    if (mOrientation == OrientationHelper.HORIZONTAL) {
      //画横线，就是往下偏移一个分割线的高度
      outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    } else {
      //画竖线，就是往右偏移一个分割线的宽度
      outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
    }
  }
}
