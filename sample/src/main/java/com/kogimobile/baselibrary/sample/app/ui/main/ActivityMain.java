package com.kogimobile.baselibrary.sample.app.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kogimobile.android.baselibrary.app.base.BaseActivityMVP;
import com.kogimobile.android.baselibrary.app.development.TestFragment;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.presenter.PresenterActivityMain;
import com.kogimobile.baselibrary.sample.app.ui.main.presenter.PresenterListenerActivityMain;

import butterknife.BindView;

public class ActivityMain extends BaseActivityMVP<PresenterActivityMain> implements PresenterListenerActivityMain {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void initVars(){
        setPresenter(new PresenterActivityMain());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getTitleStack().size()>1){
                    return super.onOptionsItemSelected(item);
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        setHomeAsUpIndicator(R.drawable.ic_menu);
        setupDrawerContent(navigationView);
        navigateToActivityRootLevel(FrgNavigation.newInstance(getSupportFragmentManager().getBackStackEntryCount()),R.id.container,getString(R.string.nav_drawer_item_section_1));
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void navigateBackRootLevel() {
        super.navigateBackRootLevel();
        setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public void navigateToActivityLowLevel(Fragment frg, int layoutContainerId, String title) {
        super.navigateToActivityLowLevel(frg, layoutContainerId, title);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        enableHomeBackArrowIndicator();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_section_1:
                                navigateToActivityRootLevel(FrgNavigation.newInstance(getSupportFragmentManager().getBackStackEntryCount()),R.id.container,getString(R.string.nav_drawer_item_section_1));
                                break;
                            case R.id.nav_section_2:
                                navigateToActivityRootLevel(FrgEvents.newInstance(),R.id.container,getString(R.string.nav_drawer_item_section_2));
                                break;
                            case R.id.nav_section_3:
                                navigateToActivityRootLevel(FrgUtils.newInstance(),R.id.container,getString(R.string.nav_drawer_item_section_3));
                                break;
                            case R.id.nav_subsection_1:
                                navigateToActivityLowLevel(TestFragment.newInstance(),R.id.container,getString(R.string.nav_drawer_item_subsection_1));
                                break;
                            case R.id.nav_subsection_2:
                                navigateToActivityLowLevel(TestFragment.newInstance(),R.id.container,getString(R.string.nav_drawer_item_subsection_2));
                                break;
                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
            if(getTitleStack().size()==1){
                setHomeAsUpIndicator(R.drawable.ic_menu);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }
    }

}
