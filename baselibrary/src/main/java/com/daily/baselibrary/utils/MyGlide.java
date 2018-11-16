package com.daily.baselibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * description:
 * author: dlx
 * date: 2018/11/16
 * version: 1.0
 */
public class MyGlide {


    /**
     * 基础供调用方法
     */
    private static void loadUrl(Context context, String url, ImageView imageView, boolean isRound, int radius, int defaultIcon) {
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions().centerCrop().placeholder(defaultIcon).error(defaultIcon))
                .into(imageView);
    }

    private static void loadRes() {

    }

}
