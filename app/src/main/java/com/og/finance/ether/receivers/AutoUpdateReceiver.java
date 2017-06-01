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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.og.finance.ether.BuildConfig;
import com.og.finance.ether.services.AutoUpdateService;
import com.og.finance.ether.utilities.NotificationUtilities;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import java.util.Calendar;

/**
 * Created by olivier.goutay on 2/29/16.
 * Starts the {@link AutoUpdateService} every 20 min
 */
public class AutoUpdateReceiver extends BroadcastReceiver {

    /**
     * A service ID to recognize if the service is declared in the {@link android.app.AlarmManager} or not
     */
    public static final int AUTO_UPDATE_SERVICE_ID = 1234;

    public static final String START_UPDATE_SERVICE = "com.og.finance.ether.receivers.AutoUpdateReceiver.START_UPDATE_SERVICE";
    public static final String STOP_UPDATE_SERVICE = "com.og.finance.ether.receivers.AutoUpdateReceiver.STOP_UPDATE_SERVICE";

    /**
     * Restart service every 20 minutes
     */
    private static final long AUTO_UPDATE_REPEAT_TIME_MILLISECONDS = 1000 * 60 * 20;

    /**
     * Remember static intent, so we can stop it if the user revokes
     * authorization to use auto tracking
     */
    private static PendingIntent mAutoUpdateService = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            stopAutoUpdateService(context);
            return;
        }

        String action = intent.getAction();
        action = action != null ? action : "";
        //Start/Stop the service if not boot, ie alarm manager wants service started
        switch (action) {
            case START_UPDATE_SERVICE:
                startAutoUpdateService(context, intent);
                break;
            case STOP_UPDATE_SERVICE:
            default:
                stopAutoUpdateService(context);
                break;
        }
    }

    /**
     * Starts the {@link AutoUpdateService}
     */
    private static void startAutoUpdateService(Context context, Intent intent) {
        if (mAutoUpdateService != null) {
            startService(context);
            return;
        } else {
            Intent updateReceiver = new Intent(context, AutoUpdateReceiver.class);
            updateReceiver.setAction(START_UPDATE_SERVICE);
            mAutoUpdateService = PendingIntent.getBroadcast(context, AUTO_UPDATE_SERVICE_ID, updateReceiver, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        Calendar cal = Calendar.getInstance();

        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            // Start 20 seconds after boot completed
            cal.add(Calendar.SECOND, 20);
        } else {
            //start immediately
            cal.add(Calendar.SECOND, 0);
        }

        startRepeatingService(context, mAutoUpdateService, cal, AUTO_UPDATE_REPEAT_TIME_MILLISECONDS);
    }

    /**
     * Stops the {@link AutoUpdateService}
     */
    private static void stopAutoUpdateService(Context context) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (mAutoUpdateService == null) {
            Intent retrieverIntent = new Intent(context, AutoUpdateReceiver.class);
            retrieverIntent.setAction(STOP_UPDATE_SERVICE);
            mAutoUpdateService = PendingIntent.getBroadcast(context, AUTO_UPDATE_SERVICE_ID, retrieverIntent, PendingIntent.FLAG_NO_CREATE);
        }
        if (mAutoUpdateService != null) {
            alarm.cancel(mAutoUpdateService);
        }

        mAutoUpdateService = null;
    }

    /**
     * Starts the {@link AutoUpdateService}
     *
     * @param context
     */
    public static void startService(Context context) {
        if (context != null) {
            context.startService(new Intent(context, AutoUpdateService.class));
        }
    }

    /**
     * Starts a repeating service
     *
     * @param context             The context to use
     * @param service             The service pending intent to start
     * @param cal                 The calendar time to start the service at
     * @param repeatIntervalMilli The repeating time of the service
     */
    protected static void startRepeatingService(Context context, PendingIntent service, Calendar cal, long repeatIntervalMilli) {
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // InexactRepeating allows Android to optimize the energy consumption
        if (BuildConfig.DEBUG) {//Repeat exactly for tests
            alarm.setRepeating(AlarmManager.RTC,
                    cal.getTimeInMillis(), repeatIntervalMilli, service);
        } else {//Run in power efficient mode on release
            alarm.setInexactRepeating(AlarmManager.RTC,
                    cal.getTimeInMillis(), repeatIntervalMilli, service);
        }
    }

    /**
     * Static method to call to send a {@link #START_UPDATE_SERVICE} intent to this
     * receiver that launches the {@link AutoUpdateService}
     *
     * @param context The context to use to send the broadcast
     */
    public static void startAutoUpdate(Context context) {
        if (SharedPreferencesUtilities.getBooleanForKey(context, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE)) {
            startAutoUpdateService(context, null);
        } else {
            stopAutoUpdate(context);
        }
    }

    /**
     * Static method to call to send a {@link #STOP_UPDATE_SERVICE} intent to this
     * receiver that stops the {@link AutoUpdateService}
     *
     * @param context The context to use to send the broadcast
     */
    public static void stopAutoUpdate(Context context) {
        stopAutoUpdateService(context);
        NotificationUtilities.cancelNotification(context);
    }

}
