package com.kogimobile.android.baselibrary.app.base.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.kogimobile.android.baselibrary.R;
import com.kogimobile.android.baselibrary.app.base.BaseActivity;

/**
 * @author Julian Cardona on 6/15/17.
 */

public class BaseNavigationController {

    private final int containerId;
    private final FragmentManager fragmentManager;

    public BaseNavigationController(BaseActivity activity) {
        this.containerId = R.id.container;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public int getContainerId() {
        return containerId;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    protected void navigateTo(Fragment fragment, int containerId){
        navigateTo(
                fragment,
                containerId,
                fragment.getClass().getSimpleName(),
                false,
                false
        );
    }

    protected void navigateTo(Fragment fragment, int containerId, boolean addToBackStack){
        navigateTo(
                fragment,
                containerId,
                fragment.getClass().getSimpleName(),
                addToBackStack,
                false
        );
    }

    private void navigateTo(Fragment fragment, int containerId, String fragmentTag, boolean addToBackStack, boolean allowCommitStateLoss){
        if(getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            if (addToBackStack) {
                ft.addToBackStack(fragmentTag);
            }

            ft.replace(containerId, fragment, fragmentTag);

            if (allowCommitStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        }
    }

    public void cleanFragmentStack() {
        if(getFragmentManager() != null) {
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
