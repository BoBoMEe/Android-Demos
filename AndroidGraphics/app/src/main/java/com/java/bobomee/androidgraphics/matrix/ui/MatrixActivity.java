package com.java.bobomee.androidgraphics.matrix.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.java.bobomee.androidgraphics.R;

public class MatrixActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.matrix_navigatino_layout);
  }

  public void map(View v) {
    start(MatrixMapActivity.class);
  }

  public void poly(View v) {
    start(MatrixSetPolyActivity.class);
  }

  public void rect(View v) {
    start(MatrixSetRectActivity.class);
  }

  private void start(Class c) {
    Intent intent = new Intent(this, c);
    startActivity(intent);
  }
}
