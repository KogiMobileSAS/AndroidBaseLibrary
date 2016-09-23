package com.kogimobile.android.baselibrary.navigation;

public class FragmentNavigatorOptions {

	private String tag;
	private FragmentCustomAnimation fragmentCustomAnimation;
	private boolean noHistory;
	private boolean addingToStack = true;
	private boolean allowingStateLoss;

	public String getTag() {
        return tag;
	}

    /**
     * @param tag
     * sets a tag to recognize the fragment later
     */
	public FragmentNavigatorOptions setTag(String tag) {
		this.tag = tag;
		return this;
	}

	public FragmentCustomAnimation getFragmentCustomAnimation() {
        return fragmentCustomAnimation;
	}

	public FragmentNavigatorOptions setFragmentCustomAnimation(FragmentCustomAnimation fragmentCustomAnimation) {
		this.fragmentCustomAnimation = fragmentCustomAnimation;
		return this;
	}

	public boolean isNoHistoryForCurrent() {
		return noHistory;
	}

	public FragmentNavigatorOptions setNoHistoryForCurrent(boolean noHistory) {
		this.noHistory = noHistory;
		return this;
	}
	
	public boolean isAddingToStack() {
        return addingToStack;
	}
	
	public FragmentNavigatorOptions setAddingToStack(boolean addingToStack) {
		this.addingToStack = addingToStack;
		return this;
	}

	public boolean isAllowingStateLoss() {
        return allowingStateLoss;
	}
	
	public FragmentNavigatorOptions setAllowingStateLoss(boolean allowingStateLoss) {
		this.allowingStateLoss = allowingStateLoss;
		return this;
	}
}
