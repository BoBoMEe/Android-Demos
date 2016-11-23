package com.bobomee.android.designpatterns.factorymethod;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/9/6.上午12:03.
 *
 * @author bobomee.
 * @description:
 */
public class FactoryMethodActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    //fa();
    rfa();
  }

  private void rfa() {

    RFactory rFactory = new RConcreteFactory();
    Product product = rFactory.createProduct(ConcreteProductA.class);
    product.method();
  }

  private void fa() {
    Factory factory = new ConcreteFactory();
    Product product = factory.createProduct();
    product.method();
  }
}
