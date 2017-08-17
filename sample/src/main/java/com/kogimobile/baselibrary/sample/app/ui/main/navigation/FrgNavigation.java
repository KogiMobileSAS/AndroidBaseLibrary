package com.kogimobile.baselibrary.sample.app.ui.main.navigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.ActivityMain;
import com.kogimobile.baselibrary.sample.databinding.FrgNavigationBinding;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgNavigation extends BaseFragment implements EventHandlerNavigation{

    public static final String ARG_NAVIGATION_LEVEL = "ARG_NAVIGATION_LEVEL";

    private FrgNavigationBinding binding;

    public static FrgNavigation newInstance(int navigationLevel) {
        FrgNavigation frg = new FrgNavigation();
        Bundle args = new Bundle();
        args.putInt(ARG_NAVIGATION_LEVEL,navigationLevel);
        frg.setArguments(args);
        return frg;
    }

    private int navigationLevel;

    @Override
    protected void initVars() {
        if(getArguments().containsKey(ARG_NAVIGATION_LEVEL)){
            this.navigationLevel = getArguments().getInt(ARG_NAVIGATION_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.frg_navigation, container, false);
        this.binding.setEventHandler(this);
        return this.binding.getRoot();
    }

    @Override
    protected void initViews() {
        setupNavigationLevel();
        checkNavigateRootVisibility();
    }

    @Override
    protected void initListeners() {

    }

    private void setupNavigationLevel(){
        this.binding.description.setText(getString(R.string.frg_navigation_description,navigationLevel));
    }

    private void checkNavigateRootVisibility(){
        if(navigationLevel > 0 ){
            this.binding.buttonRoot.setVisibility(View.VISIBLE);
        }else{
            this.binding.buttonRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickRoot() {
        ((ActivityMain)getActivity()).navigateBackRootLevel();
    }

    @Override
    public void onClickLowLevel() {
        int stackCount = getActivity().getSupportFragmentManager().getBackStackEntryCount() + 1;
        ((ActivityMain)getActivity())
                .navigateSection1LowLevel(
                        FrgNavigation.newInstance(stackCount),
                        String.format("%s %d",getString(R.string.frg_navigation_title),stackCount)
                );
    }
}
