package com.bobomee.android.designpatterns.observer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/20.下午11:45.
 *
 * @author bobomee.
 * @description:
 */
public class ObservableActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);

  }

  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    ISubject subject = new ConcreteSubject();

    IObserver observer1 = new ConcreteObserver1(mActivity);
    IObserver observer2 = new ConcreteObserver2(mActivity);

    //regist observer
    subject.registerObserver(observer1);
    subject.registerObserver(observer2);

    //update observer
    subject.notify("notify all");
  }
}
