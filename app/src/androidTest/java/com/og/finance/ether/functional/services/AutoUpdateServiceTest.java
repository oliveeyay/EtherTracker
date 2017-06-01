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
package com.og.finance.ether.functional.services;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.og.finance.ether.functional.AbstractFunctionalTest;
import com.og.finance.ether.receivers.AutoUpdateReceiver;
import com.og.finance.ether.utilities.NotificationUtilities;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class AutoUpdateServiceTest extends AbstractFunctionalTest {

    /**
     * Test that {@link com.og.finance.ether.services.AutoUpdateService#onStartCommand(Intent, int, int)}
     * refreshes/shows the notification.
     */
    public void testOnStartCommand() {
        //Wait until notification appears and cancel it
        assertNotificationLength(getActivity(), mSolo, 1);
        NotificationUtilities.cancelNotification(getActivity());
        assertNotificationLength(getActivity(), mSolo, 0);

        //Launch back the service and wait for the notif
        Intent intent = new Intent();
        intent.setAction(AutoUpdateReceiver.START_UPDATE_SERVICE);
        mSolo.getCurrentActivity().sendBroadcast(intent);
        assertNotificationLength(getActivity(), mSolo, 1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void assertNotificationLength(Context context, final Solo solo, final int length) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            assertTrue(solo.waitForCondition(new Condition() {
                @Override
                public boolean isSatisfied() {
                    return notificationManager.getActiveNotifications().length == length;
                }
            }, 30));
        }
    }

}
