package com.bobomee.blogdemos.view.compound;

import android.os.Bundle;
import android.widget.Toast;

import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;


/**
 * Created by bobomee on 16/1/24.
 */
public class CompoundDrawablesTextViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compound_drawables_layout);
        CompoundDrawablesTextView textWithImage = (CompoundDrawablesTextView)this.findViewById(R.id.textWithImage);
        textWithImage.setDrawableClickListener(new ImageClickListener());
    }

    class ImageClickListener implements CompoundDrawablesTextView.DrawableClickListener {

        @Override
        public void onClick(DrawablePosition position) {
            switch (position) {
                case LEFT:
                    // 左边图片被点击的响应
                    Toast.makeText(CompoundDrawablesTextViewActivity.this, "left", Toast.LENGTH_SHORT).show();
                    break;
                case RIGHT:
                    // 右边图片被点击的响应
                    Toast.makeText(CompoundDrawablesTextViewActivity.this, "right", Toast.LENGTH_SHORT).show();
                    break;
                case BOTTOM:
                    // 底部图片被点击的响应
                    Toast.makeText(CompoundDrawablesTextViewActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    break;
                case TOP:
                    // 上边图片被点击的响应
                    Toast.makeText(CompoundDrawablesTextViewActivity.this, "top", Toast.LENGTH_SHORT).show();
                    break;
                case TEXT:
                    Toast.makeText(CompoundDrawablesTextViewActivity.this, "TEXT", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
