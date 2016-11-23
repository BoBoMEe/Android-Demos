package com.bobomee.android.designpatterns.stragety;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/16.下午11:19.
 *
 * @author bobomee.
 * @description:
 */
public class TranficCal extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;
  private Calculate mCalculate;

  public void setCalculate(Calculate calculate) {
    mCalculate = calculate;
  }

  public int calculate(int km) {
    return mCalculate.calculate(km);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);

  }

  @OnClick(R.id.btn_click) public void setBtnClick(){
    TranficCal tranficCal = new TranficCal();

    tranficCal.setCalculate(new BusCal());

    Toast.makeText(TranficCal.this, "BUS -- >" + tranficCal.calculate(3), Toast.LENGTH_SHORT)
        .show();
  }
}
