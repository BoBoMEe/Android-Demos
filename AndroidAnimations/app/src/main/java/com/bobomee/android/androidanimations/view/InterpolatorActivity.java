package com.bobomee.android.androidanimations.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.androidanimations.BaseActivity;
import com.bobomee.android.androidanimations.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/9/10.下午3:50.
 *
 * @author bobomee.
 * @description
 */
public class InterpolatorActivity extends BaseActivity {


  @BindView(android.R.id.text1) TextView mText1;
  @BindView(android.R.id.list) ListView mList;

  List<Interpolator> mInterpolators = new ArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_animation);
    ButterKnife.bind(this);

    initDatas();

    initViews();
  }

  private void initDatas() {
    mInterpolators.add(new AccelerateDecelerateInterpolator());
    mInterpolators.add(new AccelerateInterpolator());
    mInterpolators.add(new AnticipateInterpolator());
    mInterpolators.add(new AnticipateOvershootInterpolator());
    mInterpolators.add(new BounceInterpolator());
    mInterpolators.add(new CycleInterpolator(1f));
    mInterpolators.add(new DecelerateInterpolator());
    mInterpolators.add(new LinearInterpolator());
    mInterpolators.add(new OvershootInterpolator());
    mInterpolators.add(new DeceAcceInterpolator());
  }

  private void initViews() {
    final Animation animation =
        AnimationUtils.loadAnimation(mActivity, R.anim.rotate_one_rotate);
    mList.setAdapter(
        new CommonAdapter<Interpolator>(mActivity, R.layout.item_main, mInterpolators) {
          @Override
          protected void convert(ViewHolder viewHolder, final Interpolator item, int position) {
            viewHolder.setText(R.id.text_name, item.getClass().getSimpleName());
            viewHolder.setOnClickListener(R.id.text_name, new View.OnClickListener() {
              @Override public void onClick(View _view) {
                animation.setInterpolator(item);
                mText1.startAnimation(animation);
              }
            });
          }
        });
  }

  private class DeceAcceInterpolator implements Interpolator{

    @Override public float getInterpolation(float input) {
      return ((4*input-2)*(4*input-2)*(4*input-2))/16f + 0.5f;
    }
  }
}
