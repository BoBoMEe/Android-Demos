package com.bobomee.android.androidanimations.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.androidanimations.BaseActivity;
import com.bobomee.android.androidanimations.R;

/**
 * Created on 16/9/10.下午6:11.
 *
 * @author bobomee.
 * @description
 */
public class DrawableAnimationActivity extends BaseActivity {

  @BindView(R.id.icon) ImageView mIcon;
  @BindView(R.id.icon1) ImageView mIcon1;
  @BindView(R.id.icon2) ImageView mIcon2;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drawable_animation);
    ButterKnife.bind(this);
    mIcon1.setImageResource(R.drawable.progress_drawable_white);
    mIcon2.setImageDrawable(createAnimationDrawable());
  }

  @OnClick(R.id.button1) public void buttonClick(Button v) {
    //xml(v);
    code(v);
  }

  private void code(Button _v) {
    AnimationDrawable animationDrawable = (AnimationDrawable) mIcon2.getDrawable();
    if (null != animationDrawable){
      if (!animationDrawable.isRunning()) {
        _v.setText("停止");
        animationDrawable.start();
      } else {
        _v.setText("开始");
        animationDrawable.stop();
      }
    }
  }

  private void xml(Button v) {
    Drawable drawable = mIcon1.getDrawable();

    if (null != drawable) {
      Animatable animatable = (Animatable) drawable;
      if (!animatable.isRunning()) {
        v.setText("停止");
        animatable.start();
      } else {
        v.setText("开始");
        animatable.stop();
      }
    }
  }

  private AnimationDrawable createAnimationDrawable() {
    AnimationDrawable mAnimationDrawable = new AnimationDrawable();
    for (int i = 1; i <= 12; i++) {
      int resourcesId = getResources().getIdentifier("ic_loading_white" + i, "drawable",
          mActivity.getPackageName());
      Drawable mDrawable = getResources().getDrawable(resourcesId);
      mAnimationDrawable.addFrame(mDrawable, 500);
    }
    mAnimationDrawable.setOneShot(false);

    return mAnimationDrawable;
  }
}
