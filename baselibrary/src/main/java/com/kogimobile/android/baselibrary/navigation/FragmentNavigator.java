package com.kogimobile.android.baselibrary.navigation;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

public class FragmentNavigator {

    public static void navigateTo(FragmentManager manager, Fragment newFragment, int containerId, FragmentNavigatorOptions options){
        FragmentTransaction ft = manager.beginTransaction();

        if (options.getFragmentCustomAnimation()!=null){
            ft.setCustomAnimations(
                    options.getFragmentCustomAnimation().getEnter(),
                    options.getFragmentCustomAnimation().getExit(),
                    options.getFragmentCustomAnimation().getPopEnter(),
                    options.getFragmentCustomAnimation().getPopExit());
        }

        if(options.isNoHistoryForCurrent())
            manager.popBackStack();

        if(TextUtils.isEmpty(options.getTag())){
            options.setTag(newFragment.getClass().getSimpleName());
        }
        ft.replace(containerId, newFragment, options.getTag());

        if(options.isAddingToStack()) {
            ft.addToBackStack(options.getTag());
        }

        if(options.isAllowingStateLoss()) {
            ft.commitAllowingStateLoss();
        }
        else {
            ft.commit();
        }
    }

    public static void cleanFragmentStack(FragmentManager fm){
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
