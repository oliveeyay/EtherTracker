/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.functional.fragments;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.og.finance.ether.R;
import com.og.finance.ether.activities.MainActivity;
import com.og.finance.ether.fragments.HomeFragment;
import com.og.finance.ether.functional.AbstractEspressoTest;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.apis.PolionexPriceApi;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by olivier.goutay on 3/9/16.
 * Test of {@link com.og.finance.ether.activities.MainActivity}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeFragmentTest extends AbstractEspressoTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    /**
     * Test {@link com.og.finance.ether.databinding.FragmentHomeBinding}
     */
    @Test
    public void testHomeFragmentBinding() {
        HomeFragment homeFragment = (HomeFragment) getCurrentFrag();

        //Test endpoint null
        homeFragment.getBinding().setEndpoint(null);
        executePendingBindings(homeFragment.getBinding());
        assertEquals("", ((TextView) getView(R.id.fragment_home_source)).getText().toString());

        //Test endpoint not null
        homeFragment.getBinding().setEndpoint(Endpoint.KRAKEN);
        executePendingBindings(homeFragment.getBinding());
        assertTrue(((TextView) getView(R.id.fragment_home_source)).getText().toString().contains(Endpoint.KRAKEN.getEndpointName()));

        //Test api null
        SharedPreferencesUtilities.INSTANCE.storeFloatForKey(getCurrentActivity(), SharedPreferencesUtilities.INSTANCE.getSHARED_BUYING_VALUE(), 10.0f);
        homeFragment.getBinding().setEtherApi(null);
        executePendingBindings(homeFragment.getBinding());
        assertEquals("", ((TextView) getView(R.id.fragment_home_change)).getText().toString());
        assertEquals("", ((TextView) getView(R.id.fragment_home_price_textview)).getText().toString());
        assertEquals(View.GONE, getView(R.id.fragment_home_separator_2).getVisibility());
        assertEquals(View.GONE, getView(R.id.fragment_home_change_buying_layout).getVisibility());

        //Test api not null
        homeFragment.getBinding().setEtherApi(new PolionexEtherApi(new PolionexPriceApi(11.0f, 0.15f)));
        executePendingBindings(homeFragment.getBinding());
        assertTrue(((TextView) getView(R.id.fragment_home_change)).getText().toString().contains("15"));
        assertTrue(((TextView) getView(R.id.fragment_home_price_textview)).getText().toString().contains("11"));
        assertEquals(View.VISIBLE, getView(R.id.fragment_home_separator_2).getVisibility());
        assertEquals(View.VISIBLE, getView(R.id.fragment_home_change_buying_layout).getVisibility());
        assertTrue(((TextView) getView(R.id.fragment_home_change_buying_textview)).getText().toString().contains("10"));
    }

}
