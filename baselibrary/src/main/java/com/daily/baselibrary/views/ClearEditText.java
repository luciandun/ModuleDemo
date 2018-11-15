package com.daily.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.daily.baselibrary.R;


/**
 * description:带一键清除的EditText
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class ClearEditText extends AppCompatEditText {

    /**
     * 右边清除按钮
     */
    private Drawable clearDrawable;

    /**
     * 仅当获取当前焦点且有内容时，才显示清除符号
     */
    protected boolean isFocused;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        int icoResId = ta.getResourceId(R.styleable.ClearEditText_clearIcon, R.drawable.icon_clear_base);
        ta.recycle();
        clearDrawable = ContextCompat.getDrawable(context, icoResId);
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
        showClearIcon(false); //初始状态为不可见
    }

    /**
     * 设置清除按钮是否可见
     *
     * @param visible
     */
    public void showClearIcon(boolean visible) {
        Drawable rightDrawable = visible ? clearDrawable : null;
        this.setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], rightDrawable, getCompoundDrawables()[3]);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        isFocused = focused;
        showClearIcon(focused && getText().length() > 0);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        showClearIcon(isFocused && s.length() > 0);
    }

    /**
     * 说明：isInnerWidth, isInnerHeight为true，触摸点在删除图标之内，则视为点击了删除图标
     * event.getX() 获取相对应自身左上角的X坐标
     * event.getY() 获取相对应自身左上角的Y坐标
     * getWidth() 获取控件的宽度
     * getHeight() 获取控件的高度
     * getTotalPaddingRight() 获取删除图标左边缘到控件右边缘的距离
     * getPaddingRight() 获取删除图标右边缘到控件右边缘的距离
     * isInnerWidth:
     * getWidth() - getTotalPaddingRight() 计算删除图标左边缘到控件左边缘的距离
     * getWidth() - getPaddingRight() 计算删除图标右边缘到控件左边缘的距离
     * isInnerHeight:
     * distance 删除图标顶部边缘到控件顶部边缘的距离
     * distance + height 删除图标底部边缘到控件顶部边缘的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds(); //拿到清除图标
                int height = rect.height();
                int distance = (getHeight() - height) / 2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y < (distance + height);
                if (isInnerWidth && isInnerHeight)
                    setText("");
            }
        }
        return super.onTouchEvent(event);
    }

}
