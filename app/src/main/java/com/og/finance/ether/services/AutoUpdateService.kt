/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

import com.og.finance.ether.network.NetworkCallback
import com.og.finance.ether.network.NetworkManager
import com.og.finance.ether.network.apis.AbstractEtherApi
import com.og.finance.ether.utilities.NotificationUtilities

/**
 * Created by olivier.goutay on 2/29/16.
 * The service launched by [com.og.finance.ether.receivers.AutoUpdateReceiver] every 20 min
 */
class AutoUpdateService : Service(), NetworkCallback<AbstractEtherApi> {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("AutoUpdateService", "Starting AutoUpdateService")
        NetworkManager.getCurrentEthValue(this, this)

        return Service.START_NOT_STICKY
    }

    override fun updateApi(api: AbstractEtherApi?) {
        NotificationUtilities.showNotification(this, api)
    }
}
