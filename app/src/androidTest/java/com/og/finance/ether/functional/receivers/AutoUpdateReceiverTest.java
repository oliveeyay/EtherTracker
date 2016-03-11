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
package com.og.finance.ether.functional.receivers;

import android.app.PendingIntent;
import android.content.Intent;

import com.og.finance.ether.functional.AbstractFunctionalTest;
import com.og.finance.ether.receivers.AutoUpdateReceiver;
import com.robotium.solo.Condition;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class AutoUpdateReceiverTest extends AbstractFunctionalTest {

    /**
     * Test {@link com.og.finance.ether.receivers.AutoUpdateReceiver}
     * Test that the {@link PendingIntent} is well registered.
     */
    public void testAutoUpdateReceiver() {
        Intent intentStop = new Intent();
        intentStop.setAction(AutoUpdateReceiver.STOP_UPDATE_SERVICE);
        mSolo.getCurrentActivity().sendBroadcast(intentStop);

        final Intent retrieverIntent = new Intent(getActivity(), AutoUpdateReceiver.class);
        retrieverIntent.setAction(AutoUpdateReceiver.STOP_UPDATE_SERVICE);
        PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), AutoUpdateReceiver.AUTO_UPDATE_SERVICE_ID, retrieverIntent, PendingIntent.FLAG_NO_CREATE);
        //If that doesn't work, it's maybe because you launched something else before
        assertEquals(null, pIntent);

        //start
        retrieverIntent.setAction(AutoUpdateReceiver.START_UPDATE_SERVICE);
        Intent intent = new Intent();
        intent.setAction(AutoUpdateReceiver.START_UPDATE_SERVICE);
        mSolo.getCurrentActivity().sendBroadcast(intent);

        mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                PendingIntent pIntent = PendingIntent.getBroadcast(getActivity(), AutoUpdateReceiver.AUTO_UPDATE_SERVICE_ID, retrieverIntent, PendingIntent.FLAG_NO_CREATE);
                return pIntent != null;
            }
        }, 20000);
        assertNotNull(PendingIntent.getBroadcast(getActivity(), AutoUpdateReceiver.AUTO_UPDATE_SERVICE_ID, retrieverIntent, PendingIntent.FLAG_NO_CREATE));
    }

}
