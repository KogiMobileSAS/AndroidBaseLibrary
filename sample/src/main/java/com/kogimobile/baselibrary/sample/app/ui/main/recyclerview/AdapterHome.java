package com.kogimobile.baselibrary.sample.app.ui.main.recyclerview;

import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.recyclerview.BaseAdapter;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.entities.Theme;

import butterknife.BindView;

/**
 * Created by juliancardona on 3/11/17.
 */

public class AdapterHome extends BaseAdapter<Theme, BaseAdapter.BaseViewHolder<Theme>> {

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_empty, parent, false));
            }
            case LOAD_MORE_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_loading_more, parent, false));
            }
            case LOADING_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_loading, parent, false));
            }
            default:
                return new ThemeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme, parent, false));
        }
    }

    class ThemeHolder extends BaseViewHolder<Theme> {

        @BindView(R.id.tVThemeName)
        AppCompatTextView tVThemeName;

        public ThemeHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Theme item) {
            tVThemeName.setText(String.format("Theme #%d",getAdapterPosition()));
        }

    }

}