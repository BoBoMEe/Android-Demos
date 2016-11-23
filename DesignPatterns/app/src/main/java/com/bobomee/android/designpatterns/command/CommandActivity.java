package com.bobomee.android.designpatterns.command;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.R;

/**
 * Created on 16/8/19.下午10:42.
 *
 * @author bobomee.
 * @description:
 */
public class CommandActivity extends AppCompatActivity {

  @BindView(R.id.btn_click) Button mBtnClick;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.btn_layout);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btn_click)
  public void setBtnClick(){
    //创建接收者对象那个
    Receiver receiver = new Receiver();
    //根据接收者对象构建命令对象
    Command command = new ConcreteCommand(receiver);
    //根据命令对象那个构建请求者对象
    Invoker invoker = new Invoker(command);
    //执行请求方法
    invoker.action();
  }
}
