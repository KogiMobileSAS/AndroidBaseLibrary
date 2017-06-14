package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseAdapter;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.entities.Item;

import butterknife.BindView;

/**
 * Created by juliancardona on 3/11/17.
 */

public class AdapterItems extends BaseAdapter<Item, BaseAdapter.BaseViewHolder<Item>> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_empty, parent, false));
            }
            case LOAD_MORE_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loading_more, parent, false));
            }
            case LOADING_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_loading, parent, false));
            }
            default:
                return new ThemeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
        }
    }

    class ThemeHolder extends BaseViewHolder<Item> {

        @BindView(R.id.tVListItemName)
        AppCompatTextView name;

        public ThemeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Item item) {
            name.setText(getContext().getString(R.string.frg_recycler_view_item_name,getAdapterPosition()));
        }

    }

}