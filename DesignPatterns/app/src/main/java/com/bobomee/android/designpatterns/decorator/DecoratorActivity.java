package com.bobomee.android.designpatterns.decorator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/28.下午6:12.
 *
 * @author bobomee.
 * @description:
 */
public class DecoratorActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    Person person = new Boy();

    PersonCloth clothCheap = new CheapCloth(person);
    clothCheap.dressed();

    PersonCloth clothExpensive = new ExpensiveCloth(person);
    clothExpensive.dressed();
  }
}
