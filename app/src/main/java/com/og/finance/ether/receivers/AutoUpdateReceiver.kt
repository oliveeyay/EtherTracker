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

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.og.finance.ether.BuildConfig
import com.og.finance.ether.services.AutoUpdateService
import com.og.finance.ether.utilities.NotificationUtilities
import com.og.finance.ether.utilities.SharedPreferencesUtilities
import java.util.*

/**
 * Created by olivier.goutay on 2/29/16.
 * Starts the [AutoUpdateService] every 20 min
 */
class AutoUpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null) {
            stopAutoUpdateService(context)
            return
        }

        var action: String? = intent.action
        action = if (action != null) action else ""
        //Start/Stop the service if not boot, ie alarm manager wants service started
        when (action) {
            START_UPDATE_SERVICE -> startAutoUpdateService(context, intent)
            STOP_UPDATE_SERVICE -> stopAutoUpdateService(context)
        }
    }

    companion object {

        /**
         * A service ID to recognize if the service is declared in the [android.app.AlarmManager] or not
         */
        val AUTO_UPDATE_SERVICE_ID = 1234

        val START_UPDATE_SERVICE = "com.og.finance.ether.receivers.AutoUpdateReceiver.START_UPDATE_SERVICE"
        val STOP_UPDATE_SERVICE = "com.og.finance.ether.receivers.AutoUpdateReceiver.STOP_UPDATE_SERVICE"

        /**
         * Restart service every 20 minutes
         */
        private val AUTO_UPDATE_REPEAT_TIME_MILLISECONDS = (1000 * 60 * 20).toLong()

        /**
         * Remember static intent, so we can stop it if the user revokes
         * authorization to use auto tracking
         */
        private var mAutoUpdateService: PendingIntent? = null

        /**
         * Starts the [AutoUpdateService]
         */
        private fun startAutoUpdateService(context: Context, intent: Intent?) {
            if (mAutoUpdateService != null) {
                startService(context)
                return
            } else {
                val updateReceiver = Intent(context, AutoUpdateReceiver::class.java)
                updateReceiver.action = START_UPDATE_SERVICE
                mAutoUpdateService = PendingIntent.getBroadcast(context, AUTO_UPDATE_SERVICE_ID, updateReceiver, PendingIntent.FLAG_CANCEL_CURRENT)
            }

            val cal = Calendar.getInstance()

            if (intent != null && intent.action != null && intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Start 20 seconds after boot completed
                cal.add(Calendar.SECOND, 20)
            } else {
                //start immediately
                cal.add(Calendar.SECOND, 0)
            }

            startRepeatingService(context, mAutoUpdateService, cal, AUTO_UPDATE_REPEAT_TIME_MILLISECONDS)
        }

        /**
         * Stops the [AutoUpdateService]
         */
        private fun stopAutoUpdateService(context: Context) {
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (mAutoUpdateService == null) {
                val retrieverIntent = Intent(context, AutoUpdateReceiver::class.java)
                retrieverIntent.action = STOP_UPDATE_SERVICE
                mAutoUpdateService = PendingIntent.getBroadcast(context, AUTO_UPDATE_SERVICE_ID, retrieverIntent, PendingIntent.FLAG_NO_CREATE)
            }
            if (mAutoUpdateService != null) {
                alarm.cancel(mAutoUpdateService)
            }

            mAutoUpdateService = null
        }

        /**
         * Starts the [AutoUpdateService]

         * @param context
         */
        fun startService(context: Context?) {
            context?.startService(Intent(context, AutoUpdateService::class.java))
        }

        /**
         * Starts a repeating service

         * @param context             The context to use
         * *
         * @param service             The service pending intent to start
         * *
         * @param cal                 The calendar time to start the service at
         * *
         * @param repeatIntervalMilli The repeating time of the service
         */
        protected fun startRepeatingService(context: Context, service: PendingIntent?, cal: Calendar, repeatIntervalMilli: Long) {
            val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // InexactRepeating allows Android to optimize the energy consumption
            if (BuildConfig.DEBUG) {//Repeat exactly for tests
                alarm.setRepeating(AlarmManager.RTC,
                        cal.timeInMillis, repeatIntervalMilli, service)
            } else {//Run in power efficient mode on release
                alarm.setInexactRepeating(AlarmManager.RTC,
                        cal.timeInMillis, repeatIntervalMilli, service)
            }
        }

        /**
         * Static method to call to send a [.START_UPDATE_SERVICE] intent to this
         * receiver that launches the [AutoUpdateService]

         * @param context The context to use to send the broadcast
         */
        fun startAutoUpdate(context: Context) {
            if (SharedPreferencesUtilities.getBooleanForKey(context, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE)) {
                startAutoUpdateService(context, null)
            } else {
                stopAutoUpdate(context)
            }
        }

        /**
         * Static method to call to send a [.STOP_UPDATE_SERVICE] intent to this
         * receiver that stops the [AutoUpdateService]

         * @param context The context to use to send the broadcast
         */
        fun stopAutoUpdate(context: Context) {
            stopAutoUpdateService(context)
            NotificationUtilities.cancelNotification(context)
        }
    }

}
