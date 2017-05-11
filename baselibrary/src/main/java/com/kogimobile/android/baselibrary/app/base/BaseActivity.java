package com.kogimobile.android.baselibrary.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kogimobile.android.baselibrary.app.busevents.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventSnackbarMessage;
import com.kogimobile.android.baselibrary.app.busevents.EventToastMessage;
import com.kogimobile.android.baselibrary.app.busevents.utils.SnackbarEventBuilder;
import com.kogimobile.android.baselibrary.navigation.FragmentNavigator;
import com.kogimobile.android.baselibrary.navigation.FragmentNavigatorOptions;
import com.kogimobile.android.baselibrary.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Julian Cardona. julian@kogimobile.com
 * @version 9/4/16. *
 *          Copyright 2015 Kogi Mobile
 *          <p>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p>
 *          http://www.apache.org/licenses/LICENSE-2.0
 *          <p>
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 * @modified Pedro Scott. scott7462@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseEventBusListener {

    private static final int HOME_UP_INDICATOR_NONE = -1;
    private static final int HOME_UP_INDICATOR_ARROW = 0;

    private CompositeSubscription subscription = new CompositeSubscription();
    private ArrayList<String> titleStack = new ArrayList<String>();
    private Unbinder unbinder;
    private ProgressDialog progress;
    private int homeUpIndicator = HOME_UP_INDICATOR_NONE;
    private boolean enableTitleStack = true;

    public CompositeSubscription getSubscription() {
        return subscription;
    }

    public ArrayList<String> getTitleStack() {
        return titleStack;
    }

    public void addSubscription(Subscription serviceSubscription) {
        this.subscription.add(serviceSubscription);
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        initVars();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    abstract protected void initVars();

    abstract protected void initViews();

    abstract protected void initListeners();

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if ((titleStack.size() == 0 && homeUpIndicator != HOME_UP_INDICATOR_NONE) || titleStack.size() > 0) {
                    onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @CallSuper
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        this.unbinder = ButterKnife.bind(this);
        initViews();
        initListeners();
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        updateActionBarTitle();
    }

    @CallSuper
    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onProgressDialogEvent(EventProgressDialog event) {
        if (event.isShown()) {
            clearKeyboardFromScreen();
            if (progress.isShowing()) {
                progress.dismiss();
            }
            progress.setMessage(event.getProgressDialogMessage());
            progress.show();
        } else {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }
    }

    @CallSuper
    @Override
    @Subscribe
    public void onAlertDialogEvent(EventAlertDialog alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(alert.getTitle())
                .setMessage(alert.getMessage())
                .setCancelable(alert.isCancellable())
                .setPositiveButton(StringUtils.isBlank(alert.getPositiveButtonText()) ? getString(android.R.string.ok) : alert.getPositiveButtonText(), alert.getPositiveListener() == null ? new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } : alert.getPositiveListener());
        if (alert.getNegativeListener() != null) {
            builder.setNegativeButton(StringUtils.isBlank(alert.getNegativeButtonText()) ? getString(android.R.string.cancel) : alert.getNegativeButtonText(), alert.getNegativeListener() == null ? new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            } : alert.getNegativeListener());
        }
        builder.show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onToastMessageEvent(EventToastMessage event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_LONG).show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onSnackbarMessageEvent(EventSnackbarMessage event) {
        clearKeyboardFromScreen();
        SnackbarEventBuilder snackbarEventBuilder = new SnackbarEventBuilder(event, getViewDecorator());
        snackbarEventBuilder.showSnackbar();
    }

    private View getViewDecorator() {
        return getWindow().getDecorView();
    }

    public void clearKeyboardFromScreen() {
        if (getWindow().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @CallSuper
    public void navigateToActivityLowLevel(Fragment frg, int layoutContainerId, String title) {
        titleStack.add(title);
        FragmentNavigator.navigateTo(getSupportFragmentManager(), frg, layoutContainerId, new FragmentNavigatorOptions().setAddingToStack(true));
        updateActionBarTitle();
    }

    @CallSuper
    public void navigateToActivityRootLevel(Fragment frg, int layoutContainerId, String title) {
        FragmentNavigator.cleanFragmentStack(getSupportFragmentManager());
        FragmentNavigator.navigateTo(getSupportFragmentManager(), frg, layoutContainerId, new FragmentNavigatorOptions().setAddingToStack(false));
        titleStack.clear();
        titleStack.add(title);
        updateActionBarTitle();
    }

    @CallSuper
    public void updateActionBarTitle() {
        if (getSupportActionBar() != null && isTitleStackEnabled()) {
            if (titleStack.size() > 0) {
                getSupportActionBar().setTitle(titleStack.get(titleStack.size() - 1));
                updateActionBarUpIndicator();
            }
        }
    }

    public void updateActionBarUpIndicator() {
        if (titleStack.size() > 1) {
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
    public void setHomeAsUpIndicator(int resourceId) {
        homeUpIndicator = resourceId;
        updateActionBarUpIndicator();
    }

    public void openUrlWebPage(String url, int colorId) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, colorId));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @CallSuper
    public void enableHomeBackArrowIndicator() {
        homeUpIndicator = HOME_UP_INDICATOR_ARROW;
        updateActionBarUpIndicator();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (this.unbinder != null) {
            this.unbinder.unbind();
        }
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void onBackPressed() {
        clearKeyboardFromScreen();
        if (isTitleStackEnabled() && (titleStack.size()) > 0) {
            titleStack.remove(titleStack.size() - 1);
            if ((titleStack.size()) > 0) {
                updateActionBarTitle();
            }
        }
        super.onBackPressed();
    }

    public boolean isTitleStackEnabled() {
        return enableTitleStack;
    }

    public void setEnableTitleStack(boolean enableTitleStack) {
        this.enableTitleStack = enableTitleStack;
    }

    public void sendSuccessResult(Intent data) {
        setResult(RESULT_OK, data);
        finish();
    }

}
