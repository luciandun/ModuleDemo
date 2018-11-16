package com.daily.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daily.baselibrary.R;

/**
 * description:通用菜单栏选项
 * 包含：图标 标题 子标题 内容 红点 更多
 * author: dlx
 * date: 2018/11/16
 * version: 1.0
 */
public class MenuItem extends FrameLayout {

    private ImageView ivIcon;
    private ImageView ivArrow;
    private TextView tvTitle;
    private TextView tvRight;
    private View redDot;
    private View divider;

    public MenuItem(@NonNull Context context) {
        this(context, null);
    }

    public MenuItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
        int iconRes = ta.getResourceId(R.styleable.MenuItem_mi_icon, R.drawable.icon_default_menu);
        String title = ta.getString(R.styleable.MenuItem_mi_title);
        String rightText = ta.getString(R.styleable.MenuItem_mi_right_content);
        boolean showDot = ta.getBoolean(R.styleable.MenuItem_mi_show_dot, false);
        boolean showArrow = ta.getBoolean(R.styleable.MenuItem_mi_show_arrow, false);
        boolean showDivider = ta.getBoolean(R.styleable.MenuItem_mi_show_divider, false);
        ta.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.layout_menu_item, this);
        ivIcon = view.findViewById(R.id.iv_icon);
        ivArrow = view.findViewById(R.id.iv_right_arrow);
        tvTitle = view.findViewById(R.id.tv_title);
        tvRight = view.findViewById(R.id.tv_right);
        redDot = view.findViewById(R.id.red_dot);
        divider = view.findViewById(R.id.divider);

        setIcon(iconRes);
        setTitle(title);
        setRightText(rightText);
        showDot(showDot);
        showArrow(showArrow);
        showDivider(showDivider);
    }

    public void setTitle(String title) {
        if (title != null) {
            tvTitle.setText(title);
        }
    }

    public void setRightText(String text) {
        if (text != null)
            tvRight.setText(text);
    }

    public void setIcon(int resId) {
        ivIcon.setImageResource(resId);
    }

    public void showArrow(boolean show) {
        ivArrow.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void showDot(boolean show) {
        redDot.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    public void showDivider(boolean show) {
        divider.setVisibility(show ? VISIBLE : INVISIBLE);
    }
}
