package com.java.bobomee.androidgraphics.matrix.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import com.java.bobomee.androidgraphics.R;
import com.java.bobomee.androidgraphics.matrix.widget.SetPolyToPoly;

public class MatrixSetPolyActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.matrix_set_poly_layout);

    testPolyToPoly();
  }

  private void testPolyToPoly() {
    final SetPolyToPoly poly = (SetPolyToPoly) findViewById(R.id.poly);

    RadioGroup group = (RadioGroup) findViewById(R.id.group);
    assert group != null;
    group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()){
          case R.id.point0: poly.setTestPoint(0); break;
          case R.id.point1: poly.setTestPoint(1); break;
          case R.id.point2: poly.setTestPoint(2); break;
          case R.id.point3: poly.setTestPoint(3); break;
          case R.id.point4: poly.setTestPoint(4); break;
        }
      }
    });
  }
}
