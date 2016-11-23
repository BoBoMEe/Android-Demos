package com.bobomee.blogdemos.view.switcher3d;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bobomee.android.scrollloopviewpager.autoscrollviewpager.FixedSpeedScroller;
import com.bobomee.blogdemos.R;


/**
 * Created by bobomee on 16/3/5.
 */
public class Image3DSwitchView extends ViewGroup implements Handler.Callback {

    /**
     * 触摸的状态
     */
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    /**
     * 记录当前的触摸状态
     */
    private int mTouchState = TOUCH_STATE_REST;
    /**
     * 滚动到下一张图片的速度
     */
    private static final int SNAP_VELOCITY = 600;
    /**
     * 表示滚动到下一张图片这个动作
     */
    private static final int SCROLL_NEXT = 0;
    /**
     * 表示滚动到上一张图片这个动作
     */
    private static final int SCROLL_PREVIOUS = 1;
    /**
     * 表示滚动回原图片这个动作
     */
    private static final int SCROLL_BACK = 2;

    private int mImagePadding = 10;
    private float mWidthRatio = 0.8f;

    private VelocityTracker mVelocityTracker;
    private FixedSpeedScroller mScroller;
    /**
     * 图片滚动监听器，当图片发生滚动时回调这个接口
     */
    private OnImageSwitchListener mListener;

    /**
     * 记录被判定为滚动运动的最小滚动值
     */
    private int mTouchSlop;

    /**
     * 记录每张图片的宽度
     */
    private int mImageWidth;
    /**
     * 记录图片的总数量
     */
    private int mCount;
    /**
     * 记录当前显示图片的坐标
     */
    private int mCurrentImage;
    /**
     * 记录上次触摸的横坐标值
     */
    private float mLastMotionX;
    /**
     * 是否强制重新布局
     */
    private boolean forceToRelayout;
    private int[] mItems;


    public static final int DEFAULT_INTERVAL = 1500;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    /**
     * the message.what for scroll
     */
    public static final int SCROLL_WHAT = 0;
    /**
     * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL} *
     */
    private long interval = DEFAULT_INTERVAL;
    /**
     * auto scroll direction, default is {@link #RIGHT} *
     */
    private int direction = RIGHT;
    /**
     * whether stop auto scroll when touching, default is true *
     */
    private boolean stopScrollWhenTouch = true;
    /**
     * scroll factor for auto scroll animation, default is 1.0 *
     */
    private int autoScrollFactor = 450;
    /**
     * scroll factor for swipe scroll animation, default is 1.0
     **/
    private int swipeScrollFactor = 450;

    private Handler handler;
    private boolean isAutoScroll = false;
    private boolean isStopByTouch = false;
    private float downX = 0f;
    private float downY = 0f;

    public Image3DSwitchView(Context context) {
        this(context, null);
    }

    public Image3DSwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Image3DSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initFromAttributes(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Image3DSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        initFromAttributes(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        mScroller = new FixedSpeedScroller(context, interpolator, swipeScrollFactor);
        handler = new Handler(this);
    }

    private void initFromAttributes(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.Image3DSwitchView, defStyleAttr, defStyleRes);
        mImagePadding = a.getInt(R.styleable.Image3DSwitchView_switcherImagePadding, mImagePadding);
        mWidthRatio = a.getFloat(R.styleable.Image3DSwitchView_switcherImageRatio, mWidthRatio);
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed || forceToRelayout) {
            mCount = getChildCount();
            // 图片数量必须大于5，不然无法正常显示
            if (mCount < 5) {
                return;
            }
            // 控件宽度
            int mWidth = getMeasuredWidth();
            //记录控件高度
            int mHeight = getMeasuredHeight();
            // 每张图片的宽度设定为控件宽度的百分之六十
            mImageWidth = (int) (mWidth * mWidthRatio);

            if (mCurrentImage >= 0 && mCurrentImage < mCount) {
                mScroller.abortAnimation();
                setScrollX(0);
                //并排了5张图片，获取的是第一张图片的left
                int left = -mImageWidth * 2 + (mWidth - mImageWidth) / 2;
                // 分别获取每个位置上应该显示的图片下标
                int[] items = {getIndexForItem(1), getIndexForItem(2),
                        getIndexForItem(3), getIndexForItem(4),
                        getIndexForItem(5)};
                mItems = items;
                // 通过循环为每张图片设定位置
                for (int i = 0; i < items.length; i++) {
                    Image3DView childView = (Image3DView) getChildAt(items[i]);
                    childView.layout(left + mImagePadding, 0,
                            left + mImageWidth - mImagePadding, mHeight);
                    childView.setContainerWidth(mWidth);
                    childView.setImagePadding(mImagePadding);
                    childView.initImageViewBitmap();
                    left += mImageWidth;
                }
                refreshImageShowing();
            }
            forceToRelayout = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScroller.isFinished()) {
            if (mVelocityTracker == null) {
                mVelocityTracker = VelocityTracker.obtain();
            }
            mVelocityTracker.addMovement(event);
            int action = event.getAction();
            float x = event.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 记录按下时的横坐标
                    mLastMotionX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int disX = (int) (mLastMotionX - x);
                    mLastMotionX = x;
                    scrollBy(disX, 0);
                    // 当发生移动时刷新图片的显示状态
                    refreshImageShowing();
                    break;
                case MotionEvent.ACTION_UP:
                    mVelocityTracker.computeCurrentVelocity(1000);
                    int velocityX = (int) mVelocityTracker.getXVelocity();
                    if (shouldScrollToNext(velocityX)) {
                        // 滚动到下一张图
                        scrollToNext();
                    } else if (shouldScrollToPrevious(velocityX)) {
                        // 滚动到上一张图
                        scrollToPrevious();
                    } else {
                        // 滚动回当前图片
                        scrollBack();
                    }
                    if (mVelocityTracker != null) {
                        mVelocityTracker.recycle();
                        mVelocityTracker = null;
                    }
                    break;
            }
        }
        return true;
    }

    /**
     * 根据当前的触摸状态来决定是否屏蔽子控件的交互能力。
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE)
                && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }
        float x = ev.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int) Math.abs(mLastMotionX - x);
                if (xDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;
            case MotionEvent.ACTION_UP:
            default:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return mTouchState != TOUCH_STATE_REST;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            refreshImageShowing();
            postInvalidate();
        }
    }

    /**
     * 设置图片滚动的监听器，每当有图片滚动时会回调此接口。
     *
     * @param listener 图片滚动监听器
     */
    public void setOnImageSwitchListener(OnImageSwitchListener listener) {
        mListener = listener;
    }

    /**
     * 设置当前显示图片的下标，注意如果该值小于零或大于等于图片的总数量，图片则无法正常显示。
     *
     * @param currentImage 图片的下标
     */
    public void setCurrentImage(int currentImage) {
        mCurrentImage = currentImage;
        requestLayout();
    }

    /**
     * 滚动到下一张图片。
     */
    public void scrollToNext() {
        if (mScroller.isFinished()) {
            int disX = mImageWidth - getScrollX();
            checkImageSwitchBorder(SCROLL_NEXT);
            if (mListener != null) {
                mListener.onImageSwitch(mCurrentImage);
            }
            beginScroll(getScrollX(), 0, disX, 0, SCROLL_NEXT);
        }
    }

    /**
     * 滚动到上一张图片。
     */
    public void scrollToPrevious() {
        if (mScroller.isFinished()) {
            int disX = -mImageWidth - getScrollX();
            checkImageSwitchBorder(SCROLL_PREVIOUS);
            if (mListener != null) {
                mListener.onImageSwitch(mCurrentImage);
            }
            beginScroll(getScrollX(), 0, disX, 0, SCROLL_PREVIOUS);
        }
    }

    /**
     * 滚动回原图片。
     */
    public void scrollBack() {
        if (mScroller.isFinished()) {
            beginScroll(getScrollX(), 0, -getScrollX(), 0, SCROLL_BACK);
        }
    }

    /**
     * 回收所有图片对象，释放内存。
     */
    public void clear() {
        for (int i = 0; i < mCount; i++) {
            Image3DView childView = (Image3DView) getChildAt(i);
            childView.recycleBitmap();
        }
    }

    /**
     * 让控件中的所有图片开始滚动。
     */
    private void beginScroll(int startX, int startY, int dx, int dy,
                             final int action) {
        int duration = (int) (700f / mImageWidth * Math.abs(dx));
        mScroller.startScroll(startX, startY, dx, dy, duration);
        invalidate();
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (action == SCROLL_NEXT || action == SCROLL_PREVIOUS) {
                    forceToRelayout = true;
                    requestLayout();
                }
            }
        }, duration);
    }

    /**
     * 根据当前图片的下标和传入的item参数，来判断item位置上应该显示哪张图片。
     *
     * @param item 取值范围是1-5
     * @return 对应item位置上应该显示哪张图片。
     */
    private int getIndexForItem(int item) {
        int index = -1;
        index = mCurrentImage + item - 3;
        while (index < 0) {
            index = index + mCount;
        }
        while (index > mCount - 1) {
            index = index - mCount;
        }
        return index;
    }

    /**
     * 刷新所有图片的显示状态，包括当前的旋转角度。
     */
    private void refreshImageShowing() {
        for (int i = 0; i < mItems.length; i++) {
            Image3DView childView = (Image3DView) getChildAt(mItems[i]);
            childView.setRotateData(i, getScrollX());
            childView.invalidate();
        }
    }

    /**
     * 检查图片的边界，防止图片的下标超出规定范围。
     */
    private void checkImageSwitchBorder(int action) {
        if (action == SCROLL_NEXT && ++mCurrentImage >= mCount) {
            mCurrentImage = 0;
        } else if (action == SCROLL_PREVIOUS && --mCurrentImage < 0) {
            mCurrentImage = mCount - 1;
        }
    }

    /**
     * 判断是否应该滚动到下一张图片。
     */
    private boolean shouldScrollToNext(int velocityX) {
        return velocityX < -SNAP_VELOCITY || getScrollX() > mImageWidth / 2;
    }

    /**
     * 判断是否应该滚动到上一张图片。
     */
    private boolean shouldScrollToPrevious(int velocityX) {
        return velocityX > SNAP_VELOCITY || getScrollX() < -mImageWidth / 2;
    }

    /**
     * 图片滚动的监听器
     */
    public interface OnImageSwitchListener {

        /**
         * 当图片滚动时会回调此方法
         *
         * @param currentImage 当前图片的坐标
         */
        void onImageSwitch(int currentImage);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SCROLL_WHAT:
                if (isAutoScroll) {
                    mScroller.setScrollDurationFactor(autoScrollFactor);
                    scrollOnce();
                }
            default:
                break;
        }
        return false;
    }

    public void scrollOnce() {
        if (direction == LEFT) {
            scrollToPrevious();
        } else {
            scrollToNext();
        }
        sendScrollMessage(interval + mScroller.getDuration());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        float touchX = ev.getX();
        float touchY = ev.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            downX = touchX;
            downY = touchY;
            if (isAutoScroll && stopScrollWhenTouch) {
                isStopByTouch = true;
                mScroller.setScrollDurationFactor(swipeScrollFactor);
                stopAutoScroll();
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            if (isStopByTouch && stopScrollWhenTouch) {
                startAutoScroll();
            }
        }

        if (Math.abs(downX - touchX) > Math.abs(downY - touchY)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        return super.dispatchTouchEvent(ev);

    }

    /**
     * start auto scroll, first scroll delay time is {@link #getInterval()}
     */
    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage(interval + mScroller.getDuration() / autoScrollFactor);
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(long delayTimeInMills) {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    /**
     * set the factor by which the duration of sliding animation will change while auto scrolling
     */
    public void setAutoScrollDurationFactor(int scrollFactor) {
        autoScrollFactor = scrollFactor;
    }

    /**
     * set the factor by which the duration of sliding animation will change while swiping
     */
    public void setSwipeScrollDurationFactor(int scrollFactor) {
        swipeScrollFactor = scrollFactor;
    }


    private void sendScrollMessage(long delayTimeInMills) {
        /** removeItem messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    /**
     * get auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * set auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     *
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * get auto scroll direction
     *
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     *
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether stop auto scroll when touching, default is true
     *
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     *
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    public void setForceToRelayout(boolean forceToRelayout) {
        this.forceToRelayout = forceToRelayout;
        requestLayout();
    }

}
