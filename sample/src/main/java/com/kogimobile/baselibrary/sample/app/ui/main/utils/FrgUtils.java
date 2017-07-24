package com.kogimobile.baselibrary.sample.app.ui.main.utils;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.utils.ConnectivityUtils;
import com.kogimobile.android.baselibrary.utils.DateTimeUtils;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.FrgUtilsBinding;

import org.joda.time.DateTime;

/**
 * @author Julian Cardona on 6/13/17.
 */

public class FrgUtils extends BaseFragment implements EventHandlerUtils{

    private final String DATETIME_FROM = "2017/06/12T10:11:12";
    private FrgUtilsBinding binding;

    public static FrgUtils newInstance() {
        FrgUtils frg = new FrgUtils();
        Bundle args = new Bundle();
        frg.setArguments(args);
        return frg;
    }

    @Override
    protected void initVars() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.frg_utils, container, false);
        this.binding.setEventHandler(this);
        return this.binding.getRoot();
    }

    @Override
    protected void initViews() {
        setUpConnectivityInfo();
        convertDateTime();
    }

    @Override
    protected void initListeners() {

    }

    private void convertDateTime(){
        DateTime dateTimeFrom = DateTimeUtils.getDateTimeFromPattern(
                getString(R.string.date_format_convert_from),
                DATETIME_FROM
        );
        this.binding.timeConvert.setText(
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

    @Override
    public void onClickRefresh() {
        setUpConnectivityInfo();
    }

    private void setUpConnectivityInfo(){
        this.binding.connectivityOn.setText(Boolean.toString(ConnectivityUtils.isConnected(getContext())));
        this.binding.connectivityOnWifi.setText(Boolean.toString(ConnectivityUtils.isConnectedWifi(getContext())));
        this.binding.connectivityOnMobile.setText(Boolean.toString(ConnectivityUtils.isConnectedMobile(getContext())));
    }
}
