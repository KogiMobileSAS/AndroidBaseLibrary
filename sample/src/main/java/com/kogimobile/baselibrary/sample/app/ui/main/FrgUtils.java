package com.kogimobile.baselibrary.sample.app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.utils.ConnectivityUtils;
import com.kogimobile.baselibrary.sample.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgUtils extends BaseFragment {

    public static FrgUtils newInstance() {
        FrgUtils frg = new FrgUtils();
        Bundle args = new Bundle();
        frg.setArguments(args);
        return frg;
    }

    @BindView(R.id.tVFrgUtilsConnectivityOn)
    TextView tVFrgUtilsConnectivityOn;
    @BindView(R.id.tVFrgUtilsConnectivityOnWifi)
    TextView tVFrgUtilsConnectivityOnWifi;
    @BindView(R.id.tVFrgUtilsConnectivityOnMobile)
    TextView tVFrgUtilsConnectivityOnMobile;

    @Override
    protected void initVars() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_utils, container, false);
    }

    @Override
    protected void initViews() {
        setUpConnectivityInfo();
    }

    @Override
    protected void initListeners() {

    }

    private void setUpConnectivityInfo(){
        tVFrgUtilsConnectivityOn.setText(Boolean.toString(ConnectivityUtils.isConnected(getContext())));
        tVFrgUtilsConnectivityOnWifi.setText(Boolean.toString(ConnectivityUtils.isConnectedWifi(getContext())));
        tVFrgUtilsConnectivityOnMobile.setText(Boolean.toString(ConnectivityUtils.isConnectedMobile(getContext())));
    }

    @OnClick(R.id.bFrgUtilsConnectivityRefresh)
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.bFrgUtilsConnectivityRefresh:
                setUpConnectivityInfo();
                break;
        }
    }

}
