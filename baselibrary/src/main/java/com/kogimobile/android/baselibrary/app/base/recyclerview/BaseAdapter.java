package com.kogimobile.android.baselibrary.app.base.recyclerview;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @author Pedro Scott. scott7462@gmail.com
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
 * @modified Julian Cardona. julian@kogimobile.com
 */
public abstract class BaseAdapter<T, H extends BaseAdapter.BaseViewHolder> extends RecyclerView.Adapter<H> {

    protected static final int EMPTY_VIEW = 2000;
    protected static final int HEADER_VIEW = 3000;
    protected static final int LOADING_VIEW = 4000;
    protected static final int LOAD_MORE_VIEW = 5000;

    private boolean isLoading = true;
    private boolean isLoadingMore;
    private boolean isRefreshing;
    private boolean isHeaderEnabled;
    private boolean isLoadEnabled = true;
    private boolean isLoadMoreEnabled;
    private boolean isRefreshEnabled;
    private List<T> items = new ArrayList<>();
    private BaseAdapter.onItemClickListener<T> clickListener;

    public List<T> getItems() {
        return this.items;
    }

    @Override
    public int getItemCount() {
        validateItemsNullAndCreate();
        if (getItems().size() == 0) {
            return headerViewCount() + loadViewCount();
        } else {
            return  headerViewCount() + getItems().size() + loadMoreViewCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        validateItemsNullAndCreate();

        if(position == 0){
            if(isHeaderEnabled()){
                return HEADER_VIEW;
            } else if(isLoadEnabled() && isLoading() && getItems().size() == 0){
                return LOADING_VIEW;
            }else if (items.size() == 0){
                return EMPTY_VIEW;
            }else{
                return super.getItemViewType(position);
            }
        }else if(position == 1){
            if(isLoadEnabled() && isLoading() && getItems().size() == 0){
                return LOADING_VIEW;
            }else if(items.size() == 0){
                return EMPTY_VIEW;
            }else if(isLoadMoreEnabled() && isLoadingMore() && position == headerViewCount() + getItems().size() - 1 + loadMoreViewCount()){
                return LOAD_MORE_VIEW;
            }else{
                return super.getItemViewType(position);
            }
        }else{
            if (isLoadMoreEnabled() && isLoadingMore() && position == headerViewCount() + getItems().size() - 1 + loadMoreViewCount()) {
                return LOAD_MORE_VIEW;
            } else {
                return super.getItemViewType(position);
            }
        }
    }

    protected void validateItemsNullAndCreate() {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
    }

    /**
     * This method find an item adapter by position.
     *
     * @param position Where the item is in the list of base items.
     * @return The item that found in the position or null in case to don't founded.
     */
    public T getItem(int position) {
        if (getItems() != null && position - headerViewCount() < getItems().size()) {
            return getItems().get(position - headerViewCount());
        }
        return null;
    }

    /**
     * This method add the new list of items if you past a null.
     * the list of items go to create a new entry list.
     *
     * @param list list of items to add in the base list of items in the adapter.
     */
    public void addItems(@Nullable List<T> list) {
        validateItemsNullAndCreate();
        if(list != null && list.size() > 0) {
            int startRange = getItemCount() + 1;
            getItems().addAll(list);
            notifyItemRangeInserted(startRange,list.size());
        }
    }

    /**
     * This method clear the base list items and add the new list of items.
     * if you past a null the list of items go to create a new entry list.
     *
     * @param list list of items to insert in the base list of items in the adapter.
     */
    public void refreshItems(@Nullable List<T> list) {
        validateItemsNullAndCreate();
        clearItems();
        getItems().addAll(list);
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
        int index = getItems().indexOf(item);
        if (index != -1) {
            getItems().remove(item);
            notifyItemRemoved(index);
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
        getItems().add(item);
        notifyItemInserted(getItemPosition(getItemCount()));
    }

    /**
     * This method add an item adapter by position
     *
     * @param item     the item to insert in list.
     * @param position the position to insert in list
     */
    public void addItemAtPosition(@NonNull T item, int position) {
        validateItemsNullAndCreate();
        if (position < getItems().size()) {
            getItems().add(position, item);
            notifyItemInserted(position + headerViewCount());
        }
    }

    /**
     * This method add an item adapter and notify all the adapter.
     *
     * @param item the item to insert in list.
     */
    public void updateItemAtPosition(int position, @NonNull T item) {
        validateItemsNullAndCreate();
        getItems().set(position, item);
        notifyItemChanged(position);
    }

    /**
     * This method find and remove an item adapter by position
     *
     * @param position Where the item is in the list of base items.
     * @return The true in case to find and remove the items.
     */
    public boolean removeItemAtPosition(int position) {
        validateItemsNullAndCreate();
        if (position < getItems().size()) {
            getItems().remove(position);
            notifyItemRemoved(position + headerViewCount());
            return true;
        }
        return false;
    }

    /**
     * Call the click item  Listener with the position of the item in the adapter and call
     * the @onItemViewsClick() executing the position rules and giving to the listener the position
     * in the list items and the current item.
     *
     * @param adapterPosition Receive the position of the adapter.
     */
    public void callItemListenerAtPosition(int adapterPosition) {
        if (getClickListener() != null) {
            getClickListener().onItemViewsClick(getItems().get(getItemPosition(adapterPosition)), getItemPosition(adapterPosition));
        }
    }

    public int headerViewCount(){
        return isHeaderEnabled() ? 1 : 0;
    }

    public int loadViewCount(){
        return isLoadEnabled() && isLoading() && getItems().size() == 0 ? 1 : 0;
    }

    public int loadMoreViewCount(){
        return isLoadMoreEnabled() && isLoadingMore() ? 1 : 0;
    }

    /**
     * Get position of the item in the list.
     *
     * @param adapterPosition Receive the position of the adapter.
     * @return The Position of the items in the list.
     */
    public int getItemPosition(int adapterPosition) {
        return adapterPosition - headerViewCount();
    }

    public void checkLoadingViewState(){
        int loadingViewPosition = headerViewCount();
        if(isLoading()) {
            notifyItemInserted(getItemCount());
        }else{
            notifyItemRemoved(loadingViewPosition);
        }
    }

    public void checkLoadingMoreViewState(){
        int loadingMoreViewPosition = headerViewCount() + getItems().size();
        if(isLoadingMore()){
            notifyItemInserted(getItemCount());
        }
        else{
            notifyItemRemoved(loadingMoreViewPosition);
        }
    }

    public void clearItems(){
        validateItemsNullAndCreate();
        getItems().clear();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public boolean isLoadingMore() {
        return this.isLoadingMore;
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public void setLoadingMore(boolean loadingMore) {
        this.isLoadingMore = loadingMore;
    }

    public void setRefreshing(boolean refreshing) {
        this.isRefreshing = refreshing;
    }

    public boolean isHeaderEnabled() {
        return this.isHeaderEnabled;
    }

    public boolean isLoadEnabled() {
        return this.isLoadEnabled;
    }

    public boolean isLoadMoreEnabled() {
        return this.isLoadMoreEnabled;
    }

    public boolean isRefreshEnabled() {
        return isRefreshEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.isHeaderEnabled = headerEnabled;
    }

    public void setLoadEnabled(boolean loadEnabled) {
        this.isLoadEnabled = loadEnabled;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.isLoadMoreEnabled = loadMoreEnabled;
    }

    public void setRefreshEnabled(boolean refreshEnabled) {
        isRefreshEnabled = refreshEnabled;
    }

    public BaseAdapter.onItemClickListener<T> getClickListener() {
        return this.clickListener;
    }

    public void addClickListener(BaseAdapter.onItemClickListener<T> clickListener) {
        this.clickListener = clickListener;
    }

    public interface onItemClickListener<T> {
        void onItemViewsClick(T item, int position);
    }

    @CallSuper
    @Override
    public void onBindViewHolder(H holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).bindView();
        } else {
            int pos = getItemPosition(position);
            holder.bindView(getItems().get(pos));
        }
    }

    protected static class EmptyViewHolder<T> extends BaseAdapter.BaseViewHolder<T> {

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

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void bindView(T item);
    }
}