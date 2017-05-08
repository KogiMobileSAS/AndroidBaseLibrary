package com.kogimobile.android.baselibrary.navigation;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentNavigator {

    public static void navigateTo(FragmentManager manager, Fragment fragment, int containerId){
        navigateTo(
                manager,
                fragment,
                containerId,
                fragment.getClass().getSimpleName(),
                false,
                false
        );
    }

    public static void navigateTo(FragmentManager manager, Fragment fragment, int containerId,boolean addToBackStack){
        navigateTo(
                manager,
                fragment,
                containerId,
                fragment.getClass().getSimpleName(),
                addToBackStack,
                false
        );
    }

    public static void navigateTo(FragmentManager manager,
                                  Fragment fragment,
                                  int containerId,
                                  String fragmentTag,
                                  boolean addToBackStack,
                                  boolean allowCommitStateLoss){
        FragmentTransaction ft = manager.beginTransaction();
        ft.addToBackStack(fragmentTag);
        ft.replace(containerId, fragment,fragmentTag);
        if(allowCommitStateLoss){
            ft.commitAllowingStateLoss();
        }
        else {
            ft.commit();
        }
        if(addToBackStack) {

        }
    }

    public static void cleanFragmentStack(FragmentManager fm) {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
