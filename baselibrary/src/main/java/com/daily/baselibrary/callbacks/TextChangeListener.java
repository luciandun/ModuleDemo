package com.daily.baselibrary.callbacks;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * description:监听器
 * author: dlx
 * date: 2018/5/4
 * version: 1.0
 */
public class TextChangeListener implements TextWatcher {

    private EditText mEditText;
    private TextView mCountText;
    private Context mContext;
    private Toast toast;
    private int maxLength = -1;
    private boolean isFilter;


    /**
     * constructor
     *
     * @param et
     * @param context
     */
    public TextChangeListener(EditText et, Context context) {
        this.mEditText = et;
        this.mContext = context;
    }

    /**
     * 设置计数器View
     *
     * @param textView
     */
    public void setCountText(TextView textView) {
        this.mCountText = textView;
    }

    /**
     * 设置最大统计字数
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 设置是否过滤EmoJi表情
     *
     * @param isFilter
     */
    public void setFilterEmoJi(boolean isFilter) {
        this.isFilter = isFilter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        CharSequence input = s.subSequence(start, start + count);
        if (maxLength != -1 && mCountText != null) {
            if (s.length() <= maxLength) {
                String str = s.length() + "/" + maxLength;
                mCountText.setText(str);
            }
        }
        // 退格
        if (count == 0) return;

        if (isFilter) {
            if (isContainEmoJi(input)) {
                show("请不要输入表情");
                mEditText.setText(removeEmoJi(s));
            }
        }
        //最后光标移动到最后
        mEditText.setSelection(mEditText.getText().toString().length());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 去除字符串中的EmoJi表情
     *
     * @param source 输入的内容
     * @return 去除EmoJi后的内容
     */
    private String removeEmoJi(CharSequence source) {
        String result = "";
        for (int i = 0; i < source.length(); i++) {

            int point = source.toString().codePointAt(i);
            if (isEmoJiChar(point))
                continue;
            char c = source.charAt(i);
            result += c;
        }
        return result;
    }


    /**
     * 判断字符串中是否包含有EmoJi表情
     *
     * @param input 输入的内容
     * @return true 有EmoJi
     */
    private boolean isContainEmoJi(CharSequence input) {
        for (int i = 0; i < input.length(); i++) {
            int point = input.toString().codePointAt(i);
            if (isEmoJiChar(point))
                return true;
        }
        return false;
    }

    /**
     * 是否是EmoJi 表情,抄的那哥们的代码
     *
     * @param codePoint 输入的内容
     * @return true 是EmoJi表情
     */
    private boolean isEmoJiChar(char codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x25FB || codePoint == 0x25FC // 实体框和空框
                || codePoint == 0x25B6 || codePoint == 0x25C0 // 播放，回退（对应快进快退）
                || codePoint == 0xA9 || codePoint == 0xAE // C R 符号
                || (codePoint >= 0x2196 && codePoint <= 0x2199) // 4个斜向箭头
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)//
                || (codePoint >= 0x2028 && codePoint <= 0x202F)//
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)//
                /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
                || codePoint >= 0x10000;
    }

    /**
     * 是否是EmoJi 表情,抄的那哥们的代码
     *
     * @param codePoint 输入的内容
     * @return true 是EmoJi表情
     */
    private boolean isEmoJiChar(int codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
                || codePoint == 0x25FB || codePoint == 0x25FC // 实体框和空框
                || codePoint == 0x25B6 || codePoint == 0x25C0 // 播放，回退（对应快进快退）
                || codePoint == 0xA9 || codePoint == 0xAE // C R 符号
                || (codePoint >= 0x2196 && codePoint <= 0x2199) // 4个斜向箭头
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x2000 && codePoint <= 0x200F)//
                || (codePoint >= 0x2028 && codePoint <= 0x202F)//
                || codePoint == 0x205F //
                || (codePoint >= 0x2065 && codePoint <= 0x206F)//
                /* 标点符号占用区域 */
                || (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
                || (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
                || (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
                || (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
                || (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
                || (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
                || (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
                || codePoint >= 0x10000;
    }

    private void show(String msg) {
        if (toast == null)
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        else {
            toast.cancel();
            toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
