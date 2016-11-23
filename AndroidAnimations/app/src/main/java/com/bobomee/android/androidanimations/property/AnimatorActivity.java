package com.bobomee.android.androidanimations.property;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.androidanimations.BaseActivity;
import com.bobomee.android.androidanimations.R;

/**
 * Created on 16/9/10.下午10:35.
 *
 * @author bobomee.
 * @description
 */
public class AnimatorActivity extends BaseActivity {

  @BindView(R.id.value_animator) Button mValueAnimator;
  @BindView(R.id.object_animator) Button mObjectAnimator;
  @BindView(R.id.animator_set) Button mAnimatorSet;
  @BindView(R.id.animator_animate) Button mAnimatorAnimate;
  private static final String TAG = "AnimatorActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.property_animator_layout);
    ButterKnife.bind(this);
  }

  @OnClick({
      R.id.value_animator, R.id.object_animator, R.id.animator_set,R.id.animator_animate
  }) public void click(View _view) {
    switch (_view.getId()) {
      case R.id.value_animator:
        valueAnimator(_view);
        break;
      case R.id.object_animator:
        //objectAnimator1(_view);
        objectAnimator2(_view);
        break;
      case R.id.animator_set:
        animatorSet(_view);
        break;
      case R.id.animator_animate:
        animatorAnimate(_view);
        break;
    }
  }

  private void objectAnimator2(View _view) {
    ObjectAnimator animator = ObjectAnimator.ofFloat(_view, "translationX", 300);
    animator.setDuration(1000);
    animator.start();
  }

  private void animatorAnimate(View _view) {
    _view.animate().alpha(0).y(10).setDuration(2000)
        .setListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {
            Log.d(TAG, "onAnimationStart: ");
          }

          @Override public void onAnimationEnd(Animator animation) {
            Log.d(TAG, "onAnimationEnd: ");
          }

          @Override public void onAnimationCancel(Animator animation) {
            Log.d(TAG, "onAnimationCancel: ");
          }

          @Override public void onAnimationRepeat(Animator animation) {
            Log.d(TAG, "onAnimationRepeat: ");
          }
        });
  }

  private void animatorSet(View _view) {
    // 获取屏幕宽度
    int maxWidth = getWindowManager().getDefaultDisplay().getWidth();
    // 将目标view进行包装
    ViewWrapper wrapper = new ViewWrapper(_view, maxWidth);
    // 将xml转化为ObjectAnimator对象
    AnimatorSet animatorSet =
        (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animator_set);
    // 设置动画的目标对象为包装后的view
    animatorSet.setTarget(wrapper);
    // 启动动画
    animatorSet.start();
  }

  private void objectAnimator1(View _view) {
    // 获取屏幕宽度
    int maxWidth = getWindowManager().getDefaultDisplay().getWidth();
    // 将目标view进行包装
    ViewWrapper wrapper = new ViewWrapper(_view, maxWidth);
    // 将xml转化为ObjectAnimator对象
    ObjectAnimator objectAnimator =
        (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
    // 设置动画的目标对象为包装后的view
    objectAnimator.setTarget(wrapper);
    // 启动动画
    objectAnimator.start();
  }

  private void valueAnimator(final View _view) {
    final int maxWidth = getWindowManager().getDefaultDisplay().getWidth();
    ValueAnimator valueAnimator =
        (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.value_animator);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animator) {
        // 当前动画值，即为当前宽度比例值
        int currentValue = (Integer) animator.getAnimatedValue();
        // 根据比例更改目标view的宽度
        _view.getLayoutParams().width = maxWidth * currentValue / 100;
        _view.requestLayout();
      }
    });
    valueAnimator.start();
  }
}
