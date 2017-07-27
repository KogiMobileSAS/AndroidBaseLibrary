package com.kogimobile.android.baselibrary.app.base.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Julian Cardona on 6/15/17.
 */

public class BaseNavigationController {

    private final FragmentManager fragmentManager;

    public BaseNavigationController(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
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
