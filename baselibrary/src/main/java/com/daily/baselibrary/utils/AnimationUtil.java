package com.daily.baselibrary.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by arvin on 2016/2/2 16:41.
 * 常用动画工具方法
 */
@SuppressWarnings("all")
public class AnimationUtil {
    private static final long duration = 200l;

    /**
     * 旋转
     *
     * @param targetView 动画目标view
     * @param duration   周期
     * @return Animator
     */
    public static Animator animRotation(View targetView, int duration, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "rotation", values);
        animator.setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * 透明度
     *
     * @param targetView 动画目标view
     * @param duration   周期
     * @return Animator
     */
    public static Animator animAlpha(View targetView, long duration, float... values) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "alpha", values);
        animator.setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * view缩放动画，无实际缩放效果，用于下拉场景
     *
     * @param view     目标view
     * @param duration 动画执行时间 单位：毫秒
     */
    public static void startScaleOutAnim(View view, long duration) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    /**
     * view拉伸动画，无实际缩放效果，用于下拉场景
     *
     * @param view 目标view，动画执行时间 单位：毫秒 默认200ms
     */
    public static void startScaleOutAnim(View view) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(1f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(200);
//        animation.setInterpolator(new AccelerateInterpolator());
//        animation.setInterpolator(new DecelerateInterpolator());
        animation.setFillAfter(true);
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    /**
     * 回收下拉view
     *
     * @param view
     * @param duration
     */
    public static void startScaleInAnim(View view, long duration) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(1f, 1f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    /**
     * 回收下拉view
     *
     * @param view
     */
    public static void startScaleInAnim(View view) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(1f, 1f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(200);
        animation.setFillAfter(true);
        if (view != null) {
            view.startAnimation(animation);
        }
    }

    // 以下为缩放同时带淡入淡出功能动画

    /**
     * 从view右上角放大到view左下角显示
     *
     * @param view
     */
    public static void startZoomScaleOutAnim(View view) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(200);
        animation.setFillAfter(true);
//        animation.setInterpolator();

        //透明度
        AlphaAnimation animation1 = new AlphaAnimation(0, 1f);
        animation1.setDuration(200);
        animation1.setFillAfter(true);
        animation1.startNow();

        if (view != null) {
            view.startAnimation(animation);
        }
    }

    /**
     * 缩小到右上角回收下拉view
     *
     * @param view
     */
    public static void startZoomScaleInAnim(View view) {
//        ScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        ScaleAnimation animation = new ScaleAnimation(1f, 0f, 1f, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(200);
        animation.setFillAfter(true);

        //透明度
        AlphaAnimation animation1 = new AlphaAnimation(1f, 0);
        animation1.setDuration(300);
        animation1.setFillAfter(true);

        if (view != null) {
            view.startAnimation(animation1);
            view.startAnimation(animation);
        }
    }


    /**
     * 淡入动画
     *
     * @param view
     */
    public static void animFadeIn(View view) {
        AlphaAnimation animation = new AlphaAnimation(0, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);

        if (view != null)
            view.startAnimation(animation);
    }

    /**
     * 淡出动画
     *
     * @param view
     */
    public static void animFadeOut(View view) {
        AlphaAnimation animation = new AlphaAnimation(1f, 0);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        if (view != null)
            view.startAnimation(animation);
    }

    public static void animScaleIn(View view){
//        ScaleAnimation animation = new ScaleAnimation();

    }
}
