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
package com.og.finance.ether.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by olivier.goutay on 2/29/16.
 * Allows to restart the periodic {@link com.og.finance.ether.services.AutoUpdateService} after a boot
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    /**
     * Tag for logging
     */
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                startAutoUpdate(context);
        }
    }

    /**
     * Starts {@link AutoUpdateReceiver#START_UPDATE_SERVICE}
     * @param context The context
     */
    private void startAutoUpdate(Context context) {
        if(context != null){
            Intent intent = new Intent(AutoUpdateReceiver.START_UPDATE_SERVICE);
            context.sendBroadcast(intent);
        }
    }
}
