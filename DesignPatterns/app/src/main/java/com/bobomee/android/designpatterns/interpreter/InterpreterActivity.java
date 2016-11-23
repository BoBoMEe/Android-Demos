package com.bobomee.android.designpatterns.interpreter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/9/6.下午11:39.
 *
 * @author bobomee.
 * @description:
 */
public class InterpreterActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;
  private static final String TAG = "InterpreterActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    Calculator calculator = new Calculator("123+124+125+126");
    Log.d(TAG, "setBtnClick: -->" + calculator.calculate());
  }
}
