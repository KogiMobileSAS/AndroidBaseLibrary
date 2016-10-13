package com.kogimobile.android.baselibrary.app.base.adapter;

import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author pedroscott. scott7462@gmail.com
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
public abstract class BaseFilterSimpleAdapter<T, H extends BaseSimpleAdapter.BaseViewHolder> extends BaseSimpleAdapter<T, H>
        implements Filterable {

    private int minNumberToCleanFilter = 0;

    public void setMinNumberToCleanFilter(int minNumberToCleanFilter) {
        this.minNumberToCleanFilter = minNumberToCleanFilter;
    }

    private AdapterFilter filter = null;

    public void filterItems(String query) {
        getFilter().filter(query);
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AdapterFilter(getItems());
        return filter;
    }


    class AdapterFilter extends Filter {

        private final List<T> originalList;
        private final List<T> filteredItemList;

        public AdapterFilter(List<T> items) {

            this.originalList = new LinkedList<>(items);
            this.filteredItemList = new ArrayList<>();
        }

        private List<T> getFilteredItemList() {
            return filteredItemList;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            getFilteredItemList().clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == minNumberToCleanFilter) {
                getFilteredItemList().addAll(originalList);
            } else {
                for (final T item : getItems()) {
                    if (searchCondition(item, constraint.toString())) {
                        getFilteredItemList().add(item);
                    }
                }
            }
            results.values = getFilteredItemList();
            results.count = getFilteredItemList().size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cleanItemsAndUpdate((List<T>) results.values);
            notifyDataSetChanged();
        }
    }

    protected abstract boolean searchCondition(T item, String query);


}
