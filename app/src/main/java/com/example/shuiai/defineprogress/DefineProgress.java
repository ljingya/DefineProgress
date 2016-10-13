package com.example.shuiai.defineprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


public class DefineProgress extends View {
    /**
     * 第一个圆的颜色
     */
    private int firstcolor;
    /**
     * 第二个圆的颜色
     */
    private int secondcolor;
    /**
     * 第三个圆的颜色
     */
    private int threecolor;
    /**
     * 第一个圆的宽度
     */
    private int firststroke;
    /**
     * 第二个圆的宽度
     */
    private int secondstroke;
    /**
     * 第三个圆的宽度
     */
    private int threestroke;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 矩形
     */
    private RectF rect;
    /**
     * 设置加载的度数
     */
    private int progress;

    public DefineProgress(Context context) {
        this(context, null);
    }

    public DefineProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefineProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DefineProgress, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int arr = array.getIndex(i);
            switch (arr) {
                case R.styleable.DefineProgress_firstcolor:
                    firstcolor = array.getColor(arr, Color.BLUE);
                    break;
                case R.styleable.DefineProgress_secondcolor:
                    secondcolor = array.getColor(arr, Color.RED);
                    break;
                case R.styleable.DefineProgress_threecolor:
                    threecolor = array.getColor(arr, Color.WHITE);
                    break;
                case R.styleable.DefineProgress_firststroke:
                    firststroke = array.getDimensionPixelSize(arr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.DefineProgress_secondstroke:
                    secondstroke = array.getDimensionPixelSize(arr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.DefineProgress_radious:
                    threestroke = array.getDimensionPixelSize(arr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        init();
    }

    public DefineProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    private void init() {
        mPaint = new Paint();
        rect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 200;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 200;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        mPaint.setColor(firstcolor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(firststroke);
        mPaint.setStyle(Paint.Style.STROKE);
        int center = getWidth() / 2;
        final int radious = center - secondstroke / 2;

        canvas.drawCircle(getMeasuredHeight() / 2, getMeasuredWidth() / 2, radious, mPaint);
        rect.left = center - radious;
        rect.top = center - radious;
        rect.bottom = center + radious;
        rect.right = center + radious;
        mPaint.setColor(secondcolor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(secondstroke);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rect, 270, progress, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(threecolor);
        canvas.drawCircle(center, secondstroke / 2, threestroke, mPaint);
    }

    //    private ProgressListener progressListener;
//
//    /**
//     * 设置进度的接口
//     *
//     * @param progressListener
//     */
//    public void setOnProgressListener(ProgressListener progressListener) {
//        this.progressListener = progressListener;
//    }
//
//    public interface ProgressListener {
//        void OnProgressLIstner(int angle);
//    }
    public void setUpdateAngle(final int angle) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (progress <= angle) {
                        progress++;
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        postInvalidate();
                    }
                }
            }
        }.start();
    }
}
