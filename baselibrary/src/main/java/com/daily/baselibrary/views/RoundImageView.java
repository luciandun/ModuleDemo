package com.daily.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.daily.baselibrary.R;

/**
 * description:自定义多功能ImageView，可圆角亦可圆形，可设置边框颜色
 * author: dlx
 * date: 2018/11/06
 * version: 1.0
 */
public class RoundImageView extends AppCompatImageView {



    /**
     * 定义图形类型：
     * 普通、圆形、圆角
     */
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_CIRCLE = 1;
    private static final int TYPE_ROUND = 2;

    /**
     * 边框颜色
     * 显示区背景
     * 边框宽度
     * 圆角度数
     */
    private int mBorderColor;
    private int mBackgroundColor;
    private float mBorderWidth;
    private float mRoundRadius;

    /**
     * 控件自身宽高
     */
    private int mWidth;
    private int mHeight;

    /**
     * 内容画笔、边框画笔
     */
    private Paint mPaintBitmap;
    private Paint mPaintBorder;
    private Paint mPaintBackground;

    /**
     * 内容矩形、边框矩形
     */
    private RectF mRectBitmap;
    private RectF mRectBorder;

    private Matrix mMatrix;
    private BitmapShader mBitmapShader;
    private Bitmap mRawBitmap;

    private int mImgType = TYPE_NORMAL;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPain();
        mRectBitmap = new RectF();
        mRectBorder = new RectF();
        mMatrix = new Matrix();
    }

    /**
     * getDimension 返回float数据，最精确
     * getDimensionPixelSize 返回int数据，四舍五入
     * getDimensionPixelOffset 返回int数据，向下取整
     *
     * @param context 上下文
     * @param attrs   attrs
     */
    private void initParams(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mBorderColor = ta.getColor(R.styleable.RoundImageView_riv_borderColor, Color.WHITE);         //边框颜色 默认白色
        mBorderWidth = ta.getDimension(R.styleable.RoundImageView_riv_borderWidth, 0);      //边框宽度 默认0
        mRoundRadius = ta.getDimension(R.styleable.RoundImageView_riv_borderCorner, 0);     //圆角弧度 默认0
        mBackgroundColor = ta.getColor(R.styleable.RoundImageView_riv_backgroundColor, Color.TRANSPARENT); //显示区背景 默认透明
        int type = ta.getInt(R.styleable.RoundImageView_riv_imgType, TYPE_NORMAL);
        mImgType = type == TYPE_NORMAL ? TYPE_NORMAL :
                type == TYPE_ROUND ? TYPE_ROUND : TYPE_CIRCLE;

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    /**
     * 初始化画笔数据
     */
    private void initPain() {
        if (mPaintBitmap == null) mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (mPaintBorder == null) mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (mPaintBackground == null) mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (mBackgroundColor != Color.TRANSPARENT) {
            mPaintBackground.setColor(mBackgroundColor);
            mPaintBackground.setStyle(Paint.Style.FILL);
        }

        mPaintBitmap.setStyle(Paint.Style.FILL);

        if (mBorderWidth > 0) {
            mPaintBorder.setStyle(Paint.Style.STROKE);
            mPaintBorder.setColor(mBorderColor);
            mPaintBorder.setStrokeWidth(mBorderWidth);
        }
    }

    /**
     * 获取显示区内容
     */
    private void initBitmap() {
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            mRawBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            mRawBitmap = bmp;
        } else {
            mRawBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initBitmap();
        if (mRawBitmap == null) return;

        int viewMinSize = Math.min(mWidth, mHeight);
        float tarWidth = mImgType == TYPE_CIRCLE ? viewMinSize : mWidth;
        float tarHeight = mImgType == TYPE_CIRCLE ? viewMinSize : mHeight;
        float halfBorderWidth = mBorderWidth / 2f;
        float doubleBorderWidth = mBorderWidth * 2f;

        if (mBitmapShader == null) {
            mBitmapShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }

        //等比缩放图片至填充完整整个控件
        mMatrix.setScale((tarWidth - doubleBorderWidth) / mRawBitmap.getWidth(), (tarHeight - doubleBorderWidth) / mRawBitmap.getHeight());
        mBitmapShader.setLocalMatrix(mMatrix);

        mPaintBitmap.setShader(mBitmapShader);

        if (mImgType == TYPE_CIRCLE) {
            float radius = viewMinSize / 2;
            //画底色背景
            if (mBackgroundColor != Color.TRANSPARENT) {
                canvas.drawCircle(radius, radius, radius - mBorderWidth, mPaintBackground);
            }
            //画边框
            if (mBorderWidth > 0) {
                canvas.drawCircle(radius, radius, radius - mBorderWidth, mPaintBorder);
            }
//            canvas.translate(mBorderWidth, mBorderWidth);
            //画内容
//            canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius - mBorderWidth, mPaintBitmap);
            canvas.drawCircle(radius, radius, radius - mBorderWidth, mPaintBitmap);
        } else {
            mRectBitmap.set(0, 0, tarWidth - doubleBorderWidth, tarHeight - doubleBorderWidth);
            mRectBorder.set(halfBorderWidth, halfBorderWidth, tarWidth - halfBorderWidth, tarHeight - halfBorderWidth);
            float borderRadius = mRoundRadius - halfBorderWidth > 0 ? mRoundRadius - halfBorderWidth : 0f;
            float bitmapRadius = mRoundRadius - mBorderWidth > 0 ? mRoundRadius - mBorderWidth : 0f;

            if (mImgType == TYPE_ROUND) {
                if (mBorderWidth > 0) {
                    canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBackground);
                }
                canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder);
                canvas.translate(mBorderWidth, mBorderWidth);
                canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBitmap);
            }
            if (mImgType == TYPE_NORMAL) {
                if (mBorderWidth > 0) {
                    canvas.drawRect(mRectBitmap, mPaintBackground);
                }
                canvas.drawRect(mRectBorder, mPaintBorder);
                canvas.translate(mBorderWidth, mBorderWidth);
                canvas.drawRect(mRectBitmap, mPaintBitmap);
            }
        }
    }

//    /**
//     * 定义图形类型：
//     * 普通、圆形、圆角
//     */
//    private static final int TYPE_NORMAL = 0;
//    private static final int TYPE_CIRCLE = 1;
//    private static final int TYPE_ROUND = 2;
//
//    /**
//     * 边框颜色
//     * 边框宽度
//     * 圆角度数
//     */
//    private int mBorderColor;
//    private float mBorderWidth;
//    private float mRoundRadius;
//
//    /**
//     * 控件自身宽高
//     */
//    private int mWidth;
//    private int mHeight;
//
//    /**
//     * 是否是圆形
//     */
//    private boolean isCircle;
//
//    /**
//     * 内容画笔、边框画笔
//     */
//    private Paint mPaintBitmap;
//    private Paint mPaintBorder;
//
//    /**
//     * 内容矩形、边框矩形
//     */
//    private RectF mRectBitmap;
//    private RectF mRectBorder;
//
//    private Matrix mMatrix;
//    private BitmapShader mBitmapShader;
//    private Bitmap mRawBitmap;
//    private int mType = TYPE_NORMAL;
//
//
//    public RoundImageView(Context context) {
//        this(context, null);
//    }
//
//    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initParams(context, attrs);
//        mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        mRectBitmap = new RectF();
//        mRectBorder = new RectF();
//
//        mMatrix = new Matrix();
//        if (isCircle) mType = TYPE_CIRCLE;
//        else if (mRoundRadius > 0) mType = TYPE_ROUND;
//        else mType = TYPE_NORMAL;
//    }
//
//    /**
//     * getDimension 返回float数据，最精确
//     * getDimensionPixelSize 返回int数据，四舍五入
//     * getDimensionPixelOffset 返回int数据，向下取整
//     *
//     * @param context 上下文
//     * @param attrs   attrs
//     */
//    private void initParams(Context context, AttributeSet attrs) {
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
//        mBorderColor = ta.getColor(R.styleable.RoundImageView_riv_borderColor, Color.WHITE);         //边框颜色 默认白色
//        mBorderWidth = ta.getDimension(R.styleable.RoundImageView_riv_borderWidth, 0);      //边框宽度 默认0
//        mRoundRadius = ta.getDimension(R.styleable.RoundImageView_riv_borderCorner, 0);     //圆角弧度 默认0
//        isCircle = ta.getBoolean(R.styleable.RoundImageView_riv_isCircle, false);           //是否圆形 默认不是
//        ta.recycle();
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mWidth = getMeasuredWidth();
//        mHeight = getMeasuredHeight();
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Bitmap bitmap = getDrawableBitmap(getDrawable());
//        if (bitmap != null && mType != TYPE_NORMAL) {
//            int viewMinSize = Math.min(mWidth, mHeight);
//            float trgWidth = mType == TYPE_CIRCLE ? viewMinSize : mWidth;
//            float trgHeight = mType == TYPE_CIRCLE ? viewMinSize : mHeight;
//            float halfBorderWidth = mBorderWidth / 2f;
//            float doubleBorderWidth = mBorderWidth * 2f;
//
//            if (mBitmapShader == null || !bitmap.equals(mRawBitmap)) {
//                mRawBitmap = bitmap;
//                mBitmapShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            }
//
//            //等比缩放图片至填充完整整个控件
//            if (mBitmapShader != null) {
//                mMatrix.setScale((trgWidth - doubleBorderWidth) / bitmap.getWidth(), (trgHeight - doubleBorderWidth) / bitmap.getHeight());
//                mBitmapShader.setLocalMatrix(mMatrix);
//            }
//
//            mPaintBitmap.setShader(mBitmapShader);
//            mPaintBorder.setStyle(Paint.Style.STROKE);
//            mPaintBorder.setStrokeWidth(mBorderWidth);
//            mPaintBorder.setColor(mBorderWidth > 0 ? mBorderColor : Color.TRANSPARENT);
//            if (mType == TYPE_CIRCLE) {
//                float radius = viewMinSize / 2;
//                canvas.drawCircle(radius, radius, radius - halfBorderWidth, mPaintBorder);
//                canvas.translate(mBorderWidth, mBorderWidth);
//                canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius - mBorderWidth, mPaintBitmap);
//            } else if (mType == TYPE_ROUND) {
//                mRectBitmap.set(0, 0, trgWidth - doubleBorderWidth, trgHeight - doubleBorderWidth);
//                mRectBorder.set(halfBorderWidth, halfBorderWidth, trgWidth - halfBorderWidth, trgHeight - halfBorderWidth);
//                float borderRadius = mRoundRadius - halfBorderWidth > 0 ? mRoundRadius - halfBorderWidth : 0f;
//                float bitmapRadius = mRoundRadius - mBorderWidth > 0 ? mRoundRadius - mBorderWidth : 0f;
//                canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder);
//                canvas.translate(mBorderWidth, mBorderWidth);
//                canvas.drawRoundRect(mRectBitmap, bitmapRadius, bitmapRadius, mPaintBitmap);
//            }
//        } else {
//            super.onDraw(canvas);
//        }
//    }
//
//
//    private Bitmap getDrawableBitmap(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable)
//            return ((BitmapDrawable) drawable).getBitmap();
//        else if (drawable instanceof ColorDrawable) {
//            Rect rect = drawable.getBounds();
//            int width = rect.right - rect.left;
//            int height = rect.bottom - rect.top;
//            int color = ((ColorDrawable) drawable).getColor();
//            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bmp);
//            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
//            return bmp;
//        } else return null;
//    }
//
//
//    private float px2dp(Context context, int pxValue) {
//        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
//        return (pxValue / scale);
//    }
//
//    private int dp2px(Context context, int dpValue) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
//                context.getApplicationContext().getResources().getDisplayMetrics());
//    }
}
