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
package com.og.finance.ether.unit.utilities;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.apis.PolionexPriceApi;
import com.og.finance.ether.unit.AbstractUnitTest;
import com.og.finance.ether.utilities.NotificationUtilities;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class NotificationUtilitiesTest extends AbstractUnitTest {

    /**
     * Test {@link NotificationUtilities#showNotification(Context, AbstractEtherApi)}
     * and {@link NotificationUtilities#cancelNotification(Context)}
     * Only testable on Marshmallow devices.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void testShowCancelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Test no notification
            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            assertEquals(0, notificationManager.getActiveNotifications().length);

            //Test notification with null api
            NotificationUtilities.showNotification(getContext(), null);
            assertEquals(1, notificationManager.getActiveNotifications().length);

            //Test cancel notification
            NotificationUtilities.cancelNotification(getContext());
            assertEquals(0, notificationManager.getActiveNotifications().length);

            //Test notification with correct api
            NotificationUtilities.showNotification(getContext(), new PolionexEtherApi(new PolionexPriceApi(10.0f, 10.0f)));
            assertEquals(1, notificationManager.getActiveNotifications().length);

            //Test cancel notification
            NotificationUtilities.cancelNotification(getContext());
            assertEquals(0, notificationManager.getActiveNotifications().length);
        }
    }

}
