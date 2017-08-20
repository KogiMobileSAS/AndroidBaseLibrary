package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseAdapter;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.ListItemBinding;
import com.kogimobile.baselibrary.sample.entities.Item;

/**
 * Created by juliancardona on 3/11/17.
 */

public class AdapterItems extends BaseAdapter<Item, BaseAdapter.BaseViewHolder<Item>> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        switch (viewType) {
            case EMPTY_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_empty, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            case LOAD_MORE_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_loading_more, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            case LOADING_VIEW: {
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_loading, parent, false);
                return new EmptyViewHolder(binding.getRoot());
            }
            default:
                binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
                return new ThemeHolder(binding.getRoot());
        }
    }

    class ThemeHolder extends BaseViewHolder<Item> {

        public ThemeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected ListItemBinding getBinding() {
            return super.getBinding();
        }

        @Override
        protected void bindView(Item item) {
            getBinding().itemName.setText(getContext().getString(R.string.frg_recycler_view_item_name,getAdapterPosition()));
        }

    }

}