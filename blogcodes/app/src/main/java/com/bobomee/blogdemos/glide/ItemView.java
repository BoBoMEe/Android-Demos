package com.bobomee.blogdemos.glide;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bobomee.blogdemos.R;

/**
 * Created by bobomee on 16/4/26.
 */
public class ItemView extends LinearLayout {

    ImageView imageView;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.glide_item_view, this);

        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setImageView(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

}
