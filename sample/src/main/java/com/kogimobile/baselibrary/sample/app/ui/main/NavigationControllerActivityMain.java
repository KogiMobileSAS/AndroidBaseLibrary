package com.kogimobile.baselibrary.sample.app.ui.main;

import com.kogimobile.android.baselibrary.app.base.BaseActivityInnerNavigation;
import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.app.base.navigation.BaseInnerNavigationControllerActivityInner;
import com.kogimobile.android.baselibrary.app.development.TestFragment;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.app.ui.main.events.FrgEvents;
import com.kogimobile.baselibrary.sample.app.ui.main.navigation.FrgNavigation;
import com.kogimobile.baselibrary.sample.app.ui.main.recyclerview.FrgRecyclerView;
import com.kogimobile.baselibrary.sample.app.ui.main.utils.FrgUtils;

import java.util.HashMap;

/**
 * @author Julian Cardona on 6/15/17.
 */

public class NavigationControllerActivityMain extends BaseInnerNavigationControllerActivityInner {

    private HashMap<String,BaseFragment> navFragments;

    public NavigationControllerActivityMain(BaseActivityInnerNavigation activity) {
        super(activity,R.id.container);
        initFragments();
    }

    private void initFragments(){
        this.navFragments = new HashMap<>();
        this.navFragments.put(getSection1Title(),FrgNavigation.newInstance(getFragmentManager().getBackStackEntryCount()));
        this.navFragments.put(getSection2Title(),FrgEvents.newInstance());
        this.navFragments.put(getSection3Title(),FrgUtils.newInstance());
        this.navFragments.put(getSection4Title(),FrgRecyclerView.newInstance());
        this.navFragments.put(getSubSection1Title(),TestFragment.newInstance());
    }

    public void navigateToSection1(){
        navigateToRootLevel(this.navFragments.get(getSection1Title()), getSection1Title());
    }

    public void navigateToSection1LowLevel(BaseFragment frg,String title){
        navigateToLowLevel(frg,title);
    }

    public String getSection1Title(){
        return getContext().getString(R.string.nav_drawer_item_section_1);
    }

    public void navigateToSection2(){
        navigateToRootLevel(this.navFragments.get(getSection2Title()), getSection2Title());
    }

    public String getSection2Title(){
        return getContext().getString(R.string.nav_drawer_item_section_2);
    }

    public void navigateToSection3(){
        navigateToRootLevel(this.navFragments.get(getSection3Title()), getSection3Title());
    }

    public String getSection3Title(){
        return getContext().getString(R.string.nav_drawer_item_section_3);
    }

    public void navigateToSection4(){
        navigateToRootLevel(this.navFragments.get(getSection4Title()), getSection4Title());
    }

    public String getSection4Title(){
        return getContext().getString(R.string.nav_drawer_item_section_4);
    }

    public void navigateToSubSection1(){
        navigateToLowLevel(this.navFragments.get(getSubSection1Title()),getSubSection1Title());
    }

    public String getSubSection1Title(){
        return getContext().getString(R.string.nav_drawer_item_sub_section_1);
    }
}
