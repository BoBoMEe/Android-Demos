package com.java.bobomee.androidgraphics.matrix.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.java.bobomee.androidgraphics.R;

public class MatrixSetPolyToPoly extends View {

  private Bitmap mBitmap;
  private Matrix mPolyMatrix;

  public MatrixSetPolyToPoly(Context context) {
    this(context, null);
  }

  public MatrixSetPolyToPoly(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MatrixSetPolyToPoly(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MatrixSetPolyToPoly(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demo);

    mPolyMatrix = new Matrix();

    float[] src = {
        0, 0,                                    // 左上
        mBitmap.getWidth(), 0,                          // 右上
        mBitmap.getWidth(), mBitmap.getHeight(),        // 右下
        0, mBitmap.getHeight()
    };                        // 左下

    float[] dst = {
        0, 0,                                    // 左上
        mBitmap.getWidth(), 400,                        // 右上
        mBitmap.getWidth(), mBitmap.getHeight() - 200,  // 右下
        0, mBitmap.getHeight()
    };                        // 左下

    // 核心要点
    mPolyMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1); // src.length >> 1 为位移运算 相当于处以2

    // 此处为了更好的显示对图片进行了等比缩放和平移(图片本身有点大)
    mPolyMatrix.postScale(0.26f, 0.26f);
    mPolyMatrix.postTranslate(0, 200);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawBitmap(mBitmap, mPolyMatrix, null);
  }
}
