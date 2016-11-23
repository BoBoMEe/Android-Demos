package com.bobomee.blogdemos.scroll;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created on 16/8/21.下午7:17.
 *
 * @author bobomee.
 * @description:
 */
public class OverScrollerView extends LinearLayout {

  private static final String TAG = OverScrollerView.class.getSimpleName();
  private OverScroller mOverScroller;
  private int mScaledTouchSlop;
  private int mScaledMaximumFlingVelocity;
  private int mScaledMinimumFlingVelocity;

  private VelocityTracker mVelocityTracker;

  private boolean mIsBeginDrag;

  private int mActivePointerId;

  private int mDownX, mDownY;
  private int mLastX, mLastY;
  private int mOverscrollDistance = 200;


  public OverScrollerView(Context context) {
    this(context, null);
  }

  public OverScrollerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public OverScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setOrientation(VERTICAL);
    setOverScrollMode(OVER_SCROLL_ALWAYS);
    init();
  }

  private void init() {
    mOverScroller = new OverScroller(getContext());
    ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
    mScaledMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    mScaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
    mVelocityTracker = VelocityTracker.obtain();
    mOverscrollDistance = 300;
    Log.d(TAG, "mOverscrollDistance:" + mOverscrollDistance);
  }


  @Override
  public boolean onInterceptHoverEvent(MotionEvent event) {

    int actionMasked = MotionEventCompat.getActionMasked(event);

    if ((actionMasked == MotionEvent.ACTION_MOVE) && (mIsBeginDrag)) {
      return true;
    }

    switch (actionMasked) {
      case MotionEvent.ACTION_DOWN: {
        if (mOverScroller.isOverScrolled()) {
          mIsBeginDrag = true;
          mOverScroller.abortAnimation();
        } else {
          mIsBeginDrag = false;
        }
        int index = event.getActionIndex();
        mActivePointerId = event.getPointerId(index);
        mDownX = (int) MotionEventCompat.getX(event, index);
        mDownY = (int) MotionEventCompat.getY(event, index);
        mLastX = mDownX;
        mLastY = mDownY;
        break;
      }
      case MotionEventCompat.ACTION_POINTER_DOWN: {
        onSecondPointerDown(event);
        break;
      }
      case MotionEvent.ACTION_MOVE: {

        if (mActivePointerId == MotionEvent.INVALID_POINTER_ID) {
          return false;
        }
        int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }

        int currentX = (int) MotionEventCompat.getX(event, pointerIndex);
        int currentY = (int) MotionEventCompat.getY(event, pointerIndex);
        int dx = mLastX - currentX;
        int dy = mLastY - currentY;

        if (Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) {
          mIsBeginDrag = true;
          mLastX = currentX;
          mLastY = currentY;
        }

        break;
      }
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP: {
        mIsBeginDrag = false;
        break;
      }
      case MotionEventCompat.ACTION_POINTER_UP: {
        onSecondPointerUp(event);
        break;
      }
    }


    return mIsBeginDrag;
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int actionMasked = MotionEventCompat.getActionMasked(event);
    mVelocityTracker.addMovement(event);

    switch (actionMasked) {
      case MotionEvent.ACTION_DOWN: {
        if ((mIsBeginDrag = !mOverScroller.isFinished())) {
          mOverScroller.abortAnimation();
        }
        int index = event.getActionIndex();
        mActivePointerId = event.getPointerId(index);
        mLastX = (int) MotionEventCompat.getX(event, index);
        mLastY = (int) MotionEventCompat.getY(event, index);
        break;
      }
      case MotionEventCompat.ACTION_POINTER_DOWN: {
        onSecondPointerDown(event);
        break;
      }
      case MotionEvent.ACTION_MOVE: {
        if (mActivePointerId == MotionEvent.INVALID_POINTER_ID) {
          return false;
        }
        int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
        if (pointerIndex < 0) {
          return false;
        }
        int currentX = (int) MotionEventCompat.getX(event, pointerIndex);
        int currentY = (int) MotionEventCompat.getY(event, pointerIndex);
        int dx = mLastX - currentX;
        int dy = mLastY - currentY;
        if (!mIsBeginDrag && Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) {
          mIsBeginDrag = true;
          if (dy > 0) {
            dy -= mScaledTouchSlop;
          } else {
            dy += mScaledTouchSlop;
          }

          if (dx > 0) {
            dx -= mScaledTouchSlop;
          } else {
            dx += mScaledTouchSlop;
          }
        }

        if (mIsBeginDrag) {
          boolean b = overScrollBy(dx, dy, getScrollX(), getScrollY(), 0, getScrollRange(), 0, mOverscrollDistance, true);
          mLastX = currentX;
          mLastY = currentY;
        }
        break;
      }
      case MotionEventCompat.ACTION_POINTER_UP: {
        onSecondPointerUp(event);

        break;
      }
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP: {
        mIsBeginDrag = false;
        int index = MotionEventCompat.findPointerIndex(event, mActivePointerId);
        mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
        float yVelocity = mVelocityTracker.getYVelocity(index);
        if (Math.abs(yVelocity) > mScaledMinimumFlingVelocity) {
          Log.d(TAG, "onTouchEvent() called with: " + "doFling");
          doFling(-yVelocity);
        } else if (mOverScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
          Log.d(TAG, "onTouchEvent() called with: " + "springBack");
          ViewCompat.postInvalidateOnAnimation(this);
        }
        mVelocityTracker.clear();
        mActivePointerId = MotionEvent.INVALID_POINTER_ID;
        break;
      }
    }

    return true;
  }

  private void doFling(float v) {
    Log.d(TAG + "DD", "yVelocity:" + v);
    mOverScroller.fling(
        getScrollX(),
        getScrollY(),
        0, (int) v,
        0, 0,
        0, getScrollRange(),
        0, mOverscrollDistance
    );
    ViewCompat.postInvalidateOnAnimation(this);
  }

  private void onSecondPointerDown(MotionEvent event) {
    int index = MotionEventCompat.getActionIndex(event);
    mActivePointerId = MotionEventCompat.getPointerId(event, index);
    mLastX = (int) MotionEventCompat.getX(event, index);
    mLastY = (int) MotionEventCompat.getY(event, index);
  }


  private void onSecondPointerUp(MotionEvent event) {
    int index = MotionEventCompat.getActionIndex(event);
    int pointerId = MotionEventCompat.getPointerId(event, index);
    if (mActivePointerId == pointerId) {
      int newIndex = index == 0 ? 1 : 0;
      mLastX = (int) MotionEventCompat.getX(event, newIndex);
      mLastY = (int) MotionEventCompat.getY(event, newIndex);
      mActivePointerId = MotionEventCompat.getPointerId(event, newIndex);
    }
  }

  private int getScrollRange() {
    int scrollRange = 0;
    int childCount = getChildCount();
    if (childCount > 0) {
      View child = getChildAt(childCount - 1);
      scrollRange = Math.max(0,
          child.getBottom() - (getHeight() - getPaddingBottom() - getPaddingTop()));
    }
    return scrollRange;
  }


  @Override
  public void computeScroll() {
    if (mOverScroller.computeScrollOffset()) {
      int oldX = getScrollX();
      int oldY = getScrollY();
      int x = mOverScroller.getCurrX();
      int y = mOverScroller.getCurrY();
      if (oldX != x || oldY != y) {
        final int range = getScrollRange();
        int dx = x - oldX;
        int dy = y - oldY;
        overScrollBy(dx, dy, oldX, oldY, 0, range,
            0, mOverscrollDistance, false);
        onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
      }

      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  @Override
  protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    Log.d(TAG, "mOverScroller.isFinished():" + mOverScroller.isFinished());
    if (!mOverScroller.isFinished()) {
      super.scrollTo(scrollX, scrollY);
      if (clampedX || clampedY) {
        mOverScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, 0);
        Log.d(TAG, "onOverScrolled-->springBack");
      }
    } else {
      super.scrollTo(scrollX, scrollY);
    }
  }
}