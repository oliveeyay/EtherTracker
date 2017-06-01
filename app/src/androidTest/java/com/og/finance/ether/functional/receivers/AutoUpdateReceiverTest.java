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
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.og.finance.ether.activities.MainActivity;
import com.og.finance.ether.functional.AbstractEspressoTest;
import com.og.finance.ether.functional.Condition;
import com.og.finance.ether.receivers.AutoUpdateReceiver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by olivier.goutay on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AutoUpdateReceiverTest extends AbstractEspressoTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    /**
     * Test {@link com.og.finance.ether.receivers.AutoUpdateReceiver}
     * Test that the {@link PendingIntent} is well registered.
     */
    @Test
    public void testAutoUpdateReceiver() {
        Intent intentStop = new Intent();
        intentStop.setAction(AutoUpdateReceiver.Companion.getSTOP_UPDATE_SERVICE());
        getCurrentActivity().sendBroadcast(intentStop);

        final Intent retrieverIntent = new Intent(getCurrentActivity(), AutoUpdateReceiver.class);
        retrieverIntent.setAction(AutoUpdateReceiver.Companion.getSTOP_UPDATE_SERVICE());
        PendingIntent pIntent = PendingIntent.getBroadcast(getCurrentActivity(), AutoUpdateReceiver.Companion.getAUTO_UPDATE_SERVICE_ID(), retrieverIntent, PendingIntent.FLAG_NO_CREATE);
        //If that doesn't work, it's maybe because you launched something else before
        assertEquals(null, pIntent);

        //start
        retrieverIntent.setAction(AutoUpdateReceiver.Companion.getSTART_UPDATE_SERVICE());
        Intent intent = new Intent();
        intent.setAction(AutoUpdateReceiver.Companion.getSTART_UPDATE_SERVICE());
        getCurrentActivity().sendBroadcast(intent);

        waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                PendingIntent pIntent = PendingIntent.getBroadcast(getCurrentActivity(), AutoUpdateReceiver.Companion.getAUTO_UPDATE_SERVICE_ID(), retrieverIntent, PendingIntent.FLAG_NO_CREATE);
                return pIntent != null;
            }
        }, 20000);
        assertNotNull(PendingIntent.getBroadcast(getCurrentActivity(), AutoUpdateReceiver.Companion.getAUTO_UPDATE_SERVICE_ID(), retrieverIntent, PendingIntent.FLAG_NO_CREATE));
    }

}
