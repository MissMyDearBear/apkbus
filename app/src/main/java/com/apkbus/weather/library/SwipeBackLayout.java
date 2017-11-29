package com.apkbus.weather.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.apkbus.weather.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    // 将被视为滑动的最小速度（dp/s）
    private static final int MIN_FLING_VELOCITY = 400;
    // 缺省遮罩颜色值
    private static final int DEFAULT_SCRIM_COLOR = 0x99000000;
    // 全部透明度值
    private static final int FULL_ALPHA = 255;
    // 超过滑动距离
    private static final int OVER_SCROLL_DISTANCE = 100;
    // 缺省滑退阈值
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;
    // 我的方向标志
    private int mEdgeFlag;
    // 我的滑退阈值
    private float mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;
    // 我的activity对象
    private Activity mActivity;
    // 我能否滑动
    private boolean mEnable = true;
    // 我的将滑动视图
    private View mContentView;
    // 我的滑屏助手
    private ViewDragHelper mDragHelper;
    // 我的滑动比例
    private float mScrollPercent;
    // 监听器集
    private List<SwipeListener> mListeners;
    // 遮罩不透明度
    private float mScrimOpacity;
    // 遮罩颜色
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    // 被拖动的边标志
    private int mTrackingEdge;

    private int mContentLeft;
    private int mContentTop;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private Drawable mShadowBottom;
    private boolean mInLayout;
    private Rect mTmpRect = new Rect();

    // 静-动：位置正在变化 作为一个用户输入或模拟用户输入
    public static final int STATE_DRAGGING = ViewDragHelper.STATE_DRAGGING;
    // 静-静；位置不变
    public static final int STATE_IDLE = ViewDragHelper.STATE_IDLE;
    // 动-静：位置停滞到一个固定值
    public static final int STATE_SETTLING = ViewDragHelper.STATE_SETTLING;
    // 滑动边标志 左、右、下、全部（左右下）
    public static final int EDGE_LEFT = ViewDragHelper.EDGE_LEFT;
    public static final int EDGE_RIGHT = ViewDragHelper.EDGE_RIGHT;
    public static final int EDGE_BOTTOM = ViewDragHelper.EDGE_BOTTOM;
    public static final int EDGE_ALL = EDGE_LEFT | EDGE_RIGHT | EDGE_BOTTOM;
    private static final int[] EDGE_FLAGS = {
            EDGE_LEFT, EDGE_RIGHT, EDGE_BOTTOM, EDGE_ALL
    };//方向标志数组

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SwipeBackLayoutStyle);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, new ViewDragCallback());

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeBackLayout, defStyle, R.style.SwipeBackLayout);

        int edgeSize = a.getDimensionPixelSize(R.styleable.SwipeBackLayout_edge_size, -1);
        if (edgeSize > 0) setEdgeSize(edgeSize);
        int mode = EDGE_FLAGS[a.getInt(R.styleable.SwipeBackLayout_edge_flag, 0)];
        setEdgeTrackingEnabled(mode);

        int shadowLeft = a.getResourceId(R.styleable.SwipeBackLayout_shadow_left, R.drawable.shadow_left);
        int shadowRight = a.getResourceId(R.styleable.SwipeBackLayout_shadow_right, R.drawable.shadow_right);
        int shadowBottom = a.getResourceId(R.styleable.SwipeBackLayout_shadow_bottom, R.drawable.shadow_bottom);
        setShadow(shadowLeft, EDGE_LEFT);
        setShadow(shadowRight, EDGE_RIGHT);
        setShadow(shadowBottom, EDGE_BOTTOM);
        a.recycle();
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = MIN_FLING_VELOCITY * density;
        mDragHelper.setMinVelocity(minVel);
        mDragHelper.setMaxVelocity(minVel * 2f);
    }

    /**
     * @param context     上下文
     * @param sensitivity 介于0-1的触摸溢出float终值:{@link android.view.ViewConfiguration getScaledTouchSlop() * (1 / s)}
     */
    public void setSensitivity(Context context, float sensitivity) {
        mDragHelper.setSensitivity(context, sensitivity);
    }//设置导航布局的灵敏度

    /**
     * @param view 视图
     */
    private void setContentView(View view) {
        mContentView = view;
    }//设置将被用户手势滑动的view

    public void setEnableGesture(boolean enable) {
        mEnable = enable;
    }//设置可手势滑动与否

    /**
     * @param edgeFlags Combination of edge flags describing the edges to watch
     * @see #EDGE_LEFT
     * @see #EDGE_RIGHT
     * @see #EDGE_BOTTOM
     * 回调方法是下面俩货
     * {@link ViewDragHelper.Callback#onEdgeTouched(int, int)}
     * {@link ViewDragHelper.Callback#onEdgeDragStarted(int, int)}
     */
    public void setEdgeTrackingEnabled(int edgeFlags) {
        mEdgeFlag = edgeFlags;
        mDragHelper.setEdgeTrackingEnabled(mEdgeFlag);
    }//设置这个边是否可滑动，仅对可滑动边有效

    /**
     * @param color ARGB格式颜色值，如：0xAARRGGBB
     */
    public void setScrimColor(int color) {
        mScrimColor = color;
        invalidate();
    }//滑退时设置此色遮罩掩盖露出的部分

    /**
     * @param size 边缘可触的像素范围
     */
    public void setEdgeSize(int size) {
        mDragHelper.setEdgeSize(size);
    }//设置可触边缘的像素范围，如果可触视图可动态检测边缘触碰或滑动

    /**
     * @param listener 监听器
     */
    public void addSwipeListener(SwipeListener listener) {
        if (mListeners == null)
            mListeners = new ArrayList<>();
        mListeners.add(listener);
    }//添加一个监听器到监听器集中

    /**
     * @param listener 监听器
     */
    public void removeSwipeListener(SwipeListener listener) {
        if (mListeners == null)
            return;
        mListeners.remove(listener);
    }//从监听器集中移除一个

    public interface SwipeListener {
        /**
         * @param state         描述滑动状态的标志
         * @param scrollPercent 滑动比例
         * @see #STATE_IDLE
         * @see #STATE_DRAGGING
         * @see #STATE_SETTLING
         */
        void onScrollStateChange(int state, float scrollPercent);//状态变化时调用

        /**
         * @param edgeFlag 被触及的边缘标志
         * @see #EDGE_LEFT
         * @see #EDGE_RIGHT
         * @see #EDGE_BOTTOM
         */
        void onEdgeTouch(int edgeFlag);//边缘点击时调用

        /* 滑动比例首次超出阈值时调用 */
        void onScrollOverThreshold();
    }

    /**
     * @param threshold 滑退阈值 0-1之间的浮点值
     */
    public void setScrollThresHold(float threshold) {
        if (threshold >= 1.0f || threshold <= 0)
            throw new IllegalArgumentException("Threshold value should be between 0 and 1.0");
        mScrollThreshold = threshold;
    }//设置滑退阈值

    /**
     * @param shadow   图片资源
     * @param edgeFlag 边缘标识
     * @see #EDGE_LEFT
     * @see #EDGE_RIGHT
     * @see #EDGE_BOTTOM
     */
    public void setShadow(Drawable shadow, int edgeFlag) {
        if ((edgeFlag & EDGE_LEFT) != 0) {
            mShadowLeft = shadow;
        } else if ((edgeFlag & EDGE_RIGHT) != 0) {
            mShadowRight = shadow;
        } else if ((edgeFlag & EDGE_BOTTOM) != 0) {
            mShadowBottom = shadow;
        }
        invalidate();
    }//设置边缘阴影显示图片

    /* 设置边缘阴影显示图片,根据资源id获取图片后调用上个方法 */
    public void setShadow(int resId, int edgeFlag) {
        setShadow(getResources().getDrawable(resId), edgeFlag);
    }

    /* 滑走并结束本Activity */
    public void scrollToFinishActivity() {
        int left = 0, top = 0;
        if ((mEdgeFlag & EDGE_LEFT) != 0) {
            left = mContentView.getWidth() + mShadowLeft.getIntrinsicWidth() + OVER_SCROLL_DISTANCE;
            mTrackingEdge = EDGE_LEFT;
        } else if ((mEdgeFlag & EDGE_RIGHT) != 0) {
            left = -mContentView.getWidth() - mShadowRight.getIntrinsicWidth() - OVER_SCROLL_DISTANCE;
            mTrackingEdge = EDGE_RIGHT;
        } else if ((mEdgeFlag & EDGE_BOTTOM) != 0) {
            top = -mContentView.getHeight() - mShadowBottom.getIntrinsicHeight() - OVER_SCROLL_DISTANCE;
            mTrackingEdge = EDGE_BOTTOM;
        }

        mDragHelper.smoothSlideViewTo(mContentView, left, top);
        invalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mEnable)
            return false;
        try {
            return mDragHelper.shouldInterceptTouchEvent(event);
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnable)
            return false;
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mInLayout = true;
        if (mContentView != null)
            mContentView.layout(mContentLeft, mContentTop, mContentLeft + mContentView.getMeasuredWidth(), mContentTop + mContentView.getMeasuredHeight());
        mInLayout = false;
    }

    @Override
    public void requestLayout() {
        if (!mInLayout)
            super.requestLayout();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mContentView;

        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (mScrimOpacity > 0 && drawContent
                && mDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE) {
            drawShadow(canvas, child);
            drawScrim(canvas, child);
        }
        return ret;
    }

    private void drawScrim(Canvas canvas, View child) {
        final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * mScrimOpacity);
        final int color = alpha << 24 | (mScrimColor & 0xffffff);

        if ((mTrackingEdge & EDGE_LEFT) != 0) {
            canvas.clipRect(0, 0, child.getLeft(), getHeight());
        } else if ((mTrackingEdge & EDGE_RIGHT) != 0) {
            canvas.clipRect(child.getRight(), 0, getRight(), getHeight());
        } else if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
            canvas.clipRect(child.getLeft(), child.getBottom(), getRight(), getHeight());
        }
        canvas.drawColor(color);
    }

    private void drawShadow(Canvas canvas, View child) {
        final Rect childRect = mTmpRect;
        child.getHitRect(childRect);

        if ((mEdgeFlag & EDGE_LEFT) != 0) {
            mShadowLeft.setBounds(childRect.left - mShadowLeft.getIntrinsicWidth(), childRect.top,
                    childRect.left, childRect.bottom);
            mShadowLeft.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
            mShadowLeft.draw(canvas);
        }

        if ((mEdgeFlag & EDGE_RIGHT) != 0) {
            mShadowRight.setBounds(childRect.right, childRect.top,
                    childRect.right + mShadowRight.getIntrinsicWidth(), childRect.bottom);
            mShadowRight.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
            mShadowRight.draw(canvas);
        }

        if ((mEdgeFlag & EDGE_BOTTOM) != 0) {
            mShadowBottom.setBounds(childRect.left, childRect.bottom, childRect.right,
                    childRect.bottom + mShadowBottom.getIntrinsicHeight());
            mShadowBottom.setAlpha((int) (mScrimOpacity * FULL_ALPHA));
            mShadowBottom.draw(canvas);
        }
    }

    public void attachToActivity(Activity activity) {
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    @Override
    public void computeScroll() {
        mScrimOpacity = 1 - mScrollPercent;
        if (mDragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        private boolean mIsScrollOverValid;

        @Override
        public boolean tryCaptureView(View view, int i) {
            boolean ret = mDragHelper.isEdgeTouched(mEdgeFlag, i);
            if (ret) {
                if (mDragHelper.isEdgeTouched(EDGE_LEFT, i))
                    mTrackingEdge = EDGE_LEFT;
                else if (mDragHelper.isEdgeTouched(EDGE_RIGHT, i))
                    mTrackingEdge = EDGE_RIGHT;
                else if (mDragHelper.isEdgeTouched(EDGE_BOTTOM, i))
                    mTrackingEdge = EDGE_BOTTOM;
                if (mListeners != null && !mListeners.isEmpty())
                    for (SwipeListener listener : mListeners)
                        listener.onEdgeTouch(mTrackingEdge);
                mIsScrollOverValid = true;
            }
            boolean directionCheck = false;
            if (mEdgeFlag == EDGE_LEFT || mEdgeFlag == EDGE_RIGHT)
                directionCheck = !mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL, i);
            else if (mEdgeFlag == EDGE_BOTTOM)
                directionCheck = !mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL, i);
            else if (mEdgeFlag == EDGE_ALL)
                directionCheck = true;
            return ret & directionCheck;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mEdgeFlag & (EDGE_LEFT | EDGE_RIGHT);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mEdgeFlag & EDGE_BOTTOM;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                mScrollPercent = Math.abs((float) left / (mContentView.getWidth() + mShadowLeft.getIntrinsicWidth()));
            } else if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                mScrollPercent = Math.abs((float) left / (mContentView.getWidth() + mShadowRight.getIntrinsicWidth()));
            } else if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
                mScrollPercent = Math.abs((float) top / (mContentView.getHeight() + mShadowBottom.getIntrinsicHeight()));
            }
            mContentLeft = left;
            mContentTop = top;
            invalidate();
            if (mScrollPercent < mScrollThreshold && !mIsScrollOverValid) {
                mIsScrollOverValid = true;
            }
            if (mListeners != null && !mListeners.isEmpty()
                    && mDragHelper.getViewDragState() == STATE_DRAGGING
                    && mScrollPercent >= mScrollThreshold && mIsScrollOverValid) {
                mIsScrollOverValid = false;
                for (SwipeListener listener : mListeners) {
                    listener.onScrollOverThreshold();
                }
            }

            if (mScrollPercent >= 1) {
                if (!mActivity.isFinishing()) {
                    mActivity.finish();
                    mActivity.overridePendingTransition(0, 0);
                }
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int childWidth = releasedChild.getWidth();
            final int childHeight = releasedChild.getHeight();

            int left = 0, top = 0;
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                left = xvel > 0 || xvel == 0 && mScrollPercent > mScrollThreshold ? childWidth
                        + mShadowLeft.getIntrinsicWidth() + OVER_SCROLL_DISTANCE : 0;
            } else if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                left = xvel < 0 || xvel == 0 && mScrollPercent > mScrollThreshold ? -(childWidth
                        + mShadowLeft.getIntrinsicWidth() + OVER_SCROLL_DISTANCE) : 0;
            } else if ((mTrackingEdge & EDGE_BOTTOM) != 0) {
                top = yvel < 0 || yvel == 0 && mScrollPercent > mScrollThreshold ? -(childHeight
                        + mShadowBottom.getIntrinsicHeight() + OVER_SCROLL_DISTANCE) : 0;
            }
            mDragHelper.settleCapturedViewAt(left, top);
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int ret = 0;
            if ((mTrackingEdge & EDGE_LEFT) != 0) {
                ret = Math.min(child.getWidth(), Math.max(left, 0));
            } else if ((mTrackingEdge & EDGE_RIGHT) != 0) {
                ret = Math.min(0, Math.max(left, -child.getWidth()));
            }
            return ret;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int ret = 0;
            if ((mTrackingEdge & EDGE_BOTTOM) != 0)
                ret = Math.min(0, Math.max(top, -child.getHeight()));
            return ret;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (mListeners != null && !mListeners.isEmpty())
                for (SwipeListener listener : mListeners)
                    listener.onScrollStateChange(state, mScrollPercent);
        }
    }
}
