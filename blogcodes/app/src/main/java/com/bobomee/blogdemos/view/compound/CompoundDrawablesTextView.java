package com.bobomee.blogdemos.view.compound;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bobomee.blogdemos.R;


public class CompoundDrawablesTextView extends TextView implements OnClickListener {

    /**
     * 各个方向的图片资源
     **/
    private Drawable mLeftDrawable;
    private Drawable mTopDrawable;
    private Drawable mRightDrawable;
    private Drawable mBottomDrawable;

    /**
     * 各个方向的图片资源是否被touch
     **/
    private boolean mIsLeftTouched;
    private boolean mIsTopTouched;
    private boolean mIsRightTouched;
    private boolean mIsBottomTouched;

    /**
     * Drawable可响应的点击区域x方向允许的误差，表示图片x方向的此范围内的点击都被接受
     **/
    private int mLazyX = 0;
    /**
     * Drawable可响应的点击区域y方向允许的误差，表示图片y方向的此范围内的点击都被接受
     **/
    private int mLazyY = 0;

    /**
     * 图片点击的监听器
     **/
    private DrawableClickListener mDrawableClickListener;

    public CompoundDrawablesTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CompoundDrawablesTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.Compound_Drawables_TextView_Style);
    }

    public CompoundDrawablesTextView(Context context) {
        this(context, null);
    }

    /**
     * 设置OnClickListener为当前的listener，即调用{@link CompoundDrawablesTextView#onClick(View)}函数
     **/
    private void init() {
        super.setOnClickListener(this);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mLeftDrawable = left;
        mTopDrawable = top;
        mRightDrawable = right;
        mBottomDrawable = bottom;
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 在event为actionDown时标记用户点击是否在相应的图片范围内
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            resetTouchStatus();
            if (mDrawableClickListener != null) {
                mIsLeftTouched = touchLeftDrawable(event);
                mIsTopTouched = touchTopDrawable(event);
                mIsRightTouched = touchRightDrawable(event);
                mIsBottomTouched = touchBottomDrawable(event);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        /**
         * 按照左上右下的顺序响应第一个点击范围内的Drawable
         */
        if (mDrawableClickListener != null) {
            if (mIsLeftTouched) {
                mDrawableClickListener.onClick(DrawableClickListener.DrawablePosition.LEFT);
            } else if (mIsTopTouched) {
                mDrawableClickListener.onClick(DrawableClickListener.DrawablePosition.TOP);
            } else if (mIsRightTouched) {
                mDrawableClickListener.onClick(DrawableClickListener.DrawablePosition.RIGHT);
            } else if (mIsBottomTouched) {
                mDrawableClickListener.onClick(DrawableClickListener.DrawablePosition.BOTTOM);
            } else {
                mDrawableClickListener.onClick(DrawableClickListener.DrawablePosition.TEXT);
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        mRightDrawable = null;
        mBottomDrawable = null;
        mLeftDrawable = null;
        mTopDrawable = null;
        super.finalize();
    }

    /**
     * 重置各个图片touch的状态
     */
    private void resetTouchStatus() {
        mIsLeftTouched = false;
        mIsTopTouched = false;
        mIsRightTouched = false;
        mIsBottomTouched = false;
    }

    /**
     * touch左边的Drawable
     *
     * @param event
     * @return 是否在touch范围内
     */
    private boolean touchLeftDrawable(MotionEvent event) {
        if (mLeftDrawable == null) {
            return false;
        }

        // 计算图片点击可响应的范围，计算方法见http://trinea.iteye.com/blog/1562388
        int drawHeight = mLeftDrawable.getIntrinsicHeight();
        int drawWidth = mLeftDrawable.getIntrinsicWidth();
        int topBottomDis = (mTopDrawable == null ? 0 : mTopDrawable.getIntrinsicHeight())
                - (mBottomDrawable == null ? 0 : mBottomDrawable.getIntrinsicHeight());
        double imageCenterY = 0.5 * (this.getHeight() + topBottomDis);
        Rect imageBounds = new Rect(this.getCompoundDrawablePadding() - mLazyX,
                (int) (imageCenterY - 0.5 * drawHeight - mLazyY), this.getCompoundDrawablePadding()
                + drawWidth + mLazyX,
                (int) (imageCenterY + 0.5 * drawHeight + mLazyY));
        return imageBounds.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * touch上边的Drawable
     *
     * @param event
     * @return 是否在touch范围内
     */
    private boolean touchTopDrawable(MotionEvent event) {
        if (mTopDrawable == null) {
            return false;
        }

        int drawHeight = mTopDrawable.getIntrinsicHeight();
        int drawWidth = mTopDrawable.getIntrinsicWidth();
        int leftRightDis = (mLeftDrawable == null ? 0 : mLeftDrawable.getIntrinsicWidth())
                - (mRightDrawable == null ? 0 : mRightDrawable.getIntrinsicWidth());
        double imageCenterX = 0.5 * (this.getWidth() + leftRightDis);
        Rect imageBounds = new Rect((int) (imageCenterX - 0.5 * drawWidth - mLazyX), this.getCompoundDrawablePadding()
                - mLazyY,
                (int) (imageCenterX + 0.5 * drawWidth + mLazyX), this.getCompoundDrawablePadding()
                + drawHeight + mLazyY);
        return imageBounds.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * touch右边的Drawable
     *
     * @param event
     * @return 是否在touch范围内
     */
    private boolean touchRightDrawable(MotionEvent event) {
        if (mRightDrawable == null) {
            return false;
        }

        int drawHeight = mRightDrawable.getIntrinsicHeight();
        int drawWidth = mRightDrawable.getIntrinsicWidth();
        int topBottomDis = (mTopDrawable == null ? 0 : mTopDrawable.getIntrinsicHeight())
                - (mBottomDrawable == null ? 0 : mBottomDrawable.getIntrinsicHeight());
        double imageCenterY = 0.5 * (this.getHeight() + topBottomDis);
        Rect imageBounds = new Rect(this.getWidth() - this.getCompoundDrawablePadding() - drawWidth - mLazyX,
                (int) (imageCenterY - 0.5 * drawHeight - mLazyY),
                this.getWidth() - this.getCompoundDrawablePadding() + mLazyX,
                (int) (imageCenterY + 0.5 * drawHeight + mLazyY));
        return imageBounds.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * touch下边的Drawable
     *
     * @param event
     * @return 是否在touch范围内
     */
    private boolean touchBottomDrawable(MotionEvent event) {
        if (mBottomDrawable == null) {
            return false;
        }

        int drawHeight = mBottomDrawable.getIntrinsicHeight();
        int drawWidth = mBottomDrawable.getIntrinsicWidth();
        int leftRightDis = (mLeftDrawable == null ? 0 : mLeftDrawable.getIntrinsicWidth())
                - (mRightDrawable == null ? 0 : mRightDrawable.getIntrinsicWidth());
        double imageCenterX = 0.5 * (this.getWidth() + leftRightDis);
        Rect imageBounds = new Rect((int) (imageCenterX - 0.5 * drawWidth - mLazyX), this.getHeight()
                - this.getCompoundDrawablePadding()
                - drawHeight - mLazyY,
                (int) (imageCenterX + 0.5 * drawWidth + mLazyX), this.getHeight()
                - this.getCompoundDrawablePadding()
                + mLazyY);
        return imageBounds.contains((int) event.getX(), (int) event.getY());
    }

    /**
     * 图片点击的监听器
     *
     * @author Trinea 2012-5-3 下午11:45:41
     */
    public interface DrawableClickListener {

        /**
         * 图片的位置
         */
        enum DrawablePosition {
            /**
             * 图片在TextView的左部
             **/
            LEFT,
            /**
             * 图片在TextView的上部
             **/
            TOP,
            /**
             * 图片在TextView的右部
             **/
            RIGHT,
            /**
             * 图片在TextView的底部
             **/
            BOTTOM,
            /**
             * 点击的是文字
             */
            TEXT
        }


        /**
         * 点击相应位置的响应函数
         *
         * @param position
         */
        void onClick(DrawablePosition position);
    }

    /**
     * 得到Drawable可响应的点击区域x方向允许的误差
     *
     * @return the lazyX
     */
    public int getLazyX() {
        return mLazyX;
    }

    /**
     * 设置Drawable可响应的点击区域x方向允许的误差
     *
     * @param lazyX
     */
    public void setLazyX(int lazyX) {
        this.mLazyX = lazyX;
    }

    /**
     * 得到Drawable可响应的点击区域y方向允许的误差
     *
     * @return the lazyY
     */
    public int getLazyY() {
        return mLazyY;
    }

    /**
     * 设置Drawable可响应的点击区域y方向允许的误差
     *
     * @param lazyY
     */
    public void setLazyY(int lazyY) {
        this.mLazyY = lazyY;
    }

    /**
     * 设置Drawable可响应的点击区域x和y方向允许的误差
     *
     * @param lazyX
     * @param lazyY
     */
    public void setLazy(int lazyX, int lazyY) {
        this.mLazyX = lazyX;
        this.mLazyY = lazyY;
    }

    /**
     * 设置图片点击的listener
     *
     * @param listener
     */
    public void setDrawableClickListener(DrawableClickListener listener) {
        this.mDrawableClickListener = listener;
    }
}