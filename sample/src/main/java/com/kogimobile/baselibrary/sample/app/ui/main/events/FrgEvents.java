package com.kogimobile.baselibrary.sample.app.ui.main.events;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kogimobile.android.baselibrary.app.base.BaseFragment;
import com.kogimobile.android.baselibrary.app.busevents.alert.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.progress.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.snackbar.EventSnackbarMessage;
import com.kogimobile.baselibrary.sample.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Julian Cardona on 6/12/17.
 */

public class FrgEvents extends BaseFragment {

    @BindView(R.id.bFrgEventSnackbar)
    Button bFrgEventSnackbar;

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
		return inflater.inflate(R.layout.frg_events, container, false);
	}

    @Override
    protected void initViews() {

	}

    @Override
    protected void initListeners() {

	}

	@OnClick({
	        R.id.bFrgEventSnackbar,
            R.id.bFrgEventProgress,
            R.id.bFrgEventAlert
	})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.bFrgEventSnackbar:
                sendEventSnackbar();
                break;
            case R.id.bFrgEventProgress:
                sendEventProgress();
                break;
            case R.id.bFrgEventAlert:
                sendEventAlertDialog();
                break;
        }
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


