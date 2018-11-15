package com.daily.baselibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * description:封装通用的RecyclerView Adapter,方便数据调用
 * author: dlx
 * date: 2018/5/11
 * version: 1.0
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter
        implements BaseItemTouchHelperCallback.ItemTouchHelperAdapter {

    private List<T> dataList;
    private Context mContext;
    private int itemLayoutId;

    public abstract void bindData(BaseViewHolder viewHolder, T data, int type, int position);

    public BaseRVAdapter(Context context, int itemLayoutId) {
        this.mContext = context;
        this.itemLayoutId = itemLayoutId;
        dataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseViewHolder.newInstance(mContext, itemLayoutId, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindData((BaseViewHolder) holder, dataList.get(position), holder.getItemViewType(), position);
    }

    @Override
    public int getItemCount() {
        return dataList.isEmpty() ? 0 : dataList.size();
    }


    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        dataList.clear();
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加某个数据
     *
     * @param data
     */
    public void addData(T data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加某个集合数据
     *
     * @param data
     */
    public void addData(List<T> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清除adapter数据
     */
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }

    /**
     * 删除某个数据
     *
     * @param data
     */
    public void delData(T data) {
        dataList.remove(data);
        notifyDataSetChanged();
    }

    /**
     * 将某个item置顶
     *
     * @param fromPosition
     */
    @Override
    public void onItemTop(int fromPosition) {
        T data = dataList.get(fromPosition);
        for (int i = fromPosition; i > 0; i--) {
            dataList.set(i, dataList.get(i - 1));
        }
        dataList.set(0, data);
        notifyItemMoved(fromPosition, 0);
    }

    /**
     * 拖动交换数据
     *
     * @param itemAPosition
     * @param itemBPosition
     */
    @Override
    public void onItemSwap(int itemAPosition, int itemBPosition) {
        Collections.swap(dataList, itemAPosition, itemBPosition);
        notifyItemMoved(itemAPosition, itemBPosition);
    }

    /**
     * 删除某个item
     *
     * @param position
     */
    @Override
    public void onItemDelete(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

}
