package com.bobomee.android.androidanimations.contenttransition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.androidanimations.BaseActivity;
import com.bobomee.android.androidanimations.R;

/**
 * Created on 16/9/11.下午2:06.
 *
 * @author bobomee.
 * @description
 */
public class TransitionActivity extends BaseActivity {

    @BindView(R.id.original_imageView) ImageView mOriginalImageView;
    @BindView(R.id.original_textView) TextView mOriginalTextView;
    @BindView(R.id.chrome_imageView) ImageView mChromeImageView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityoptions_layout_main);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unchecked")
    public void buttonListener(View views) {
        switch (views.getId()) {
            case R.id.custom_button:
                customAnim();
                break;
            case R.id.scaleUp_button:
                scaleUpAnim(mOriginalImageView);
                break;
            case R.id.thumbnail_button:
                thumbNailScaleAnim(mChromeImageView);
                break;
            case R.id.scene_button:
                sceneTransitionAnimation(mChromeImageView);
                break;
            case R.id.scene_button1:
                sceneTransitionAnimation();
                break;
            default:
                break;
        }
    }

    private void sceneTransitionAnimation() {
        Pair<View, String> imagePair = Pair.create((View) mOriginalImageView, getString(R.string.transitionImageName));
        Pair<View, String> chromePair = Pair.create((View)mChromeImageView, getString(R.string.transitionChromeName));
        Pair<View, String> textPair = Pair.create((View)mOriginalTextView, getString(R.string.transitionTextName));

        ActivityOptionsCompat compat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, imagePair, textPair,chromePair);
        ActivityCompat.startActivity(this, new Intent(this, TransitionBActivity.class),
                compat.toBundle());
    }

    private void sceneTransitionAnimation(ImageView _chromeImageView) {
        ActivityOptionsCompat compat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        _chromeImageView, getString(R.string.transitionChromeName));
        ActivityCompat.startActivity(this, new Intent(this,
                TransitionBActivity.class), compat.toBundle());
    }

    private void thumbNailScaleAnim(ImageView _chromeImageView) {
        _chromeImageView.setDrawingCacheEnabled(true);
        _chromeImageView.buildDrawingCache();
        Bitmap bitmap = _chromeImageView.getDrawingCache();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(
                _chromeImageView, bitmap, 0, 0);
        // Request the activity be started, using the custom animation options.
        ActivityCompat.startActivity(this, new Intent(this, TransitionBActivity.class), options.toBundle());
        _chromeImageView.setDrawingCacheEnabled(false);
    }

    private void scaleUpAnim(ImageView _originalImageView) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(_originalImageView,
                0, 0, //拉伸开始的坐标
                _originalImageView.getMeasuredWidth(), _originalImageView.getMeasuredHeight());
        ActivityCompat.startActivity(this, new Intent(this, TransitionBActivity.class),
                compat.toBundle());
    }

    private void customAnim() {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
                R.anim.slide_bottom_in, R.anim.slide_bottom_out);
        ActivityCompat.startActivity(this,
                new Intent(this, TransitionBActivity.class), compat.toBundle());
    }
}