package com.bobomee.android.designpatterns.prototype;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.designpatterns.BaseActivity;
import com.bobomee.android.designpatterns.R;
import java.util.ArrayList;

/**
 * Created on 16/8/8.上午12:11.
 *
 * @author bobomee.
 * @description:
 */
public class CloneActivity extends BaseActivity {

  private static final String TAG = "CloneActivity";

  @BindView(R.id.button) Button mButton;
  @BindView(R.id.button2) Button mButton2;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.clone_layout);
    ButterKnife.bind(this);
  }


  @OnClick(R.id.button)
  public void setButton(){

    CloneImpl cloneImpl = new CloneImpl();

    ArrayList<String> mList = new ArrayList<>();
    mList.add("Prototype");

    cloneImpl.setInt(1);
    cloneImpl.setList(mList);

    Log.d(TAG, "cloneImpl: --->" + cloneImpl);

    try {
      Object clone = cloneImpl.clone();

      CloneImpl cloneImpl1 = (CloneImpl) clone;

      Log.d(TAG, "cloneImpl1: ---->"+cloneImpl1);

      cloneImpl1.setInt(2);
      cloneImpl1.getList().add("Clone");

      Log.d(TAG, "clone 后 cloneImpl: --->" + cloneImpl);
      Log.d(TAG, "clone 后 cloneImpl1: ---->"+cloneImpl1);

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }


  @OnClick(R.id.button2)
  public void setButton2(){
    CloneImpl2 cloneImpl = new CloneImpl2();

    ArrayList<String> mList = new ArrayList<>();
    mList.add("Prototype");

    cloneImpl.setInt(1);
    cloneImpl.setList(mList);

    Log.d(TAG, "cloneImpl: --->" + cloneImpl);

    try {
      Object clone = cloneImpl.clone();

      CloneImpl2 cloneImpl1 = (CloneImpl2) clone;

      Log.d(TAG, "cloneImpl1: ---->"+cloneImpl1);


      cloneImpl1.setInt(2);
      cloneImpl1.getList().add("Clone");

      Log.d(TAG, "clone 后 cloneImpl: --->" + cloneImpl);
      Log.d(TAG, "clone 后 cloneImpl1: ---->"+cloneImpl1);

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

}

