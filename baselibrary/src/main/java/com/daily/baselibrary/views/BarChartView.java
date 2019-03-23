package com.daily.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.daily.baselibrary.R;


public class BarChartView extends View {

    /**
     * 文字画笔
     */
    private Paint mPaintText;
    /**
     * 矩形画笔
     */
    private Paint mPaintContent;

    private String title;
    private String xTitle, yTitle;

    private int titleColor, contentColor;

    private int verticalLines;

    private int verticalHeight;

    private float barWidth;

    private static final int DEFAULT_PADDING = 30;

    private int yMaxValue = 100;

    private int[] yValues = new int[]{22, 33, 66, 88, 43, 46, 91};

    private String[] xValues = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日",};

    private int topGap, bottomGap, leftGap, rightGap;

    private RectF mRect = new RectF();
    /**
     * 控件自身宽高
     */
    private int mWidth, mHeight;

    public BarChartView(Context context) {
        this(context, null);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BarChartView);
        title = ta.getString(R.styleable.BarChartView_bcv_title);
        xTitle = ta.getString(R.styleable.BarChartView_bcv_xTitle);
        yTitle = ta.getString(R.styleable.BarChartView_bcv_yTitle);
        titleColor = ta.getColor(R.styleable.BarChartView_bcv_titleColor, Color.GRAY);
        contentColor = ta.getColor(R.styleable.BarChartView_bcv_contentColor, Color.DKGRAY);
        verticalLines = ta.getInt(R.styleable.BarChartView_bcv_verticalLines, 10);
        barWidth = ta.getDimension(R.styleable.BarChartView_bcv_barWidth, dp2px(20));
        ta.recycle();

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setColor(titleColor);
        mPaintText.setTextSize(sp2px(10));
        mPaintText.setStyle(Paint.Style.FILL);

        mPaintContent = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintContent.setColor(contentColor);
        mPaintContent.setStyle(Paint.Style.FILL);

        leftGap = rightGap = topGap = bottomGap = dp2px(DEFAULT_PADDING);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    private static final String TAG = "BarChartView";

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        Log.i(TAG, "onLayout: viewPxSize(" + mWidth + "," + mHeight + ") | viewDpSize(" + px2dp(mWidth) + "," + px2dp(mHeight) + ")");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaintText.setColor(Color.BLACK);
        //画y轴
        canvas.drawLine(dp2px(DEFAULT_PADDING), dp2px(DEFAULT_PADDING), dp2px(DEFAULT_PADDING),
                mHeight - dp2px(DEFAULT_PADDING), mPaintText);
        //画x轴
        canvas.drawLine(dp2px(DEFAULT_PADDING), mHeight - dp2px(DEFAULT_PADDING),
                mWidth - dp2px(DEFAULT_PADDING), mHeight - dp2px(DEFAULT_PADDING), mPaintText);

        //画标题和表名
        float yTitleWidth = mPaintText.measureText(yTitle);
        float xTitleWidth = mPaintText.measureText(xTitle);
        float titleWidth = mPaintText.measureText(title);
        Paint.FontMetrics fontMetrics1 = mPaintText.getFontMetrics();
        float titleHeight = fontMetrics1.descent - fontMetrics1.ascent;
        canvas.drawText(yTitle, dp2px(DEFAULT_PADDING) - yTitleWidth / 2, topGap - dp2px(5), mPaintText);
        mPaintText.setTextSize(sp2px(18));
        canvas.drawText(title, (mWidth - leftGap - rightGap) / 2 - titleWidth / 2, topGap / 2 + titleHeight / 2, mPaintText);
        mPaintText.setTextSize(sp2px(10));
        canvas.drawText(xTitle, mWidth - rightGap + dp2px(5), mHeight - bottomGap + titleHeight / 2, mPaintText);

        //画y轴刻度数字和虚线：
        //先找到内容区域高度，再除以等分
        //循环画完刻度文字和刻度尺
        mPaintText.setColor(Color.GRAY);
        int contentHeight = mHeight - topGap - bottomGap;
        float dividerHeight = contentHeight / (verticalLines + 1);
        for (int i = 0; i < verticalLines; i++) {
            canvas.drawLine(leftGap, mHeight - bottomGap - dividerHeight * (i + 1),
                    leftGap + dp2px(5), mHeight - bottomGap - dividerHeight * (i + 1), mPaintText);

            String text = String.valueOf(10 * (i + 1)); //文字内容
            float textWidth = mPaintText.measureText(text); //文字宽度
            Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;

            float textX = leftGap - textWidth - dp2px(5);
            float textY = mHeight - bottomGap - dividerHeight * (i + 1) + textHeight / 3; //文字垂直居中
            canvas.drawText(text, textX, textY, mPaintText);
        }

        //画矩形内容
        float rectGap = (mWidth - leftGap - rightGap - (barWidth * 7)) / 8;
        Log.i(TAG, "onDraw: gaps = " + leftGap + " barGap = " + rectGap + " barWidth = " + barWidth);
        for (int i = 0; i < xValues.length; i++) {
            mRect.left = leftGap + (barWidth * i) + (rectGap * (i + 1));
            mRect.top = mHeight - bottomGap - ((mHeight - topGap - bottomGap - dividerHeight) / yMaxValue * yValues[i]);
            mRect.right = mRect.left + barWidth;
            mRect.bottom = mHeight - bottomGap;
            canvas.drawRect(mRect, mPaintContent);

            //写底部文字
            Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
            float textWidth = mPaintText.measureText(xValues[i]);
            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            float barMiddle = mRect.left + barWidth / 2;
            canvas.drawText(xValues[i], barMiddle - textWidth / 2, mHeight - bottomGap + textHeight + dp2px(5), mPaintText);

            //画柱状图上方数据
            String value = String.valueOf(yValues[i]);
            float valueWidth = mPaintText.measureText(value);
            canvas.drawText(value, barMiddle - valueWidth / 2, mRect.top - dp2px(5), mPaintText);
        }

    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param dpValue
     * @return
     */
    public int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param pxValue
     * @return
     */
    public int px2dp(float pxValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */

    public int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
