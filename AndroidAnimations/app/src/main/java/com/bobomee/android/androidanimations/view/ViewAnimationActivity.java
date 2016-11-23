package com.bobomee.android.androidanimations.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
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
 * Created on 16/9/8.下午10:29.
 *
 * @author bobomee.
 * @description
 */
public class ViewAnimationActivity extends BaseActivity {

  @BindView(android.R.id.text1) TextView mText1;
  @BindView(android.R.id.list) ListView mList;

  List<Animation> mAnimations = new ArrayList<>();

  private static final String TAG = "ViewAnimationActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_animation);
    ButterKnife.bind(this);

    initDatas();

    initViews();

  }

  private void initDatas() {
    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        Log.d(TAG, "onAnimationStart: ");
      }

      @Override public void onAnimationEnd(Animation animation) {
        Log.d(TAG, "onAnimationEnd: ");
      }

      @Override public void onAnimationRepeat(Animation animation) {
        Log.d(TAG, "onAnimationRepeat: ");
      }
    };
    Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.fade_in_alpha);
    animation.setAnimationListener(animationListener);
    mAnimations.add(animation);
    mAnimations.add(AnimationUtils.loadAnimation(mActivity, R.anim.zoom_out_scale));
    mAnimations.add(AnimationUtils.loadAnimation(mActivity, R.anim.move_left_to_right_translate));
    mAnimations.add(AnimationUtils.loadAnimation(mActivity, R.anim.rotate_one_rotate));
    mAnimations.add(AnimationUtils.loadAnimation(mActivity, R.anim.move_and_scale_rotate_set));
    mAnimations.add(new CustomAnimation());
    Custom3DAnimation custom3DAnimation = new Custom3DAnimation();
    custom3DAnimation.setRotateY(5);
    mAnimations.add(custom3DAnimation);
    mAnimations.add(createTranslateAnimation());
  }

  private void initViews() {
    mList.setAdapter(
        new CommonAdapter<Animation>(mActivity, R.layout.item_main, mAnimations) {
          @Override
          protected void convert(ViewHolder viewHolder, final Animation item, int position) {
            viewHolder.setText(R.id.text_name, item.getClass().getSimpleName());
            viewHolder.setOnClickListener(R.id.text_name, new View.OnClickListener() {
              @Override public void onClick(View _view) {
                mText1.startAnimation(item);
              }
            });
          }
        });
  }

  private TranslateAnimation createTranslateAnimation() {
    TranslateAnimation translateAnimation =
        new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 100f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 100f);
    translateAnimation.setDuration(1000);
    return translateAnimation;
  }
}
