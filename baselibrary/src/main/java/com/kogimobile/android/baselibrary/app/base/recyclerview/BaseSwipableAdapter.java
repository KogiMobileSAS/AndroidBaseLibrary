package com.kogimobile.android.baselibrary.app.base.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

/**
 * @author Pedro Scott. pedro@kogimobile.com
 * @version 9/4/16. *
 *          Copyright 2015 Kogi Mobile
 *          <p>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p>
 *          http://www.apache.org/licenses/LICENSE-2.0
 *          <p>
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 */
public abstract class BaseSwipableAdapter<T, H extends BaseAdapter.BaseViewHolder> extends BaseAdapter<T, H> {

    private int whereItemStartToMove;
    private int whereItemEndToMove;
    private boolean callbackMoved;
    private boolean callbackSwiped;
    private ItemTouchHelperAdapter<T> itemTouchHelperAdapter;

    public void setCallbackMoved(boolean callbackMoved) {
        this.callbackMoved = callbackMoved;
    }

    public void setCallbackSwiped(boolean callbackSwiped) {
        this.callbackSwiped = callbackSwiped;
    }

    public boolean isCallbackMoved() {
        return callbackMoved;
    }

    public boolean isCallbackSwiped() {
        return callbackSwiped;
    }

    private boolean changeItemsByPosition(int adapterPositionStart, int adapterPositionEnd) {
        if (isHeaderEnabled() && adapterPositionEnd == 0) {
            return false;
        }
        if (adapterPositionEnd > getItems().size()) {
            return false;
        }
        if (adapterPositionStart < adapterPositionEnd) {
            for (int i = getItemPosition(adapterPositionStart); i < getItemPosition(adapterPositionEnd); i++) {
                Collections.swap(getItems(), i, i + 1);
            }
        } else {
            for (int i = getItemPosition(adapterPositionStart); i > getItemPosition(adapterPositionEnd); i--) {
                Collections.swap(getItems(), i, i - 1);
            }
        }
        whereItemEndToMove = adapterPositionEnd;
        notifyItemMoved(adapterPositionStart, adapterPositionEnd);
        if (getItemTouchHelperAdapter() != null) {
            T itemFrom = getItems().get(getItemPosition(adapterPositionStart));
            T itemTarget = getItems().get(getItemPosition(adapterPositionEnd));
            getItemTouchHelperAdapter().onItemMoved(adapterPositionStart,
                    getItemPosition(adapterPositionStart),
                    itemFrom,
                    getItemPosition(adapterPositionEnd),
                    adapterPositionEnd,
                    itemTarget);
        }
        return true;
    }

    /**
     * Insert an item in list by position.
     *
     * @param position       is the position adapter of the items.
     * @param itemsToDismiss is the item that you previews remove
     */
    public void undoRemovedItem(int position, T itemsToDismiss) {
        addItemAtPosition(itemsToDismiss, position);
    }

    public boolean undoLastItemsChangesPosition() {
        return changeItemsByPosition(whereItemEndToMove, whereItemStartToMove);
    }

    public ItemTouchHelperAdapter<T> getItemTouchHelperAdapter() {
        return itemTouchHelperAdapter;
    }

    public void addItemTouchHelperAdapter(RecyclerView recyclerView, ItemTouchHelperAdapter<T> itemTouchHelperAdapter, boolean activeSwipe, boolean activeMove) {
        this.itemTouchHelperAdapter = itemTouchHelperAdapter;
        setCallbackMoved(activeMove);
        setCallbackSwiped(activeSwipe);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public interface ItemTouchHelperAdapter<T> {

        void onItemMoved(int fromAdapterPosition, int fromItemPosition, T itemOrigin, int toAdapterPosition, int toItemsPosition, T itemTarget);

        void onItemDismissed(int position, T item);
    }

    /**
     * Move Items
     */
    public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = 0;
            if (isCallbackMoved()) {
                dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            int swipeFlags = 0;
            if (isCallbackSwiped()) {
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            }
            if ((isHeaderEnabled() && viewHolder.getAdapterPosition() == 0)
                    || getItems().size() == 0) {
                return 0;
            }
            whereItemStartToMove = viewHolder.getAdapterPosition();
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            return changeItemsByPosition(viewHolder.getAdapterPosition(),
                    target.getAdapterPosition());
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (!(isHeaderEnabled() && viewHolder.getAdapterPosition() == 0)) {
                int position = getItemPosition(viewHolder.getAdapterPosition());
                T item = getItems().get(position);
                getItems().remove(position);
                notifyItemRemoved(position + headerViewCount());
                if (getItemTouchHelperAdapter() != null) {
                    getItemTouchHelperAdapter().onItemDismissed(position,
                            item);
                }
            }
        }
    }

}
