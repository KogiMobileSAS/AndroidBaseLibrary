package com.kogimobile.baselibrary.sample.app.ui.main.events;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.app.busevents.alert.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.progress.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.snackbar.EventSnackbarMessage;
import com.kogimobile.baselibrary.sample.R;
import com.kogimobile.baselibrary.sample.databinding.FrgEventsBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Julian Cardona on 6/12/17.
 */

public class FrgEvents extends BaseFragment implements EventHandlerEvents{

    private FrgEventsBinding binding;

    public static FrgEvents newInstance() {
        FrgEvents frg = new FrgEvents();
        Bundle args = new Bundle();
        frg.setArguments(args);
        return frg;
    }

    @Override
    protected void initVars() {

	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.frg_events, container, false);
        this.binding.setEventHandler(this);
        return this.binding.getRoot();
    }

    @Override
    protected void initViews() {}

    @Override
    protected void initListeners() {}

    @Override
    public void onClickEventSnackBar() {
        sendEventSnackbar();
    }

    @Override
    public void onClickEventProgress() {
        sendEventProgress();
    }

    @Override
    public void onClickEventAlert() {
        sendEventAlertDialog();
    }

    private void sendEventSnackbar(){
        EventBus.getDefault()
                .post(
                        EventSnackbarMessage
                                .getBuilder()
                                .withMessage(R.string.frg_events_message_snackbar)
                                .build()
                );
    }

    private void sendEventProgress(){
        EventBus.getDefault()
                .post(
                        EventProgressDialog
                                .getBuilder()
                                .withMessageId(R.string.frg_events_message_progress)
                                .withCancelable(true)
                                .build()
                );
    }

    private void sendEventProgressDismiss(){
        EventBus.getDefault()
                .post(
                        EventProgressDialog
                                .getBuilder()
                                .dismiss()
                                .build()
                );
    }

    private void sendEventAlertDialog() {
        EventBus.getDefault()
                .post(
                        EventAlertDialog.getBuilder()
                                .withTitleId(R.string.frg_events_title_alert_dialog)
                                .withMesageId(R.string.frg_events_message_alert_dialog)
                                .withPositiveButton(
                                        R.string.frg_events_message_positive_alert_dialog,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }
                                )
                                .withNegativeButton(
                                        R.string.frg_events_message_negative_alert_dialog,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }
                                )
                                .build()
                );
    }

}


