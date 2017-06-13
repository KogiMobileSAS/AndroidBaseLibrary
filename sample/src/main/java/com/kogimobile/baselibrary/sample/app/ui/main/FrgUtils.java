package com.kogimobile.baselibrary.sample.app.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.utils.ConnectivityUtils;
import com.kogimobile.android.baselibrary.utils.DateTimeUtils;
import com.kogimobile.baselibrary.sample.R;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgUtils extends BaseFragment {

    private final String DATETIME_FROM = "2017/06/12T10:11:12";

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
    @BindView(R.id.tVFrgUtilsDateTimeConvert)
    TextView tVFrgUtilsDateTimeConvert;

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
        convertDateTime();
    }

    @Override
    protected void initListeners() {

    }

    private void setUpConnectivityInfo(){
        tVFrgUtilsConnectivityOn.setText(Boolean.toString(ConnectivityUtils.isConnected(getContext())));
        tVFrgUtilsConnectivityOnWifi.setText(Boolean.toString(ConnectivityUtils.isConnectedWifi(getContext())));
        tVFrgUtilsConnectivityOnMobile.setText(Boolean.toString(ConnectivityUtils.isConnectedMobile(getContext())));
    }

    private void convertDateTime(){
        DateTime dateTimeFrom = DateTimeUtils.getDateTimeFromPattern(
                getString(R.string.date_format_convert_from),
                DATETIME_FROM
        );
        tVFrgUtilsDateTimeConvert.setText(
                getString(
                        R.string.frg_utils_datetime_convert,
                        DATETIME_FROM,
                        DateTimeUtils.getStringPatternFromDateTime(
                                getString(R.string.date_format_convert_to),
                                dateTimeFrom
                        )
                )
        );
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
