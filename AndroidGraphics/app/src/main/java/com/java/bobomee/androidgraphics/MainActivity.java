package com.java.bobomee.androidgraphics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.java.bobomee.androidgraphics.matrix.ui.MatrixActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void matrix(View v) {
    start(MatrixActivity.class);
  }

  private void start(Class c) {
    Intent intent = new Intent(this, c);
    startActivity(intent);
  }
}
