package com.kogimobile.android.baselibrary.app.base;

import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;

import com.kogimobile.android.baselibrary.R;
import com.kogimobile.android.baselibrary.app.base.navigation.BaseInnerNavigationControllerActivityInner;

import java.util.ArrayList;

/**
 * @author Julian Cardona on 7/27/17.
 */

public abstract class BaseActivityInnerNavigation extends BaseActivity{

    private static final int HOME_UP_INDICATOR_NONE = -1;
    private static final int HOME_UP_INDICATOR_ARROW = 0;

    private int homeUpIndicator = HOME_UP_INDICATOR_NONE;
    private BaseInnerNavigationControllerActivityInner navigationController;

    protected <N extends BaseInnerNavigationControllerActivityInner> N getNavigationControllerOf(Class<N> clazz) {
        if(navigationController == null){
            navigationController = new BaseInnerNavigationControllerActivityInner(this, R.id.container);
        }
        return clazz.cast(navigationController);
    }

    public abstract @NonNull <N extends BaseInnerNavigationControllerActivityInner> N getNavigationController();

    protected void setNavigationController(BaseInnerNavigationControllerActivityInner navigationController) {
        this.navigationController = navigationController;
    }

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if ((getNavigationController().getTitleStack().size() == 0 && homeUpIndicator != HOME_UP_INDICATOR_NONE) || getNavigationController().getTitleStack().size() > 0) {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        updateActionBarTitle();
    }

    @CallSuper
    public void updateActionBarTitle() {
        if (getSupportActionBar() != null) {
            if (getNavigationControllerOf(BaseInnerNavigationControllerActivityInner.class).getTitleStack().size() > 0) {
                getSupportActionBar().setTitle(getNavigationControllerOf(BaseInnerNavigationControllerActivityInner.class).getTitleStack().get(getNavigationController().getTitleStack().size() - 1));
                updateActionBarUpIndicator();
            }
        }
    }

    public ArrayList<String> getTitleStack(){
        return getNavigationControllerOf(BaseInnerNavigationControllerActivityInner.class).getTitleStack();
    }

    @CallSuper
    protected  void updateActionBarUpIndicator() {
        if (getNavigationController().getTitleStack().size() > 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (homeUpIndicator != HOME_UP_INDICATOR_NONE) {
                Drawable upIndicator;
                if (homeUpIndicator != HOME_UP_INDICATOR_ARROW) {
                    upIndicator = ContextCompat.getDrawable(this, homeUpIndicator);
                } else {
                    upIndicator = getDrawerToggleDelegate().getThemeUpIndicator();
                }
                getSupportActionBar().setHomeAsUpIndicator(upIndicator);
            }
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if (homeUpIndicator != HOME_UP_INDICATOR_NONE) {
            if (homeUpIndicator != HOME_UP_INDICATOR_ARROW) {
                getSupportActionBar().setHomeAsUpIndicator(ContextCompat.getDrawable(this, homeUpIndicator));
            } else {
                getSupportActionBar().setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @CallSuper
    protected void setHomeAsUpIndicator(int resourceId) {
        homeUpIndicator = resourceId;
        updateActionBarUpIndicator();
    }

    @CallSuper
    protected void enableHomeBackArrowIndicator() {
        homeUpIndicator = HOME_UP_INDICATOR_ARROW;
        updateActionBarUpIndicator();
    }

    @CallSuper
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ((getNavigationControllerOf(BaseInnerNavigationControllerActivityInner.class).getTitleStack().size()) > 0) {
            getNavigationController().getTitleStack().remove(getNavigationController().getTitleStack().size() - 1);
            if ((getNavigationControllerOf(BaseInnerNavigationControllerActivityInner.class).getTitleStack().size()) > 0) {
                updateActionBarTitle();
            }
        }
    }

}
