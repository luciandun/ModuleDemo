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
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.daily.baselibrary.R;

/**
 * description:
 * author: dlx
 * date: 2018/11/09
 * version: 1.0
 */
public class CircleImageView extends AppCompatImageView {

    private int borderColor;
    private int fillColor;
    private float borderWidth;

    private Paint paint;

    private int mWidth, mHeight;

    private Bitmap mRawBitmap;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
        mMatrix = new Matrix();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        borderColor = ta.getColor(R.styleable.CircleImageView_civ_borderColor, Color.WHITE);
        fillColor = ta.getColor(R.styleable.CircleImageView_civ_fillColor, Color.TRANSPARENT);
        borderWidth = ta.getDimension(R.styleable.CircleImageView_civ_borderWidth, 0f);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        initBitmap();
        if (mRawBitmap == null) return;

        int minWidth = Math.min(mWidth, mHeight);
        int radius = minWidth / 2;
        Log.i("CircleImageView:", "onDraw(): width = " + minWidth);
        Log.i("CircleImageView:", "onDraw(): height = " + minWidth);
        Log.i("CircleImageView:", "onDraw(): radius = " + radius);
        Log.i("CircleImageView:", "onDraw(): borderWidth = " + borderWidth);

        if (mBitmapShader == null) {
            mBitmapShader = new BitmapShader(mRawBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }
        //等比缩放图片至填充完整整个控件
        mMatrix.setScale(minWidth / mRawBitmap.getWidth(), minWidth / mRawBitmap.getHeight());
        mBitmapShader.setLocalMatrix(mMatrix);

        //画底色
        if (fillColor != Color.TRANSPARENT) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fillColor);
            canvas.drawCircle(radius, radius, radius - borderWidth, paint);
            Log.i("CircleImageView:", "onDraw(): 画底色over");
        }

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(mBitmapShader);
        canvas.drawCircle(radius, radius, radius - borderWidth, paint);
        Log.i("CircleImageView:", "onDraw(): 画内容over");

        //画边框
        if (borderWidth > 0) {
            paint.reset();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(borderWidth);
            canvas.drawCircle(radius, radius, radius - borderWidth / 2, paint);
            Log.i("CircleImageView:", "onDraw(): 画边框over");
        }


    }

    private void initBitmap() {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        if (drawable instanceof BitmapDrawable) {
            mRawBitmap = ((BitmapDrawable) drawable).getBitmap();
            Log.d("CircleImageView:", "initBitmap(): BitmapDrawable");
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            mRawBitmap = bmp;
            Log.d("CircleImageView:", "initBitmap(): ColorDrawable");
        } else {
            mRawBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Log.d("CircleImageView:", "initBitmap(): createNewBitmap");
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        initBitmap();
        invalidate();
    }
}
