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
package com.og.finance.ether.functional.fragments;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.og.finance.ether.R;
import com.og.finance.ether.activities.MainActivity;
import com.og.finance.ether.databinding.FragmentSettingsBinding;
import com.og.finance.ether.fragments.SettingsFragment;
import com.og.finance.ether.functional.AbstractEspressoTest;
import com.og.finance.ether.functional.Condition;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by olivier.goutay on 3/9/16.
 * Test of {@link com.og.finance.ether.activities.MainActivity}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsFragmentTest extends AbstractEspressoTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    /**
     * Test the {@link FragmentSettingsBinding#fragmentSettingsRadioPolionex} and others
     */
    @Test
    public void testRadioButtonChangeEndpoint() throws InterruptedException {
        //Go to SettingsFragment
        goToSettings();

        //Click on coinmarketcap
        clickOnView(R.id.fragment_settings_radio_coinmarketcap);
        assertEquals(2, SharedPreferencesUtilities.INSTANCE.getIntForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_ENDPOINT_ID()));

        //Go back to home and test source
        Espresso.pressBack();
        waitForCondition(new Condition() {
            @Override public boolean isSatisfied() {
                return ((TextView) getView(R.id.fragment_home_source)).getText().toString().contains(Endpoint.COIN_MARKET_CAP.getEndpointName());
            }
        }, 5000);
        assertTrue(((TextView) getView(R.id.fragment_home_source)).getText().toString().contains(Endpoint.COIN_MARKET_CAP.getEndpointName()));
        goToSettings();

        //Click on Kraken
        clickOnView(R.id.fragment_settings_radio_kraken);
        assertEquals(3, SharedPreferencesUtilities.INSTANCE.getIntForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_ENDPOINT_ID()));

        //Click on Polionex again
        clickOnView(R.id.fragment_settings_radio_polionex);
        assertEquals(1, SharedPreferencesUtilities.INSTANCE.getIntForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_ENDPOINT_ID()));

        //Go back to home and test source
        Espresso.pressBack();
        waitForCondition(new Condition() {
            @Override public boolean isSatisfied() {
                return ((TextView) getView(R.id.fragment_home_source)).getText().toString().contains(Endpoint.POLONIEX.getEndpointName());
            }
        }, 5000);
        assertTrue(((TextView) getView(R.id.fragment_home_source)).getText().toString().contains(Endpoint.POLONIEX.getEndpointName()));
    }

    /**
     * Test the {@link FragmentSettingsBinding#fragmentSettingsNotificationCheckbox}
     * Some asserts only available on marshmallow
     */
    @Test
    public void testCheckboxPersistentNotification() {
        //Go to SettingsFragment
        goToSettings();

        //Test notification enabled by default
        final CheckBox checkBox = (CheckBox) getView(R.id.fragment_settings_notification_checkbox);
        assertTrue(checkBox.isChecked());
        assertTrue(SharedPreferencesUtilities.INSTANCE.getBooleanForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_SERVICE_ACTIVE()));

        //Test disable notification
        clickOnView(R.id.fragment_settings_notification_checkbox);
        assertFalse(checkBox.isChecked());
        assertFalse(SharedPreferencesUtilities.INSTANCE.getBooleanForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_SERVICE_ACTIVE()));

        //Test re-enable notification
        clickOnView(R.id.fragment_settings_notification_checkbox);
        assertTrue(checkBox.isChecked());
        assertTrue(SharedPreferencesUtilities.INSTANCE.getBooleanForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_SERVICE_ACTIVE()));
    }

    /**
     * Test the {@link FragmentSettingsBinding#fragmentSettingsEdittext}
     */
    @Test
    public void testInputBuyingPrice() {
        //No buying price on the HomeFragment
        assertEquals(View.GONE, getView(R.id.fragment_home_separator_2).getVisibility());
        assertEquals(View.GONE, getView(R.id.fragment_home_change_buying_layout).getVisibility());

        //Go to SettingsFragment
        goToSettings();

        //No buying price
        assertEquals(0.0f, SharedPreferencesUtilities.INSTANCE.getFloatForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE()));

        //Buying price saved
        replaceText(R.id.fragment_settings_edittext, "10");
        assertEquals(0.0f, SharedPreferencesUtilities.INSTANCE.getFloatForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE()));
        clickOnView(R.id.fragment_settings_edittext_save_button);
        assertEquals(10.0f, SharedPreferencesUtilities.INSTANCE.getFloatForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE()));

        //Check HomeFragment
        Espresso.pressBack();
        assertEquals(View.VISIBLE, getView(R.id.fragment_home_separator_2).getVisibility());
        assertEquals(View.VISIBLE, getView(R.id.fragment_home_change_buying_layout).getVisibility());
        goToSettings();

        //Reset buying price
        replaceText(R.id.fragment_settings_edittext, "");
        assertEquals(10.0f, SharedPreferencesUtilities.INSTANCE.getFloatForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE()));
        clickOnView(R.id.fragment_settings_edittext_save_button);
        assertEquals(0.0f, SharedPreferencesUtilities.INSTANCE.getFloatForKey(getContext(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE()));
    }

    /**
     * Go to the {@link SettingsFragment by clicking on views.}
     */
    private void goToSettings() {
        clickOnToolbarNavigationIcon();
        clickOnView(R.id.navigation_drawer_settings);
        assertTrue(getCurrentFrag().getClass().getName().equals(SettingsFragment.class.getName()));
    }
}
