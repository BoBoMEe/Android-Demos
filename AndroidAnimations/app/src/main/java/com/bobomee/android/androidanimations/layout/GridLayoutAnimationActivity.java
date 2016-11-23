package com.bobomee.android.androidanimations.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.GridLayoutAnimationController;
import android.widget.Button;
import android.widget.GridView;
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
 * Created on 16/9/8.&#x4e0a;&#x5348;7:23.
 *
 * @author bobomee.
 * @description sss
 */
public class GridLayoutAnimationActivity extends BaseActivity {

  @BindView(R.id.add) Button mAdd;
  @BindView(R.id.grid_view) GridView mGridView;
  private CommonAdapter<String> mCommonAdapter;

  List<String> mStrings = new ArrayList<>();
  private int mInitPos;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout_animation_grid);
    ButterKnife.bind(this);

    initView();
  }

  private void initView() {

    initData();

    mGridView.setAdapter(mCommonAdapter =
        new CommonAdapter<String>(mActivity, R.layout.item_main, mStrings) {
          @Override protected void convert(ViewHolder viewHolder, String item, int position) {
            viewHolder.setText(R.id.text_name, item);
          }
        });

    initAnimation();
  }

  private void initData() {

    for (int i = 0; i < 10; ++i) {
      mStrings.add(String.valueOf(++mInitPos) + "个条目被添加");
    }
  }

  @OnClick(R.id.add) public void setAdd() {
    initData();

    mCommonAdapter.notifyDataSetChanged();
  }

  private void initAnimation() {
    Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.slide_in_from_left);
    GridLayoutAnimationController gridLayoutAnimationController =
        new GridLayoutAnimationController(animation);
    gridLayoutAnimationController.setColumnDelay(0.75f);
    gridLayoutAnimationController.setRowDelay(0.5f);
    gridLayoutAnimationController.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP
        | GridLayoutAnimationController.DIRECTION_RIGHT_TO_LEFT);
    gridLayoutAnimationController.setDirectionPriority(
        GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP);
    gridLayoutAnimationController.setInterpolator(new DecelerateInterpolator());

    mGridView.setLayoutAnimation(gridLayoutAnimationController);
    mGridView.startLayoutAnimation();
  }
}
