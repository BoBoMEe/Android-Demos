package com.bobomee.blogdemos.view.index;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bobomee.blogdemos.R;


public class IndexView extends View {
    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // 是否画出背景
    private boolean showBg = false;
    // 选中的项
    private int choose = -1;
    // 准备好的A~Z的字母数组
    public static String[] letters = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int width;
    private int height;
    private int textSize = 20;
    private int textColor = Color.BLACK;
    private int selectTextColor = Color.YELLOW;
    private int selectBackGround = Color.LTGRAY;
    private int singleHeight;

    // 构造方法
    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndexView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
        textSize = (int) array.getDimension(R.styleable.IndexView_indexTextSize,textSize);
        textColor = array.getColor(R.styleable.IndexView_indexTextColor,textColor);
        selectTextColor = array.getColor(R.styleable.IndexView_indexSelectTextColor,selectTextColor);
        selectBackGround = array.getColor(R.styleable.IndexView_indexSelectBackGround, selectBackGround);

        initPaint();

        array.recycle();
    }

    private void initPaint() {
        paint.setColor(textColor);
        // 设置字体格式
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        singleHeight = height/letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            // 画出背景
            canvas.drawColor(selectBackGround);
        }

        // 画字母
        for (int i = 0; i < letters.length; i++) {

            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.setColor(selectTextColor);
                paint.setFakeBoldText(true);
            }else{
                paint.setColor(textColor);
                paint.setFakeBoldText(false);
            }
            // 要画的字母的x,y坐标
            float posX = width / 2 - paint.measureText(letters[i]) / 2;
            float posY = i * singleHeight + singleHeight;
            // 画出字母
            canvas.drawText(letters[i], posX, posY, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        final int index = (int) (y / getHeight() * letters.length);
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listenner != null && index > 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                if (listenner != null) {
                    if (index <= 0) {
                        listenner.onTouchLetterChange(showBg, "A");
                    } else if (index > 0 && index < letters.length) {
                        listenner.onTouchLetterChange(showBg, letters[index]);
                    } else if (index >= letters.length) {
                        listenner.onTouchLetterChange(showBg, "Z");
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(boolean isTouched, String s);
    }

    public int getSelectBackGround() {
        return selectBackGround;
    }

    public void setSelectBackGround(int selectBackGround) {
        this.selectBackGround = selectBackGround;
        invalidate();
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public void setSelectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }
}
