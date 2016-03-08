/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.og.finance.ether.network.NetworkCallback;
import com.og.finance.ether.network.NetworkManager;
import com.og.finance.ether.network.apis.BaseEtherApi;
import com.og.finance.ether.utilities.NotificationUtilities;

/**
 * Created by olivier.goutay on 2/29/16.
 * The service launched by {@link com.og.finance.ether.receivers.AutoUpdateReceiver} every 20 min
 */
public class AutoUpdateService extends Service implements NetworkCallback<BaseEtherApi> {

    private static final String TAG = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting AutoUpdateService");
        NetworkManager.getCurrentEthValue(this);

        return START_NOT_STICKY;
    }

    @Override
    public void updateApi(BaseEtherApi api) {
        NotificationUtilities.showNotification(getApplicationContext(), api);
    }
}
