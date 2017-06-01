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
package com.og.finance.ether.unit.network.apis;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.CoinMarketPriceApi;
import com.og.finance.ether.unit.AbstractUnitTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by olivier.goutay on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class CoinMarketEtherApiTest extends AbstractUnitTest {

    /**
     * Test for {@link CoinMarketEtherApi#getPriceValue()}
     */
    @Test
    public void testGetPriceValue() {
        //Test null
        CoinMarketEtherApi coinMarketEtherApi = new CoinMarketEtherApi(null, null);
        assertNull(coinMarketEtherApi.getPriceValue());

        //Test null 2
        coinMarketEtherApi = new CoinMarketEtherApi(null, 10.0f);
        assertNull(coinMarketEtherApi.getPriceValue());

        //Test null 3
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(null, null), null);
        assertNull(coinMarketEtherApi.getPriceValue());

        //Test value set
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(10.0f, null), null);
        assertEquals(10.0f, coinMarketEtherApi.getPriceValue());
    }

    /**
     * Test for {@link CoinMarketEtherApi#getPriceChange()}
     */
    @Test
    public void testGetPriceChange() {
        //Test null
        CoinMarketEtherApi coinMarketEtherApi = new CoinMarketEtherApi(null, null);
        assertNull(coinMarketEtherApi.getChange());

        //Test null 2
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(10.0f, null), null);
        assertNull(coinMarketEtherApi.getChange());

        //Test value set
        coinMarketEtherApi = new CoinMarketEtherApi(null, 10.0f);
        assertEquals(10.0f, coinMarketEtherApi.getChange());
    }

}
