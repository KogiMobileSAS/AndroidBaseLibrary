package com.kogimobile.baselibrary.sample.app.ui.main.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kogimobile.android.baselibrary.app.base.BaseActivity;
import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.baselibrary.sample.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgNavigation extends BaseFragment {

    public static final String ARG_NAVIGATION_LEVEL = "ARG_NAVIGATION_LEVEL";

    public static FrgNavigation newInstance(int navigationLevel) {
        FrgNavigation frg = new FrgNavigation();
        Bundle args = new Bundle();
        args.putInt(ARG_NAVIGATION_LEVEL,navigationLevel);
        frg.setArguments(args);
        return frg;
    }

    @BindView(R.id.bFrgNavigationDescription)
    TextView bFrgNavigationDescription;
    @BindView(R.id.bFrgNavigationRoot)
    Button bFrgNavigationRoot;

    private int navigationLevel;

    @Override
    protected void initVars() {
        if(getArguments().containsKey(ARG_NAVIGATION_LEVEL)){
            this.navigationLevel = getArguments().getInt(ARG_NAVIGATION_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_navigation, container, false);
    }

    @Override
    protected void initViews() {
        setupNavigationLevel();
        checkNavigateRootvisivility();
    }

    @Override
    protected void initListeners() {

    }

    private void setupNavigationLevel(){
        bFrgNavigationDescription.setText(getString(R.string.frg_navigation_description,navigationLevel));
    }

    private void checkNavigateRootvisivility(){
        if(navigationLevel > 0 ){
            bFrgNavigationRoot.setVisibility(View.VISIBLE);
        }else{
            bFrgNavigationRoot.setVisibility(View.GONE);
        }
    }

    @OnClick({
            R.id.bFrgNavigationRoot,
            R.id.bFrgNavigationLowLevel
    })
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.bFrgNavigationRoot:
                ((BaseActivity)getActivity()).navigateBackRootLevel();
                break;
            case R.id.bFrgNavigationLowLevel:
                ((BaseActivity)getActivity())
                        .navigateToActivityLowLevel(
                                FrgNavigation.newInstance(getActivity().getSupportFragmentManager().getBackStackEntryCount() + 1),
                                R.id.container,
                                getString(R.string.frg_navigation_title));
                break;
        }
    }

}
