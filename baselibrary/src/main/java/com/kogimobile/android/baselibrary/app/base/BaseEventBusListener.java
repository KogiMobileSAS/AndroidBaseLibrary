package com.kogimobile.android.baselibrary.app.base;

import com.kogimobile.android.baselibrary.app.busevents.alert.EventAlertDialog;
import com.kogimobile.android.baselibrary.app.busevents.progress.EventProgressDialog;
import com.kogimobile.android.baselibrary.app.busevents.snackbar.EventSnackbarMessage;

/**
 * @author Julian Cardona. julian@kogimobile.com
 * @version 9/4/16. *
 *          Copyright 2015 Kogi Mobile
 *          <p>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p>
 *          http://www.apache.org/licenses/LICENSE-2.0
 *          <p>
 *          Unless required by applicable law or agreed to in writing, software
 *          distributed under the License is distributed on an "AS IS" BASIS,
 *          WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *          See the License for the specific language governing permissions and
 *          limitations under the License.
 * @modified Pedro Scott. pedro@kogimobile.com
 */
public interface BaseEventBusListener {

    void onProgressDialogEvent(EventProgressDialog event);

    void onAlertDialogEvent(EventAlertDialog alert);

    void onSnackbarMessageEvent(EventSnackbarMessage event);

}
