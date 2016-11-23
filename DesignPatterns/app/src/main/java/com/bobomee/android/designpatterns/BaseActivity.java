package com.bobomee.android.designpatterns;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 16/8/25.下午11:01.
 *
 * @author bobomee.
 * @description:
 */
public class BaseActivity extends AppCompatActivity {

  public BaseActivity mActivity;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mActivity = this;
  }

}
