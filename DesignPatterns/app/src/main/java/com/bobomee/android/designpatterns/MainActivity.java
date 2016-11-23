package com.bobomee.android.designpatterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.designpatterns.abstactfactory.AbstractFactoryActivity;
import com.bobomee.android.designpatterns.adapter.AdapterActivity;
import com.bobomee.android.designpatterns.bridge.BridgeActivity;
import com.bobomee.android.designpatterns.chain.ChainActivity;
import com.bobomee.android.designpatterns.command.CommandActivity;
import com.bobomee.android.designpatterns.composite.CompositeActivity;
import com.bobomee.android.designpatterns.decorator.DecoratorActivity;
import com.bobomee.android.designpatterns.facade.FacadeActivity;
import com.bobomee.android.designpatterns.factorymethod.FactoryMethodActivity;
import com.bobomee.android.designpatterns.flyweight.FlyweightActivity;
import com.bobomee.android.designpatterns.interpreter.InterpreterActivity;
import com.bobomee.android.designpatterns.iterator.IteratorActivity;
import com.bobomee.android.designpatterns.mediator.MediatorActivity;
import com.bobomee.android.designpatterns.memento.NoteActivity;
import com.bobomee.android.designpatterns.observer.ObservableActivity;
import com.bobomee.android.designpatterns.prototype.CloneActivity;
import com.bobomee.android.designpatterns.proxy.ProxyActivity;
import com.bobomee.android.designpatterns.state.StateActivity;
import com.bobomee.android.designpatterns.stragety.TranficCal;
import com.bobomee.android.designpatterns.template.TemplateActivity;
import com.bobomee.android.designpatterns.visitor.VisitorAcitivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  private Class[] mClasses;
  private String[] mStrings;
  private MainActivity mMainActivity;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mMainActivity = this;

    initDatas();
    initViews();
  }

  private void initViews() {

    mRecyclerView.setLayoutManager(new LinearLayoutManager(mMainActivity));
    mRecyclerView.setAdapter(new CommonAdapter<String>(mMainActivity, android.R.layout.simple_list_item_1,
        new ArrayList<String>(Arrays.asList(mStrings))) {
      @Override protected void convert(ViewHolder holder, String s, final int position) {

        TextView textView = (TextView) holder.itemView.findViewById(android.R.id.text1);
        textView.setText(s);

        holder.setOnClickListener(android.R.id.text1, new View.OnClickListener() {
          @Override public void onClick(View v) {
            startActivity(new Intent(mMainActivity,mClasses[position]));
          }
        });
      }
    });
  }

  private void initDatas() {

    mClasses = new Class[] {
        AdapterActivity.class, CloneActivity.class, TranficCal.class, StateActivity.class, ChainActivity.class,
        CommandActivity.class, NoteActivity.class, ObservableActivity.class, IteratorActivity.class,
        TemplateActivity.class, VisitorAcitivity.class, MediatorActivity.class, ProxyActivity.class,
        CompositeActivity.class, DecoratorActivity.class, FlyweightActivity.class,
        FacadeActivity.class, BridgeActivity.class, FactoryMethodActivity.class,
        AbstractFactoryActivity.class, InterpreterActivity.class
    };
    mStrings = new String[] {
        "Adapter模式使用实例", "原型模式使用实例", "策略模式使用实例", "状态模式使用实例", "责任链模式实例", "命令模式实例", "备忘录模式实例",
        "观察者模式实例", "迭代器模式使用实例", "模板方法设计模式", "访问者模式使用实例", "中介者模式实例", "代理模式使用实例", "组合模式使用实例",
        "装饰者模式使用实例", "享元模式使用实例", "门面模式使用实例", "桥接模式使用实例","工厂方法模式","抽象工厂模式","解释器模式"
    };
  }
}
