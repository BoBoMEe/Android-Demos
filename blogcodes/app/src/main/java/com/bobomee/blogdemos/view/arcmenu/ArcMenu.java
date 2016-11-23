package com.bobomee.blogdemos.view.arcmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.bobomee.blogdemos.R;


/**
 */
public class ArcMenu extends ViewGroup implements OnClickListener {

    private  final String TAG = getClass().getSimpleName();
    /**
     * 菜单的显示位置
     */
    private Position mPosition = Position.LEFT_TOP;

    /**
     * 菜单显示的半径，默认100dp
     */
    private int mRadius = 100;
    /**
     * 里屏幕边缘的距离
     */
    private int mMargin = 0;
    /**
     * 用户点击的按钮
     */
    private View mButton;
    /**
     * 当前ArcMenu的状态
     */
    private Status mCurrentStatus = Status.CLOSE;
    /**
     * 回调接口
     */
    private OnMenuItemClickListener onMenuItemClickListener;

    private StatusChange statusChange;

    /**
     * 状态的枚举类
     *
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 设置菜单现实的位置，四选1，默认右下
     *
     */
    public enum Position {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM, CENTER_BOTTOM;
    }

    public interface OnMenuItemClickListener {
        void onClick(View view, int pos);
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ArcMenu(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        // dp convert to px
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mRadius, getResources().getDisplayMetrics());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ArcMenu, defStyle, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.ArcMenu_arcPosition) {
                int val = a.getInt(attr, 0);
                switch (val) {
                    case 0:
                        mPosition = Position.LEFT_TOP;
                        break;
                    case 1:
                        mPosition = Position.RIGHT_TOP;
                        break;
                    case 2:
                        mPosition = Position.RIGHT_BOTTOM;
                        break;
                    case 3:
                        mPosition = Position.LEFT_BOTTOM;
                        break;
                    case 4:
                        mPosition = Position.CENTER_BOTTOM;
                }

            } else if (attr == R.styleable.ArcMenu_arcRadius) {// dp convert to px
                mRadius = a.getDimensionPixelSize(attr, mRadius);

            } else if (attr == R.styleable.ArcMenu_arcMargin) {
                mMargin = a.getDimensionPixelSize(attr, mMargin);
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            // mesure child
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
                    MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {

            layoutButton();
            int count = getChildCount();
            /**
             * 设置所有孩子的位置 例如(第一个为按钮)： 左上时，从左到右 ] 第2个：mRadius(sin0 , cos0)
             * 第3个：mRadius(sina ,cosa) 注：[a = Math.PI / 2 * (cCount - 1)]
             * 第4个：mRadius(sin2a ,cos2a) 第5个：mRadius(sin3a , cos3a) ...
             */
            for (int i = 0; i < count - 1; i++) {
                View child = getChildAt(i + 1);
                child.setVisibility(View.GONE);

                int cl;
                int ct;

                if (mPosition == Position.CENTER_BOTTOM) {
                    ct = (int) (mRadius * Math.sin(Math.PI / count * (i + 1)));
                    cl = (int) (mRadius * Math.cos(Math.PI / count * (i + 1)));
                } else {
                    cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2)
                            * i));
                    ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2)
                            * i));
                }


                // childview width
                int cWidth = child.getMeasuredWidth();
                // childview height
                int cHeight = child.getMeasuredHeight();
                if (mPosition == Position.LEFT_TOP) {
                    cl += mMargin;
                    ct += mMargin;
                }

                if (mPosition == Position.LEFT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct - mMargin;
                    cl += mMargin;
                }

                if (mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct - mMargin;
                    cl = getMeasuredWidth() - cWidth - cl - mMargin;
                }

                if (mPosition == Position.RIGHT_TOP) {
                    cl = getMeasuredWidth() - cWidth - cl - mMargin;
                    ct += mMargin;
                }

                if (mPosition == Position.CENTER_BOTTOM) {
                    cl = getMeasuredWidth() / 2 - cWidth / 2 - cl;
                    ct = getMeasuredHeight() - cHeight - ct - mMargin;
                }

                child.layout(cl, ct, cl + cWidth, ct + cHeight);

            }
        }
    }

    /**
     * 第一个子元素为按钮，为按钮布局且初始化点击事件
     */
    private void layoutButton() {
        View cButton = getChildAt(0);

        cButton.setOnClickListener(this);

        int l = 0;
        int t = 0;
        int width = cButton.getMeasuredWidth();
        int height = cButton.getMeasuredHeight();
        switch (mPosition) {
            case LEFT_TOP:
                l = t = mMargin;
                break;
            case LEFT_BOTTOM:
                l = mMargin;
                t = getMeasuredHeight() - height - mMargin;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width - mMargin;
                t = mMargin;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width - mMargin;
                t = getMeasuredHeight() - height - mMargin;
                break;
            case CENTER_BOTTOM:
                l = getMeasuredWidth() / 2 - width / 2;
                t = getMeasuredHeight() - height - mMargin;
                break;

        }
        Log.e(TAG, l + " , " + t + " , " + (l + width) + " , " + (t + height));
        cButton.layout(l, t, l + width, t + height);

    }

    /**
     * 为按钮添加点击事件
     */
    @Override
    public void onClick(View v) {
        if (mButton == null) {
            mButton = getChildAt(0);
        }

        rotateView(mCurrentStatus);

        toggleMenu(300);
    }


    public void rotateView(Status mCurrentStatus) {
        if (mCurrentStatus == Status.OPEN) {
            rotateView(mButton, 135f, 0f, 300);
        } else {
            rotateView(mButton, 0f, 135f, 300);
        }
    }

    /**
     * 按钮的旋转动画
     *
     * @param view
     * @param fromDegrees
     * @param toDegrees
     * @param durationMillis
     */
    private void rotateView(View view, float fromDegrees,
                            float toDegrees, int durationMillis) {
        RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillAfter(true);

        view.startAnimation(rotate);
    }

    public void toggleMenu(int durationMillis) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);

            int xflag = 1;
            int yflag = 1;

            if (mPosition == Position.LEFT_TOP
                    || mPosition == Position.LEFT_BOTTOM)
                xflag = -1;
            if (mPosition == Position.LEFT_TOP
                    || mPosition == Position.RIGHT_TOP)
                yflag = -1;
            int cl;
            int ct;

            if (mPosition == Position.CENTER_BOTTOM) {
                ct = (int) (mRadius * Math.sin(Math.PI / count * (i + 1)));
                cl = (int) (mRadius * Math.cos(Math.PI / count * (i + 1)));
            } else {
                // child left
                cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
                // child top
                ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));
            }

            AnimationSet animset = new AnimationSet(true);
            Animation animation = null;
                                            if (mCurrentStatus == Status.CLOSE) {// to open
                animset.setInterpolator(new OvershootInterpolator(2F));
                animation = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);
            } else {// to close
                animation = new TranslateAnimation(0f, xflag * cl, 0f, yflag
                        * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }

            animation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {

                    childView.clearAnimation();
                    if (mCurrentStatus == Status.CLOSE)
                        childView.setVisibility(GONE);

                }
            });

            animation.setFillAfter(true);
            animation.setDuration(durationMillis);


            final RotateAnimation rotate = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(durationMillis);
            rotate.setFillAfter(true);

            animset.addAnimation(rotate);
            animset.addAnimation(animation);
            childView.startAnimation(animset);

            final int index = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMenuItemClickListener != null)
                        onMenuItemClickListener.onClick(childView, index - 1);
                    menuItemAnin(index - 1);
                    rotateView(mCurrentStatus);
                    changeStatus();

                }
            });

        }
        changeStatus();
    }

    private void changeStatus() {

        if (mCurrentStatus == Status.CLOSE) {
            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArcMenu.this.onClick(v);
                }
            });
        } else {
            setClickable(false);
        }

        mCurrentStatus = (mCurrentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
        if (null != statusChange) statusChange.arcMenuStatus(mCurrentStatus);

    }

    /**
     * 开始菜单动画，点击的MenuItem放大消失，其他的缩小消失
     *
     * @param item
     */
    private void menuItemAnin(int item) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == item) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);

        }

    }

    /**
     * 缩小消失
     *
     * @param durationMillis
     * @return
     */
    private Animation scaleSmallAnim(int durationMillis) {
        Animation anim = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setDuration(durationMillis);
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 放大，透明度降低
     *
     * @param durationMillis
     * @return
     */
    private Animation scaleBigAnim(int durationMillis) {
        AnimationSet animationset = new AnimationSet(true);

        Animation anim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        animationset.addAnimation(anim);
        animationset.addAnimation(alphaAnimation);
        animationset.setDuration(durationMillis);
        animationset.setFillAfter(true);
        return animationset;
    }

    public Position getmPosition() {
        return mPosition;
    }

    public void setmPosition(Position mPosition) {
        this.mPosition = mPosition;
    }

    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public Status getmCurrentStatus() {
        return mCurrentStatus;
    }

    public void setmCurrentStatus(Status mCurrentStatus) {
        this.mCurrentStatus = mCurrentStatus;
    }

    public OnMenuItemClickListener getOnMenuItemClickListener() {
        return onMenuItemClickListener;
    }

    public void setOnMenuItemClickListener(
            OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }


    public interface StatusChange {
        void arcMenuStatus(Status mStatus);
    }

    public StatusChange getStatusChange() {
        return statusChange;
    }

    public void setStatusChange(StatusChange statusChange) {
        this.statusChange = statusChange;
    }
}
