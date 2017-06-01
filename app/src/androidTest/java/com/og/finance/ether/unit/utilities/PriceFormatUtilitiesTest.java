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

import com.og.finance.ether.R;
import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.CoinMarketPriceApi;
import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.network.apis.KrakenPriceApi;
import com.og.finance.ether.network.apis.KrakenResultApi;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.apis.PolionexPriceApi;
import com.og.finance.ether.unit.AbstractUnitTest;
import com.og.finance.ether.utilities.PriceFormatUtilities;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import java.util.ArrayList;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class PriceFormatUtilitiesTest extends AbstractUnitTest {

    /**
     * Test {@link com.og.finance.ether.utilities.PriceFormatUtilities#getPriceFormatted(android.content.Context, AbstractEtherApi)}
     */
    public void testGetPriceFormatted() {
        String networkError = getContext().getResources().getString(R.string.network_error);

        //Try null AbstractEtherApi
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), null));

        //Try no PriceValue
        PolionexEtherApi polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(null, 20.0f));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), polionexEtherApi));

        //Try no Change
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, null));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), polionexEtherApi));

        //Try complete
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, 20.0f));
        String result = PriceFormatUtilities.getPriceFormatted(getContext(), polionexEtherApi);
        assertTrue(result.contains("11") && result.contains("20"));

        //Try with buying price
        SharedPreferencesUtilities.storeFloatForKey(getContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE, 10.0f);
        result = PriceFormatUtilities.getPriceFormatted(getContext(), polionexEtherApi);
        assertTrue(result.contains("11") && result.contains("20") && result.contains("10"));

        //Try coinmarketcap api
        CoinMarketEtherApi coinMarketEtherApi = new CoinMarketEtherApi(null, null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(null, 20.0f);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(11.0f, 11.0f), null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(11.0f, 11.0f), 20.0f);
        result = PriceFormatUtilities.getPriceFormatted(getContext(), coinMarketEtherApi);
        assertTrue(result.contains("11") && result.contains("20") && result.contains("10"));

        //Try kraken api
        KrakenEtherApi krakenEtherApi = new KrakenEtherApi(null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, 8.0f)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi));
        ArrayList<Float> arrayList = new ArrayList<>();
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi));
        arrayList.add(11.0f);
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, 8.0f)));
        result = PriceFormatUtilities.getPriceFormatted(getContext(), krakenEtherApi);
        assertTrue(result.contains("11") && result.contains("37.5") && result.contains("10"));
    }

    /**
     * Test {@link com.og.finance.ether.utilities.PriceFormatUtilities#getPriceFromBuying(android.content.Context, AbstractEtherApi)}
     */
    public void testGetPriceFromBuying() {
        //No buying price and api
        assertEquals("", PriceFormatUtilities.getPriceFromBuying(getContext(), null));

        //Api but no buying
        PolionexEtherApi polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, 0.11f));
        assertEquals("", PriceFormatUtilities.getPriceFromBuying(getContext(), polionexEtherApi));

        //Buying but no api
        SharedPreferencesUtilities.storeFloatForKey(getContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE, 10.0f);
        assertEquals("", PriceFormatUtilities.getPriceFromBuying(getContext(), null));

        //Both there
        assertTrue(PriceFormatUtilities.getPriceFromBuying(getContext(), polionexEtherApi).contains("10"));
    }

    /**
     * Test {@link com.og.finance.ether.utilities.PriceFormatUtilities#formatTwoDecimals(Float)}
     */
    public void testFormatTwoDecimals() {
        //Test null
        assertEquals("", PriceFormatUtilities.formatTwoDecimals(null));

        //Test format
        assertEquals("10.02", PriceFormatUtilities.formatTwoDecimals(10.0222222f));
    }

}
