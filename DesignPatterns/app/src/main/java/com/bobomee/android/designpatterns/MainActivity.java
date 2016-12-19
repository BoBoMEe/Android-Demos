package com.bobomee.android.designpatterns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.designpatterns.abstactfactory.AbstractFactoryActivity;
import com.bobomee.android.designpatterns.adapter.AdapterActivity;
import com.bobomee.android.designpatterns.bridge.BridgeActivity;
import com.bobomee.android.designpatterns.builder.BuilderActivity;
import com.bobomee.android.designpatterns.chain.ChainActivity;
import com.bobomee.android.designpatterns.command.CommandActivity;
import com.bobomee.android.designpatterns.composite.CompositeActivity;
import com.bobomee.android.designpatterns.decorator.DecoratorActivity;
import com.bobomee.android.designpatterns.expand.Folder;
import com.bobomee.android.designpatterns.expand.FolderBinder;
import com.bobomee.android.designpatterns.expand.Item;
import com.bobomee.android.designpatterns.expand.ItemBinder;
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
import com.bobomee.android.designpatterns.singleton.SingletonActivity;
import com.bobomee.android.designpatterns.state.StateActivity;
import com.bobomee.android.designpatterns.stragety.TranficCal;
import com.bobomee.android.designpatterns.template.TemplateActivity;
import com.bobomee.android.designpatterns.visitor.VisitorAcitivity;
import com.bobomee.android.recyclerviewhelper.expandable.TreeNode;
import com.bobomee.android.recyclerviewhelper.expandable.TreeViewAdapter;
import com.bobomee.android.recyclerviewhelper.expandable.interfaces.OnTreeNodeClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  private MainActivity mMainActivity;
  private List<TreeNode> mTreeNodes = new ArrayList<>();
  private List<Class> mClasses = new ArrayList<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mMainActivity = this;

    initDatas();
    initViews();
  }

  private void initViews() {

    mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

    TreeViewAdapter treeViewAdapter =
        new TreeViewAdapter(mTreeNodes, Arrays.asList(new FolderBinder(), new ItemBinder()));
    mRecyclerView.setAdapter(treeViewAdapter);

    treeViewAdapter.addOnTreeNodeClickListener(new OnTreeNodeClickListener() {
      @Override public void onClick(TreeNode node, RecyclerView.ViewHolder holder) {
        if (node.isLeaf()) {
          int layoutPosition = holder.getLayoutPosition();
          Class aClass = mClasses.get(layoutPosition);
          Intent intent = new Intent(mActivity, aClass);
          startActivity(intent);
        }
      }
    });
  }

  private void initDatas() {

    String[] mCategorys = new String[] {
        "创建型",//
        "结构型", //
        "行为型"//
    };

    String[] create = {
        "工厂方法",//
        "抽象工厂", //
        "单例", //
        "构造者", //
        "原型"//
    };

    Class[] _create = {
        FactoryMethodActivity.class,//
        AbstractFactoryActivity.class, //
        SingletonActivity.class,//
        BuilderActivity.class,//
        CloneActivity.class, //
    };

    String[] structure = {
        "适配器", //
        "装饰器", //
        "代理", //
        "外观", //
        "桥接", //
        "组合", //
        "享元"//
    };

    Class[] _structure = {
        AdapterActivity.class, //
        DecoratorActivity.class, //
        ProxyActivity.class,//
        FacadeActivity.class, //
        BridgeActivity.class, //
        CompositeActivity.class,//
        FlyweightActivity.class,//
    };

    String[] behavior = {
        "策略", //
        "模板", //
        "观察者", //
        "迭代器", //
        "责任链", //
        "命令",//
        "备忘录",//
        "状态", //
        "访问者", //
        "中介者", //
        "解释器"//
    };
    Class[] _behavior = {
        TranficCal.class,//
        TemplateActivity.class,//
        ObservableActivity.class,//
        IteratorActivity.class,//
        ChainActivity.class,//
        CommandActivity.class,//
        NoteActivity.class, //
        StateActivity.class, //
        VisitorAcitivity.class,//
        MediatorActivity.class, //
        InterpreterActivity.class
    };

    ///////////
    TreeNode<Folder> ct = new TreeNode<>(new Folder(mCategorys[0]));
    for (String c : create) {
      TreeNode<Item> itemTreeNode = new TreeNode<>(new Item(c));
      ct.addChild(itemTreeNode);
    }
    mTreeNodes.add(ct);

    mClasses.add(Class.class);
    for (Class _c : _create) {
      mClasses.add(_c);
    }

    TreeNode<Folder> st = new TreeNode<>(new Folder(mCategorys[1]));
    for (String c : structure) {
      TreeNode<Item> itemTreeNode = new TreeNode<>(new Item(c));
      st.addChild(itemTreeNode);
    }
    mTreeNodes.add(st);

    mClasses.add(Class.class);
    for (Class _c : _structure) {
      mClasses.add(_c);
    }

    TreeNode<Folder> bt = new TreeNode<>(new Folder(mCategorys[2]));
    for (String c : behavior) {
      TreeNode<Item> itemTreeNode = new TreeNode<>(new Item(c));
      bt.addChild(itemTreeNode);
    }
    mTreeNodes.add(bt);

    mClasses.add(Class.class);
    for (Class _c : _behavior) {
      mClasses.add(_c);
    }

  }
}
