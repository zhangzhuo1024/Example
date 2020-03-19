package com.example.zz.example.customcontrol;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.example.zz.example.LogUtils;
import com.example.zz.example.R;

/**
 * Created by zhangzhuo.
 * Blog: https://blog.csdn.net/zhangzhuo1024
 * Date: 2020/3/19
 */
public class IconView extends View {

    private static final int ICON_MIN_WIDTH  = 20;
    private float mCycleWidth;
    private float mCycleTroke;
    private float mHalfCycleTroke;
    private float mRedPointWidth;
    private float mCycleCentreWidth;
    private float mSquareWidth;
    private float mSquareRadius;
    private float mMoveDistance;
    private float mRedPointRadius;
    private float mRedPointOffset;
    private int mLayoutWidth;
    private int mLayoutHeight;
    private int mCycleColor;
    private int mRedPointColor;
    private Paint mCyclePaint;
    private Paint mRedPointPaint;
    private RectF mCycleRect;
    private RectF mRedPointRect;

    //代码中直接实例化自定义控件，会调用此构造函数，如 new IconView(this)
    public IconView(Context context) {
        this(context, null);
    }

    //在xml中布局使用时会调用此构造函数，attrs含有xml中添加的属性值
    public IconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //系统默认会调用前两种构造函数，我们统一将上两种调用到第三个，然后在第三个中初始化
    public IconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconView);
        mCycleTroke = typedArray.getDimension(R.styleable.IconView_cycle_troke, getContext().getResources().getDimensionPixelSize(R.dimen.cycle_troke));
        mCycleWidth = typedArray.getDimension(R.styleable.IconView_cycle_width, getContext().getResources().getDimensionPixelSize(R.dimen.cycle_width));
        mCycleCentreWidth = mCycleWidth - mCycleTroke;
        mCycleColor = typedArray.getColor(R.styleable.IconView_cycle_color, getContext().getResources().getColor(R.color.cycle_color));
        mHalfCycleTroke = mCycleTroke / 2;

        mRedPointWidth = typedArray.getDimension(R.styleable.IconView_red_point_width, getContext().getResources().getDimensionPixelSize(R.dimen.red_point_width));
        mRedPointColor = typedArray.getColor(R.styleable.IconView_red_point_color, getContext().getResources().getColor(R.color.red_point_color));

        mSquareWidth = typedArray.getDimension(R.styleable.IconView_square_width, getContext().getResources().getDimensionPixelSize(R.dimen.square_width));
        mSquareRadius = typedArray.getDimension(R.styleable.IconView_square_radius, getContext().getResources().getDimensionPixelSize(R.dimen.square_radius));

        mMoveDistance = typedArray.getDimension(R.styleable.IconView_move_distance, getContext().getResources().getDimensionPixelSize(R.dimen.move_distance));

        mCyclePaint = new Paint();
        mCyclePaint.setAntiAlias(true);
        mCyclePaint.setStyle(Paint.Style.STROKE);
        mCyclePaint.setStrokeWidth(mCycleTroke);
        mCyclePaint.setColor(mCycleColor);

        mRedPointPaint = new Paint();
        mRedPointPaint.setAntiAlias(true);
        mRedPointPaint.setStyle(Paint.Style.FILL);
        mRedPointPaint.setColor(mRedPointColor);

        mCycleRect = new RectF(0, 0, 0, 0);
        mRedPointRect = new RectF(0, 0, 0, 0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.e("onSizeChanged  oldw = " + oldw + " oldh = " + oldh);
        LogUtils.e("onSizeChanged  w = " + w + " h = " + h);
        this.mLayoutWidth = w;
        this.mLayoutHeight = h;
        mCycleRect.left = (mLayoutWidth - mCycleCentreWidth) / 2;
        mCycleRect.right = mLayoutWidth - (mLayoutWidth - mCycleCentreWidth) / 2;

        setRectVerticalValue();
        mRedPointRadius = mRedPointWidth / 2;
        mRedPointOffset = 0f;
    }

    private void setRectVerticalValue() {
        mCycleRect.top = mLayoutHeight - mCycleCentreWidth - mHalfCycleTroke - mRedPointOffset;
        mCycleRect.bottom = mLayoutHeight - mHalfCycleTroke - mRedPointOffset;

        mRedPointRect.top = mLayoutHeight - (mCycleWidth / 2 - mRedPointWidth / 2) - mRedPointWidth - mRedPointOffset;
        mRedPointRect.bottom = mLayoutHeight - (mCycleWidth / 2 - mRedPointWidth / 2) - mRedPointOffset;
        mRedPointRect.left = (mLayoutWidth - mRedPointWidth) / 2;
        mRedPointRect.right = mLayoutWidth - (mLayoutWidth - mRedPointWidth) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.e("onDraw  ");
        canvas.drawRoundRect(mCycleRect, (mLayoutWidth - mCycleTroke) / 2, (mLayoutWidth - mCycleTroke) / 2, mCyclePaint);
        canvas.drawRoundRect(mRedPointRect, mRedPointRadius, mRedPointRadius, mRedPointPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int layoutWidth = measureWidthAndHeight(widthMeasureSpec, 0);
        int layoutHeight = measureWidthAndHeight(heightMeasureSpec, 1);
        this.mLayoutWidth = layoutWidth;
        this.mLayoutHeight = layoutHeight;
        setMeasuredDimension(layoutWidth, layoutHeight);
        LogUtils.e("onSizeChanged  layoutWidth = " + layoutWidth + " layoutHeight = " + layoutHeight);
    }

    private int measureWidthAndHeight(int measureSpec, int type) {
        int model = MeasureSpec.getMode(measureSpec);//获得当前空间值的一个模式
        int size = MeasureSpec.getSize(measureSpec);//获得当前空间值的推荐值

        switch (model) {
            case MeasureSpec.EXACTLY://当你的控件设置了一个精确的值或者为match_parent时, 为这种模式
                //size = (int) paint.measureText(labels[0]);
                return size;
            case MeasureSpec.AT_MOST://当你的控件设置为wrap_content时，为这种模式
                if (type == 0) {
                    //测量宽度
                    size = ICON_MIN_WIDTH;
                    return size;
                } else {
                    //测量高度
                    size = ICON_MIN_WIDTH;
                    return size;
                }
            case MeasureSpec.UNSPECIFIED:
                LogUtils.e("未指定自定义控件高度");
                break;
        }
        LogUtils.e("onMeasure error");
        return 0;
    }

    public void startToMove(){
        TransAnimator startValue = new TransAnimator(mRedPointWidth, mRedPointWidth / 2, 0f);
        TransAnimator endValue = new TransAnimator(mSquareWidth, mSquareRadius, mMoveDistance);
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(new TransTypeEvaluator(), startValue, endValue);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new PathInterpolator(0.3f, 0f, 0, 1));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                TransAnimator animatedValue = (TransAnimator) animation.getAnimatedValue();
                mRedPointWidth = animatedValue.width;
                mRedPointRadius = animatedValue.radius;
                mRedPointOffset = animatedValue.offset;
                setRectVerticalValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private class TransAnimator{
        float width;
        float radius;
        float offset;
        TransAnimator(float width, float radius, float offset){
            this.width = width;
            this.radius = radius;
            this.offset = offset;
        }
    }

    private class TransTypeEvaluator implements TypeEvaluator<TransAnimator> {
        TransAnimator transAnimator = new TransAnimator(0f,0f, 0f);

        @Override
        public TransAnimator evaluate(float fraction, TransAnimator startValue, TransAnimator endValue) {
            transAnimator.width = startValue.width + (endValue.width - startValue.width) * fraction;
            transAnimator.radius = startValue.radius + (endValue.radius - startValue.radius) * fraction;
            transAnimator.offset = startValue.offset + (endValue.offset - startValue.offset) * fraction;
            return transAnimator;
        }
    }

}