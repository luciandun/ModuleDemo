package com.daily.baselibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO 考虑加上viewType该如何实现
 * description:封装底层ViewHolder
 * author: dlx
 * date: 2018/5/14
 * version: 1.0
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private View mItemView;
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }

    /**
     * @param context
     * @param itemLayoutId
     * @param parent
     * @return
     */
    public static BaseViewHolder newInstance(Context context, int itemLayoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
        return new BaseViewHolder(itemView);
    }

    /**
     * 根据itemLayoutId中某个控件id获得这个view
     *
     * @param elementId 控件id
     * @param <T>       控件View
     * @return 控件View
     */
    public <T extends View> T getView(int elementId) {
        View view = mViews.get(elementId);
        if (view == null) {
            view = mItemView.findViewById(elementId);
            mViews.put(elementId, view);
        }
        return (T) view;
    }


}
