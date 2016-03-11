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
package com.og.finance.ether.functional;

import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

import com.og.finance.ether.activities.MainActivity;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;
import com.robotium.solo.Solo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AbstractFunctionalTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * The {@link Solo} object used for the tests
     */
    protected Solo mSolo;

    public AbstractFunctionalTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mSolo = new Solo(getInstrumentation(), getActivity());
        wakeUpScreen();

        //Just deleting the SharedPreferences, to be sure we start fresh
        SharedPreferencesUtilities.deleteKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        SharedPreferencesUtilities.deleteKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID);
        SharedPreferencesUtilities.deleteKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE);
    }

    /**
     * Called at the end of each tests (allows to log out and finish all the activities)
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
        super.tearDown();
    }

    /**
     * Wake up the screen if not already done
     */
    private void wakeUpScreen() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
            }
        });
        mSolo.unlockScreen();
    }

    /**
     * Allows to click on a view by its id
     */
    protected void clickOnView(int viewId) {
        mSolo.clickOnView(mSolo.getView(viewId));
    }
}