package com.bobomee.android.designpatterns.bridge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/9/4.下午10:37.
 *
 * @author bobomee.
 * @description:
 */
public class BridgeActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }


  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    Ordinary ordinary = new Ordinary();
    Sugar sugar = new Sugar();

    LargeCoffee largeCoffee = new LargeCoffee(ordinary);
    largeCoffee.makeCoffee();

    SmallCoffee smallCoffee = new SmallCoffee(ordinary);
    smallCoffee.makeCoffee();

    LargeCoffee largeCoffee1 = new LargeCoffee(sugar);
    largeCoffee1.makeCoffee();

    SmallCoffee smallCoffee1 = new SmallCoffee(sugar);
    smallCoffee1.makeCoffee();
  }
}
