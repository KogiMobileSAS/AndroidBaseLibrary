package com.kogimobile.android.baselibrary.app.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

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
 * @modified Pedro Scott. scott7462@gmail.com
 */
public class BaseApp extends MultiDexApplication {

    private static Context globalContext;

    @Override
    public void onCreate() {
        super.onCreate();
        globalContext = this;
    }

    public static Context getGlobalContext() {
        return globalContext;
    }
}
