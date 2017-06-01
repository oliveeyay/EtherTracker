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
package com.og.finance.ether.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by olivier.goutay on 2/29/16.
 * Allows to restart the periodic [com.og.finance.ether.services.AutoUpdateService] after a boot
 */
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null && intent.action != null && intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            startAutoUpdate(context)
        }
    }

    /**
     * Starts [AutoUpdateReceiver.START_UPDATE_SERVICE]

     * @param context The context
     */
    private fun startAutoUpdate(context: Context?) {
        if (context != null) {
            val intent = Intent(AutoUpdateReceiver.START_UPDATE_SERVICE)
            context.sendBroadcast(intent)
        }
    }
}
