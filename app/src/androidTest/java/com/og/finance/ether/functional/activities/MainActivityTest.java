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
package com.og.finance.ether.functional.activities;

import android.widget.CheckBox;

import com.og.finance.ether.R;
import com.og.finance.ether.databinding.ActivityMainBinding;
import com.og.finance.ether.functional.AbstractFunctionalTest;
import com.og.finance.ether.functional.services.AutoUpdateServiceTest;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/9/16.
 * Test of {@link com.og.finance.ether.activities.MainActivity}
 */
public class MainActivityTest extends AbstractFunctionalTest {

    /**
     * Test the {@link ActivityMainBinding#activityMainRadioKraken} and others
     */
    public void testRadioButtonChangeEndpoint() {
        //Default
        assertEquals(1, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on coinmarketcap
        clickOnView(R.id.activity_main_radio_coinmarketcap);
        mSolo.sleep(500);
        assertEquals(2, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on Kraken
        clickOnView(R.id.activity_main_radio_kraken);
        mSolo.sleep(500);
        assertEquals(3, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on Polionex again
        clickOnView(R.id.activity_main_radio_polionex);
        mSolo.sleep(500);
        assertEquals(1, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));
    }

    /**
     * Test the {@link ActivityMainBinding#activityMainNotificationCheckbox}
     * Some asserts only available on marshmallow
     */
    public void testCheckboxPersistentNotification() {
        //Test notification enabled by default
        CheckBox checkBox = (CheckBox) mSolo.getView(R.id.activity_main_notification_checkbox);
        assertTrue(checkBox.isChecked());
        assertTrue(SharedPreferencesUtilities.getBooleanForKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE));
        AutoUpdateServiceTest.assertNotificationLength(getActivity(), mSolo, 1);

        //Test disable notification
        clickOnView(R.id.activity_main_notification_checkbox);
        mSolo.sleep(500);
        assertFalse(checkBox.isChecked());
        assertFalse(SharedPreferencesUtilities.getBooleanForKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE));
        AutoUpdateServiceTest.assertNotificationLength(getActivity(), mSolo, 0);

        //Test re-enable notification
        clickOnView(R.id.activity_main_notification_checkbox);
        mSolo.sleep(500);
        assertTrue(checkBox.isChecked());
        assertTrue(SharedPreferencesUtilities.getBooleanForKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE));
        AutoUpdateServiceTest.assertNotificationLength(getActivity(), mSolo, 1);
    }

    /**
     * Test the {@link ActivityMainBinding#activityMainEdittext}
     */
    public void testInputBuyingPrice() {
        //No buying price
        assertEquals(0.0f, SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE));

        //Buying price saved
        mSolo.enterText(0, "10");
        assertEquals(0.0f, SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE));
        clickOnView(R.id.activity_main_edittext_save_button);
        mSolo.sleep(500);
        assertEquals(10.0f, SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE));

        //Reset buying price
        mSolo.clearEditText(0);
        assertEquals(10.0f, SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE));
        clickOnView(R.id.activity_main_edittext_save_button);
        mSolo.sleep(500);
        assertEquals(0.0f, SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE));
    }
}
