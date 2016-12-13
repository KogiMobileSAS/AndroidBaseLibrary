package com.kogimobile.android.baselibrary.app.base.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author pedroscott. scott7462@gmail.com
 * @modified Julian Cardona. julian@kogimobile.com
 * @version 9/4/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class BaseSimpleAdapter<T, H extends BaseSimpleAdapter.BaseViewHolder> extends RecyclerView.Adapter<H> {

    protected static final int EMPTY_VIEW = 2000;
    protected static final int HEADER_VIEW = 3000;
    protected static final int LOADING_VIEW = 4000;
    protected static final int LOAD_MORE_VIEW = 5000;

    private List<T> items = new ArrayList<>();
    private onItemClickListener<T> clickListener;
    private ItemTouchHelperAdapter<T> itemTouchHelperAdapter;

    private int whereItemStartToMove;
    private int whereItemEndToMove;
    private boolean callbackMoved;
    private boolean callbackSwiped;

    private boolean headerView;
    private boolean isLoadMore;
    private boolean entryState = true;
    private boolean loadingState;

    public List<T> getItems() {
        return items;
    }

    @Override
    public int getItemCount() {
        validateItemsNullAndCreate();
        if (items.size() == 0) {
            return getPositionByRules();
        } else {
            return items.size() + getPositionByRules() + (haveLoadMoreView() ? 1 : 0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        validateItemsNullAndCreate();

        if (haveToShowMore(position)) {
            return LOAD_MORE_VIEW;
        }

        if (haveAdapterHeaderView() && isEmptyState()) {
            if (items.size() == 0 && position == 1) {
                return isLoadingStateEnabled() ? LOADING_VIEW : EMPTY_VIEW;
            }
            if (items.size() >= 0 && position == 0) {
                return isLoadingStateEnabled() ? LOADING_VIEW : HEADER_VIEW;
            }
        }

        if (isEmptyState() && items.size() == 0) {
            return isLoadingStateEnabled() ? LOADING_VIEW : EMPTY_VIEW;
        }

        if (haveToShowHeader(position)) {
            return HEADER_VIEW;
        }

        return super.getItemViewType(position);

    }

    private boolean haveToShowHeader(int position) {
        return haveAdapterHeaderView() && items.size() >= 0 && position == 0;
    }

    private boolean haveToShowMore(int position) {
        return (position >= items.size() + getPositionByRules()) && haveLoadMoreView() && items.size() > 0;
    }

    private void validateItemsNullAndCreate() {
        if (items == null) {
            items = new ArrayList<>();
        }
    }

    /**
     * This method find an item adapter by position.
     *
     * @param position Where the item is in the list of base items.
     * @return The item that found in the position or null in case to don't founded.
     */
    public T getItem(int position) {
        if (items != null && position - getPositionByRules() < items.size()) {
            return items.get(position - getPositionByRules());
        }
        return null;
    }

    /**
     * This method clear the base list items and add the new list of items.
     * if you past a null the list of items go to create a new entry list.
     *
     * @param list list of items to insert in the base list of items in the adapter.
     */
    public void cleanItemsAndUpdate(@Nullable List<T> list) {
        validateItemsNullAndCreate();
        items.clear();
        addItems(list);
        notifyDataSetChanged();
    }

    /**
     * This method add the new list of items if you past a null.
     * the list of items go to create a new entry list.
     *
     * @param list list of items to add in the base list of items in the adapter.
     */
    public void addItems(@Nullable List<T> list) {
        validateItemsNullAndCreate();
        this.items.addAll(list == null ? new ArrayList<T>() : list);
        notifyDataSetChanged();
    }

    /**
     * This method find and remove an item adapter by position.
     *
     * @param item to remove in list.
     * @return The true in case to find and remove the items.
     */
    public boolean removeItem(@NonNull T item) {
        validateItemsNullAndCreate();
        if (items.remove(item)) {
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * This method find and remove an item adapter by position
     *
     * @param position Where the item is in the list of base items.
     * @return The true in case to find and remove the items.
     */
    public boolean removeItemByPosition(int position) {
        validateItemsNullAndCreate();
        if (position < items.size()) {
            items.remove(position);
            notifyItemRemoved(position + getPositionByRules());
            return true;
        }
        return false;
    }

    /**
     * This method add an item adapter and notify all the adapter.
     *
     * @param item the item to insert in list.
     */
    public void addItem(@NonNull T item) {
        validateItemsNullAndCreate();
        items.add(item);
        notifyItemInserted(getItemPosition(getItemCount()));
    }


    /**
     * This method add an item adapter and notify all the adapter.
     *
     * @param item the item to insert in list.
     */
    public void updateItemByPosition(int position, @NonNull T item) {
        validateItemsNullAndCreate();
        items.set(position, item);
        notifyItemChanged(position);
    }

    /**
     * This method add an item adapter by position
     *
     * @param item     the item to insert in list.
     * @param position the position to insert in list
     */
    public void addItemByPosition(@NonNull T item, int position) {
        validateItemsNullAndCreate();
        if (position < items.size()) {
            items.add(position, item);
            notifyItemInserted(position + getPositionByRules());
        }
    }

    public onItemClickListener<T> getClickListener() {
        return clickListener;
    }

    public void addClickListener(onItemClickListener<T> clickListener) {
        this.clickListener = clickListener;
    }

    private int getPositionByRules() {
        return ((isEmptyState() && items.size() == 0) ? 1 : 0) + (haveAdapterHeaderView() ? 1 : 0);
    }

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

    public interface onItemClickListener<T> {
        void onItemViewsClick(T item, int position);
    }


    /**
     * Call the click item  Listener with the position of the item in the adapter and call
     * the @onItemViewsClick() executing the position rules and giving to the listener the position
     * in the list items and the current item.
     *
     * @param adapterPosition Receive the position of the adapter.
     */
    public void callItemListenerByPosition(int adapterPosition) {
        if (getClickListener() != null) {
            getClickListener().onItemViewsClick(items.get(getItemPosition(adapterPosition)), getItemPosition(adapterPosition));
        }
    }

    /**
     * Get position of the item in the list.
     *
     * @param adapterPosition Receive the position of the adapter.
     * @return The Position of the items in the list.
     */
    public int getItemPosition(int adapterPosition) {
        return adapterPosition - getPositionByRules();
    }

    /**
     * Entry State Elements.
     */
    public boolean isEmptyState() {
        return entryState;
    }

    /**
     * Set the entry state value
     *
     * @param entryState Is true if you want user a entry state by default is true.
     */
    public void showEmptyState(boolean entryState) {
        this.entryState = entryState;
    }

    protected static class EmptyViewHolder<T> extends BaseViewHolder<T> {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

        @CallSuper
        @Override
        public void bindView(T item) {

        }

        @CallSuper
        protected void bindView() {

        }
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void bindView(T item);
    }

    /**
     * Header element.
     */

    public boolean haveAdapterHeaderView() {
        return headerView;
    }

    /**
     * Set the entry state value in constructor builder
     *
     * @param headerView Is true if you want user a entry state by default is true.
     */
    public void showHeaderView(boolean headerView) {
        this.headerView = headerView;
    }

    /**
     * Loading elements.
     */
    public boolean isLoadingStateEnabled() {
        return loadingState;
    }

    /**
     * Set the entry state value in constructor builder
     *
     * @param loadingState Is true if you want to show loading state.
     */
    public void showLoadingState(boolean loadingState) {
        this.loadingState = loadingState;
    }

    /**
     * Load More.
     */
    public boolean haveLoadMoreView() {
        return isLoadMore;
    }

    /**
     * Set the load more
     *
     * @param isLoadMore Is true if you want add a load more view in the bottom of the list.
     */
    public void showLoadMoreView(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        notifyDataSetChanged();
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
            if ((haveAdapterHeaderView() && viewHolder.getAdapterPosition() == 0)
                    || items.size() == 0) {
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
            if (!(haveAdapterHeaderView() && viewHolder.getAdapterPosition() == 0)) {
                int position = getItemPosition(viewHolder.getAdapterPosition());
                T item = items.get(position);
                items.remove(position);
                notifyItemRemoved(position + getPositionByRules());
                if (getItemTouchHelperAdapter() != null) {
                    getItemTouchHelperAdapter().onItemDismissed(position,
                            item);
                }
            }
        }
    }

    private boolean changeItemsByPosition(int adapterPositionStart, int adapterPositionEnd) {
        if (haveAdapterHeaderView() && adapterPositionEnd == 0) {
            return false;
        }
        if (adapterPositionEnd > items.size()) {
            return false;
        }
        if (adapterPositionStart < adapterPositionEnd) {
            for (int i = getItemPosition(adapterPositionStart); i < getItemPosition(adapterPositionEnd); i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = getItemPosition(adapterPositionStart); i > getItemPosition(adapterPositionEnd); i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        whereItemEndToMove = adapterPositionEnd;
        notifyItemMoved(adapterPositionStart, adapterPositionEnd);
        if (getItemTouchHelperAdapter() != null) {
            T itemFrom = items.get(getItemPosition(adapterPositionStart));
            T itemTarget = items.get(getItemPosition(adapterPositionEnd));
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
        addItemByPosition(itemsToDismiss, position);
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
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback();
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public interface ItemTouchHelperAdapter<T> {

        void onItemMoved(int fromAdapterPosition, int fromItemPosition, T itemOrigin, int toAdapterPosition, int toItemsPosition, T itemTarget);

        void onItemDismissed(int position, T item);
    }

    public List<T> getItemsByCondition() {
        List<T> selectedItems = new ArrayList<>();
        for (T t : getItems()) {
            if (ifValidCondition(t))
                selectedItems.add(t);
        }
        return selectedItems;
    }

    public boolean ifValidCondition(T t){
        return true;
    }

    @CallSuper
    @Override
    public void onBindViewHolder(H holder, int position) {
        if(holder instanceof EmptyViewHolder){
            ((EmptyViewHolder) holder).bindView();
        }else{
            holder.bindView(items.get(getItemPosition(position)));
        }
    }
}
