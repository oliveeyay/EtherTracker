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
package com.og.finance.ether.unit.enums;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.unit.AbstractUnitTest;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by olivier.goutay on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class EndpointTest extends AbstractUnitTest {

    /**
     * Test of {@link com.og.finance.ether.network.enums.Endpoint#getFromId(int)}
     */
    @Test
    public void testGetFromId() {
        //Not existing
        assertEquals(Endpoint.POLONIEX, Endpoint.Companion.getFromId(18000));

        //Polionex
        assertEquals(Endpoint.POLONIEX, Endpoint.Companion.getFromId(Endpoint.POLONIEX.getId()));

        //CoinMarketCap
        assertEquals(Endpoint.COIN_MARKET_CAP, Endpoint.Companion.getFromId(Endpoint.COIN_MARKET_CAP.getId()));

        //Kraken
        assertEquals(Endpoint.KRAKEN, Endpoint.Companion.getFromId(Endpoint.KRAKEN.getId()));
    }

    /**
     * Test of {@link Endpoint#getCurrentEndpoint(android.content.Context)}
     */
    @Test
    public void testGetCurrentEndpoint() {
        //Default
        assertEquals(Endpoint.POLONIEX, Endpoint.Companion.getCurrentEndpoint(getContext()));

        //Store CoinMarketCap
        SharedPreferencesUtilities.storeIntForKey(getContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.COIN_MARKET_CAP.getId());
        assertEquals(Endpoint.COIN_MARKET_CAP, Endpoint.Companion.getCurrentEndpoint(getContext()));

        //Store Kraken
        SharedPreferencesUtilities.storeIntForKey(getContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.KRAKEN.getId());
        assertEquals(Endpoint.KRAKEN, Endpoint.Companion.getCurrentEndpoint(getContext()));

        //Store Polionex
        SharedPreferencesUtilities.storeIntForKey(getContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.POLONIEX.getId());
        assertEquals(Endpoint.POLONIEX, Endpoint.Companion.getCurrentEndpoint(getContext()));
    }

}
