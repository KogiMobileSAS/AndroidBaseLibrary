package com.kogimobile.android.baselibrary.app.base.navigation;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.kogimobile.android.baselibrary.app.base.BaseActivityInnerNavigation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BaseActivityInnerNavigationController extends BaseActivityNavigationController {

    private final int innerNavContainerId;
    private ArrayList<String> titleStack = new ArrayList<>();
    private WeakReference<BaseActivityInnerNavigation> weakRefActivity;

    public int getInnerNavContainerId() {
        return innerNavContainerId;
    }

    public ArrayList<String> getTitleStack() {
        return titleStack;
    }

    public BaseActivityInnerNavigationController(BaseActivityInnerNavigation weakRefActivity, @IdRes int innerNavContainerId) {
        super(weakRefActivity.getSupportFragmentManager());
        this.weakRefActivity = new WeakReference<>(weakRefActivity);
        this.innerNavContainerId = innerNavContainerId;
    }

    @CallSuper
    protected void navigateToRootLevel(Fragment frg,String title) {
        BaseFragmentNavigator.cleanFragmentStack(getFragmentManager());
        BaseFragmentNavigator.navigateTo(getFragmentManager(), frg, getInnerNavContainerId());
        getTitleStack().clear();
        getTitleStack().add(title);
        this.weakRefActivity.get().updateActionBarTitle();
    }

    @CallSuper
    public void navigateBackRootLevel() {
        BaseFragmentNavigator.cleanFragmentStack(getFragmentManager());
        String firstTitle = getTitleStack().get(0);
        getTitleStack().clear();
        getTitleStack().add(firstTitle);
        this.weakRefActivity.get().updateActionBarTitle();
    }

    @CallSuper
    protected void navigateToLowLevel(Fragment frg, String title) {
        getTitleStack().add(title);
        BaseFragmentNavigator.navigateTo(getFragmentManager(), frg, getInnerNavContainerId(), true);
        this.weakRefActivity.get().updateActionBarTitle();
    }

    protected Context getContext(){
        return this.weakRefActivity.get().getBaseContext();
    }

}
