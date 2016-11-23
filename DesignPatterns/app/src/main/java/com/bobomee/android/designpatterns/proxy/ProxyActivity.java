package com.bobomee.android.designpatterns.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;
import java.lang.reflect.Proxy;

/**
 * Created on 16/8/27.下午10:47.
 *
 * @author bobomee.
 * @description:
 */
public class ProxyActivity extends BaseActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click) public void setBtnClick() {
    //staticProxy();
    dynamicProxy();
  }

  private void staticProxy() {
    ILawsuit xiaomin = new XiaoMin();
    ILawsuit lawyer = new Lawyer(xiaomin);
    lawyer.submit();
    lawyer.burden();
    lawyer.defend();
    lawyer.finish();
  }

  private void dynamicProxy() {
    ILawsuit xiaomin = new XiaoMin();
    DynamicProxy dynamicProxy = new DynamicProxy(xiaomin);
    ClassLoader classLoader = xiaomin.getClass().getClassLoader();
    ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(classLoader, new Class[] {
        ILawsuit.class
    }, dynamicProxy);

    lawyer.submit();
    lawyer.burden();
    lawyer.defend();
    lawyer.finish();
  }
}
