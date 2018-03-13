package com.java.bobomee.androidgraphics.matrix.ui;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.java.bobomee.androidgraphics.R;
import java.util.Arrays;

public class MatrixMapActivity extends AppCompatActivity {

  private static final String TAG = "MatrixMapActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matrix_map_layout);

    testMapPoints();

    testMapRadius();

    testMapRect();

    testMapVectors();
  }

  private void testMapVectors() {
    float[] src = new float[] { 1000, 800 };
    float[] dst = new float[2];

    // 构造一个matrix
    Matrix matrix = new Matrix();
    matrix.setScale(0.5f, 1f);
    matrix.postTranslate(100, 100);

    // 计算向量, 不受位移影响
    matrix.mapVectors(dst, src);
    Log.i(TAG, "mapVectors: " + Arrays.toString(dst));

    // 计算点
    matrix.mapPoints(dst, src);
    Log.i(TAG, "mapPoints: " + Arrays.toString(dst));
  }

  private void testMapRect() {
    RectF rect = new RectF(400, 400, 1000, 800);

    // 构造一个matrix
    Matrix matrix = new Matrix();
    matrix.setScale(0.5f, 1f);
    matrix.postSkew(1, 0);

    Log.i(TAG, "mapRadius: " + rect.toString());

    boolean result = matrix.mapRect(rect);

    Log.i(TAG, "mapRadius: " + rect.toString());
    Log.e(TAG, "isRect: " + result);
  }

  private void testMapRadius() {
    float radius = 100;
    float result = 0;

    // 构造一个matrix，x坐标缩放0.5
    Matrix matrix = new Matrix();
    matrix.setScale(0.5f, 1f);

    Log.i(TAG, "mapRadius: " + radius);

    result = matrix.mapRadius(radius);

    Log.i(TAG, "mapRadius: " + result);
  }

  private void testMapPoints() {
    // 初始数据为三个点 (0, 0) (80, 100) (400, 300)
    float[] pts = new float[] { 0, 0, 80, 100, 400, 300 };

    // 构造一个matrix，x坐标缩放0.5
    Matrix matrix = new Matrix();
    matrix.setScale(0.5f, 1f);

    // 输出pts计算之前数据
    Log.i(TAG, "before: " + Arrays.toString(pts));

    // 调用map方法计算
    matrix.mapPoints(pts);

    // 输出pts计算之后数据
    Log.i(TAG, "after : " + Arrays.toString(pts));
  }
}
