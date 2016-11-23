package com.bobomee.android.androidanimations.layout;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.androidanimations.BaseActivity;
import com.bobomee.android.androidanimations.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/9/7.下午11:07.
 *
 * @author bobomee.
 * @description:
 */
public class LayoutAnimationActivity extends BaseActivity {

  @BindView(R.id.add) Button mAdd;
  @BindView(android.R.id.list) ListView mList;
  private CommonAdapter<String> mCommonAdapter;

  List<String> mStrings = new ArrayList<>();
  private int mInitPos;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_animation_list);
    ButterKnife.bind(this);

    initView();
  }

  private void initView() {

    initData();

    mList.setAdapter(mCommonAdapter =
        new CommonAdapter<String>(mActivity, R.layout.item_main, mStrings) {
          @Override protected void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.text_name, item);
          }
        });

    initAnimation();
    initTransition();
  }

  private void initTransition() {
    LayoutTransition mTransitioner = new LayoutTransition();
    mTransitioner.addTransitionListener(new LayoutTransition.TransitionListener() {
      @Override
      public void startTransition(LayoutTransition transition, ViewGroup container, View view,
          int transitionType) {

      }

      @Override
      public void endTransition(LayoutTransition transition, ViewGroup container, View view,
          int transitionType) {

      }
    });
    mList.setLayoutTransition(mTransitioner);
  }

  private void initAnimation() {
    Animation animation = AnimationUtils.loadAnimation(mActivity,R.anim.slide_in_from_left);
    LayoutAnimationController animationController = new LayoutAnimationController(animation,
         1f);
    animationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
    mList.setLayoutAnimation(animationController);
    animationController.setInterpolator(new AccelerateDecelerateInterpolator());
    mList.startLayoutAnimation();
  }

  private void initData() {

    for (int i = 0; i < 5; ++i) {
      mStrings.add(String.valueOf(++mInitPos)+"个条目被添加");
    }
  }

  @OnClick(R.id.add) public void setAdd() {
    initData();

    mCommonAdapter.notifyDataSetChanged();
  }
}
