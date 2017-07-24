package com.kogimobile.baselibrary.sample.app.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.kogimobile.android.baselibrary.app.base.BaseActivityMVP;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.events.FrgEvents;
import com.kogimobile.baselibrary.sample.app.ui.main.navigation.FrgNavigation;
import com.kogimobile.baselibrary.sample.app.ui.main.presenter.PresenterActivityMain;
import com.kogimobile.baselibrary.sample.app.ui.main.presenter.PresenterListenerActivityMain;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.FrgRecyclerView;
import com.kogimobile.baselibrary.sample.app.ui.main.utils.FrgUtils;
import com.kogimobile.baselibrary.sample.databinding.ActivityMainBinding;

public class ActivityMain extends BaseActivityMVP<PresenterActivityMain> implements PresenterListenerActivityMain {

    private ActivityMainBinding binding;

    @Override
    protected void initVars(){
        setPresenter(new PresenterActivityMain());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getTitleStack().size()>1){
                    return super.onOptionsItemSelected(item);
                }else {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        setHomeAsUpIndicator(R.drawable.ic_menu);
        setupDrawerContent(binding.navView);
        navigateToActivityRootLevel(FrgNavigation.newInstance(getSupportFragmentManager().getBackStackEntryCount()),R.id.container,getString(R.string.nav_drawer_item_section_1));
        binding.navView.getMenu().getItem(0).setChecked(true);
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
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
                            case R.id.nav_section_4:
                                navigateToActivityRootLevel(FrgRecyclerView.newInstance(),R.id.container,getString(R.string.nav_drawer_item_section_4));
                                break;
                        }
                        menuItem.setChecked(true);
                        binding.drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
            if(getTitleStack().size()==1){
                setHomeAsUpIndicator(R.drawable.ic_menu);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }
    }

}
