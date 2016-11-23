package com.bobomee.android.designpatterns.visitor;

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
 * Created on 16/8/27.下午1:34.
 *
 * @author bobomee.
 * @description:
 */
public class VisitorAcitivity extends BaseActivity {
  private static final String TAG = "VisitorAcitivity";

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    BusinessReport businessReport = new BusinessReport();

    Log.d(TAG, "给CEO看报表 ");
    businessReport.showReport(new CEOVisitor());

    Log.d(TAG, "给CTO看报表");
    businessReport.showReport(new CTOVisitor());

  }
}
