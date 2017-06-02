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
package com.og.finance.ether.utilities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.NotificationCompat

import com.og.finance.ether.R
import com.og.finance.ether.activities.MainActivity
import com.og.finance.ether.network.apis.AbstractEtherApi
import com.og.finance.ether.network.apis.CoinMarketEtherApi

/**
 * Created by olivier.goutay on 2/29/16.
 */
object NotificationUtilities {

    /**
     * Show a notification containing the [infos][CoinMarketEtherApi]

     * @param etherApi The latest [CoinMarketEtherApi]
     */
    fun showNotification(context: Context, etherApi: AbstractEtherApi?) {
        val intent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val b = NotificationCompat.Builder(context)

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Ether Tracker")
                .setOngoing(true)
                .setContentText(PriceFormatUtilities.getPriceFormatted(context, etherApi))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSound(Uri.EMPTY)
                .setContentIntent(contentIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, b.build())
    }

    /**
     * Cancel the current [Notification]
     */
    fun cancelNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }
}
