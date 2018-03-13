package com.java.bobomee.androidgraphics.matrix.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.java.bobomee.androidgraphics.R;

public class MatrixSetRectToRect extends View {

  private Bitmap mBitmap;
  private Matrix mRectMatrix;
  private int mViewWidth, mViewHeight;

  public MatrixSetRectToRect(Context context) {
    this(context, null);
  }

  public MatrixSetRectToRect(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MatrixSetRectToRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MatrixSetRectToRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.demo);
    mRectMatrix = new Matrix();
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mViewWidth = w;
    mViewHeight = h;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    RectF src = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    RectF dst = new RectF(0, 0, mViewWidth, mViewHeight);

    // 核心要点
    mRectMatrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);

    // 根据Matrix绘制一个变换后的图片
    canvas.drawBitmap(mBitmap, mRectMatrix, new Paint());
  }
}
