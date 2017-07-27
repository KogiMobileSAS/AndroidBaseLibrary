package com.kogimobile.baselibrary.sample.app.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.kogimobile.android.baselibrary.app.base.BaseActivityInnerNavigation;
import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.app.base.navigation.BaseFragmentNavigator;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.ActivityMainBinding;

public class ActivityMain extends BaseActivityInnerNavigation{

    private ActivityMainBinding binding;

    @Override
    protected void initVars(){
        setNavigationController(new NavigationControllerActivityMain(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    @NonNull
    @Override
    public NavigationControllerActivityMain getNavigationController() {
        return getNavigationControllerOf(NavigationControllerActivityMain.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getTitleStack().size() > 1){
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
        initToolbar();
        initNavView();
    }

    @Override
    protected void initListeners() {

    }

    private void initToolbar(){
        setSupportActionBar(binding.includeToolbar.toolbar);
        setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void initNavView(){
        binding.navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        navigateToSection(menuItem.getItemId());
                        menuItem.setChecked(true);
                        binding.drawerLayout.closeDrawers();
                        return true;
                    }
                });
        binding.navView.getMenu().getItem(0).setChecked(true);
        navigateToSection1();
    }

    public void navigateToSection1(){
        navigateToSection(R.id.nav_section_1);
    }

    public void navigateSection1LowLevel(BaseFragment frg,String title){
        getNavigationController().navigateToSection1LowLevel(frg,title);
        setNavigationLowLevelStatus();
        updateActionBarTitle();
    }

    public void navigateToSection2(){
        navigateToSection(R.id.nav_section_2);
    }

    public void navigateToSection3(){
        navigateToSection(R.id.nav_section_3);
    }

    public void navigateToSection4(){
        navigateToSection(R.id.nav_section_4);
    }

    public void navigateToSubSection1(){
        navigateToSection(R.id.nav_subsection_1);
    }

    private void navigateToSection(int itemId){
        BaseFragmentNavigator.cleanFragmentStack(getSupportFragmentManager());
        getTitleStack().clear();
        switch (itemId) {
            case R.id.nav_section_1:
                getNavigationController().navigateToSection1();
                break;
            case R.id.nav_section_2:
                getNavigationController().navigateToSection2();
                break;
            case R.id.nav_section_3:
                getNavigationController().navigateToSection3();
                break;
            case R.id.nav_section_4:
                getNavigationController().navigateToSection4();
                break;

            case R.id.nav_subsection_1:
                getNavigationController().navigateToSubSection1();
                break;
        }
        updateActionBarTitle();
    }

    public void navigateBackRootLevel() {
        getNavigationController().navigateBackRootLevel();
        setNavigationRootStatus();
        updateActionBarTitle();
    }

    private void setNavigationRootStatus(){
        unLockDrawerLayout();
        setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void setNavigationLowLevelStatus(){
        lockDrawerLayout();
        enableHomeBackArrowIndicator();
    }

    private void lockDrawerLayout(){
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unLockDrawerLayout(){
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
            if(getTitleStack().size()==1){
                setNavigationRootStatus();
            }
        }
    }

}
