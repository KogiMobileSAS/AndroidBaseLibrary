package com.kogimobile.android.baselibrary.navigation;

/**
 *  
 * This one is made for contains the states of the animations needed for Fragment Animation
 * Navigation 
 *  
 *
 */
public class FragmentCustomAnimation {

	private int enter;
	private int exit;
	private int popEnter;
	private int popExit;

	/**
	 * 
	 * Set specific animation resources to run for the fragments that are
	 * entering and exiting in this transaction. The popEnter and popExit
	 * animations will be played for enter/exit operations specifically when
	 * popping the back stack.
	 * 
	 * @param enter
	 * @param exit
	 * @param popEnter
	 * @param popExit
	 */
	public FragmentCustomAnimation(int enter, int exit, int popEnter,int popExit) {
		this.enter = enter;
		this.exit = exit;
		this.popEnter = popEnter;
		this.popExit = popExit;
	}

	public int getEnter() {
		return enter;
	}

	public void setEnter(int enter) {
		this.enter = enter;
	}

	public int getExit() {
		return exit;
	}

	public void setExit(int exit) {
		this.exit = exit;
	}

	public int getPopEnter() {
		return popEnter;
	}

	public void setPopEnter(int popEnter) {
		this.popEnter = popEnter;
	}

	public int getPopExit() {
		return popExit;
	}

	public void setPopExit(int popExit) {
		this.popExit = popExit;
	}
}
