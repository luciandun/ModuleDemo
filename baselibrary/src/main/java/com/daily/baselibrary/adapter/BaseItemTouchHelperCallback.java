package com.daily.baselibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * description:RecyclerView手势操作封装
 * author: dlx
 * date: 2018/5/14
 * version: 1.0
 */
public class BaseItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter adapter;
    private OnSelectEndListener onEndListener;
    private boolean canDrag;
    private boolean canSwipe;

    public void setOnSelectEndListener(OnSelectEndListener onEndListener) {
        this.onEndListener = onEndListener;
    }

    /**
     * 默认支持左右上下操作
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.START | ItemTouchHelper.END);
    }

    /**
     * 用于交换两个item位置
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        adapter.onItemSwap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                        int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
    }

    /**
     * 左右上下滑动处理
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.onItemDelete(viewHolder.getAdapterPosition());
    }

    /**
     * 是否支持长按拖动
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return canDrag;
    }

    /**
     * 是否支持左右、上下滑动
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return canSwipe;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder == null) {
            if (onEndListener != null)
                onEndListener.onSelectEnd();
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public interface OnSelectEndListener {
        void onSelectEnd();
    }

    public interface ItemTouchHelperAdapter {
        void onItemTop(int fromPosition);

        void onItemDelete(int position);

        void onItemSwap(int itemAPosition, int itemBPosition);
    }

}
