package com.bobomee.android.androidanimations.contenttransition;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class TransitionBActivity extends BaseActivity {

    @BindView(R.id.target_textView) TextView mTargetTextView;
    @BindView(R.id.target_imageView) ImageView mTargetImageView;
    @BindView(R.id.target_chrome_imageView) ImageView mTargetChromeImageView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityoptions_layout_target);
        ButterKnife.bind(this);
    }


}
