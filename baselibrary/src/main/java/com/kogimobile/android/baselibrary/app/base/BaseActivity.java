package com.kogimobile.android.baselibrary.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.kogimobile.android.baselibrary.app.busevents.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.EventSnackbarMessage;
import com.kogimobile.android.baselibrary.app.busevents.EventToastMessage;
import com.kogimobile.android.baselibrary.navigation.FragmentNavigator;
import com.kogimobile.android.baselibrary.navigation.FragmentNavigatorOptions;
import com.kogimobile.android.baselibrary.utils.DrawableUtils;
import com.kogimobile.android.baselibrary.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Julian Cardona on 11/5/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseEventBusListener{

    private static final int HOME_UP_INDICATOR_NONE = -1;
    private static final int HOME_UP_INDICATOR_ARROW = 0;

    private CompositeSubscription subscription = new CompositeSubscription();
    protected ArrayList<String> titleStack = new ArrayList<String>();
    private Unbinder unbinder;
    private ProgressDialog progress;
    private int homeUpIndicator = HOME_UP_INDICATOR_NONE;

    public void addSubscription(Subscription serviceSubscription) {
        this.subscription.add(serviceSubscription);
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        initVars();
    }

    abstract protected void initVars();

    abstract protected void initViews();

    abstract protected void initListeners();

    @CallSuper
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if((titleStack.size() == 0 && homeUpIndicator != HOME_UP_INDICATOR_NONE) || titleStack.size()>0){
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
    public void onProgressDialogEvent(EventProgressDialog event){
        if(event.isShown()){
            clearKeyboardFromScreen();
            if(progress.isShowing()){
                progress.dismiss();
            }
            progress.setMessage(event.getProgressDialogMessage());
            progress.show();
        }
        else {
            if(progress.isShowing()) {
                progress.dismiss();
            }
        }
    }

    @CallSuper
    @Override
    @Subscribe
    public void onAlertDialogEvent(EventAlertDialog alert){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(alert.getTitle())
                .setMessage(alert.getMessage())
                .setCancelable(alert.isCancellable())
                .setPositiveButton(StringUtils.isBlank(alert.getPositiveButtonText()) ? getString(android.R.string.ok) : alert.getPositiveButtonText(), alert.getPositiveListener()==null ? new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }:alert.getPositiveListener());
        if(alert.getNegativeListener()!=null){
            builder.setNegativeButton(StringUtils.isBlank(alert.getNegativeButtonText()) ? getString(android.R.string.cancel) : alert.getNegativeButtonText(), alert.getNegativeListener()==null ? new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }:alert.getNegativeListener());
        }

        builder.show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onToastMessageEvent(EventToastMessage event){
        Toast.makeText(this,event.getMessage(),Toast.LENGTH_LONG).show();
    }

    @CallSuper
    @Override
    @Subscribe
    public void onSnackbarMessageEvent(EventSnackbarMessage event) {
        clearKeyboardFromScreen();
        int viewId = android.R.id.content;
        if (event.getViewId()!=EventSnackbarMessage.NONE_VIEW) {
            viewId = event.getViewId();
        }
        Snackbar snackBar = Snackbar.make(getWindow().getDecorView().findViewById(viewId), event.getMessage(), Snackbar.LENGTH_LONG);
        if(event.getActionListener()!=null) {
            snackBar.setAction(event.getAction(), event.getActionListener());
        }
        if(event.getCallback()!=null){
            snackBar.setCallback(event.getCallback());
        }
        snackBar.show();
    }

    protected void clearKeyboardFromScreen() {
        if (getWindow().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @CallSuper
    protected void navigateToActivityLowLevel(Fragment frg,int layoutContainerId, String title) {
        titleStack.add(title);
        FragmentNavigator.navigateTo(getSupportFragmentManager(), frg,layoutContainerId, new FragmentNavigatorOptions().setAddingToStack(true));
        updateActionBarTitle();
    }

    @CallSuper
    protected void navigateToActivityRootLevel(Fragment frg,int layoutContainerId,String title){
        FragmentNavigator.cleanFragmentStack(getSupportFragmentManager());
        FragmentNavigator.navigateTo(getSupportFragmentManager(), frg, layoutContainerId, new FragmentNavigatorOptions().setAddingToStack(false));
        titleStack.clear();
        titleStack.add(title);
        updateActionBarTitle();
    }

    @CallSuper
    protected void updateActionBarTitle(){
        if(getSupportActionBar()!=null) {
            if(titleStack.size()>0) {
                getSupportActionBar().setTitle(titleStack.get(titleStack.size() - 1));
                if (titleStack.size()>1){
                    getSupportActionBar().setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                else{
                    if(homeUpIndicator!=HOME_UP_INDICATOR_NONE){

                        if(homeUpIndicator!=HOME_UP_INDICATOR_ARROW) {
                            getSupportActionBar().setHomeAsUpIndicator(DrawableUtils.getDrawableFromResource(this, homeUpIndicator));
                        }else{
                            getSupportActionBar().setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());
                        }
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                    else{
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    }
                }
            }
        }
    }

    @CallSuper
    protected void setHomeAsUpIndicator(int resourceId){
        homeUpIndicator = resourceId;
        getSupportActionBar().setHomeAsUpIndicator(DrawableUtils.getDrawableFromResource(this,homeUpIndicator));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void openUrlWebPage(String url,int colorId){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this,colorId));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
	}

    @CallSuper
    protected void enableHomeBackArrowIndicator(){
        homeUpIndicator = HOME_UP_INDICATOR_ARROW;
        getSupportActionBar().setHomeAsUpIndicator(getDrawerToggleDelegate().getThemeUpIndicator());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if(this.unbinder!=null) {
            this.unbinder.unbind();
        }
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void onBackPressed() {
        clearKeyboardFromScreen();
        if((titleStack.size())>0){
            titleStack.remove(titleStack.size()-1);
            if((titleStack.size())>0) {
                updateActionBarTitle();
            }
        }
        super.onBackPressed();
    }

}
