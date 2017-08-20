package com.kogimobile.android.baselibrary.app.development;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.R;
import com.kogimobile.android.baselibrary.app.base.BaseFragment;

public class TestFragment extends BaseFragment {

	public static TestFragment newInstance() {
		TestFragment fragment = new TestFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected void initVars() {

	}

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initVars();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_test, container, false);
	}

	@Override
	protected void initViews() {

	}

	@Override
	protected void initListeners() {

	}

}