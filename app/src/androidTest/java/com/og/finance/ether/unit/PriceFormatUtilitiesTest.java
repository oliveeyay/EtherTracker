package com.og.finance.ether.unit;

import com.og.finance.ether.R;
import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.CoinMarketPriceApi;
import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.network.apis.KrakenPriceApi;
import com.og.finance.ether.network.apis.KrakenResultApi;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.apis.PolionexPriceApi;
import com.og.finance.ether.utilities.PriceFormatUtilities;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import java.util.ArrayList;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class PriceFormatUtilitiesTest extends AbstractUnitTest {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //Just deleting the SharedPreferences, to be sure we start fresh
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID);
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE);
    }

    /**
     * Test {@link com.og.finance.ether.utilities.PriceFormatUtilities#getPriceFormatted(AbstractEtherApi)}
     */
    public void testGetPriceFormatted() {
        String networkError = getContext().getResources().getString(R.string.network_error);

        //Try null AbstractEtherApi
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(null));

        //Try no PriceValue
        PolionexEtherApi polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(null, 20.0f));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(polionexEtherApi));

        //Try no Change
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, null));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(polionexEtherApi));

        //Try complete
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, 20.0f));
        String result = PriceFormatUtilities.getPriceFormatted(polionexEtherApi);
        assertTrue(result.contains("11") && result.contains("20"));

        //Try with buying price
        SharedPreferencesUtilities.storeFloatForKey(getContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE, 10.0f);
        result = PriceFormatUtilities.getPriceFormatted(polionexEtherApi);
        assertTrue(result.contains("11") && result.contains("20") && result.contains("10"));

        //Try coinmarketcap api
        CoinMarketEtherApi coinMarketEtherApi = new CoinMarketEtherApi(null, null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(null, 20.0f);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(11.0f, 11.0f), null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(coinMarketEtherApi));
        coinMarketEtherApi = new CoinMarketEtherApi(new CoinMarketPriceApi(11.0f, 11.0f), 20.0f);
        result = PriceFormatUtilities.getPriceFormatted(coinMarketEtherApi);
        assertTrue(result.contains("11") && result.contains("20") && result.contains("10"));

        //Try kraken api
        KrakenEtherApi krakenEtherApi = new KrakenEtherApi(null);
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, 8.0f)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(krakenEtherApi));
        ArrayList<Float> arrayList = new ArrayList();
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(krakenEtherApi));
        arrayList.add(11.0f);
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertEquals(networkError, PriceFormatUtilities.getPriceFormatted(krakenEtherApi));
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, 8.0f)));
        result = PriceFormatUtilities.getPriceFormatted(krakenEtherApi);
        assertTrue(result.contains("11") && result.contains("37.5") && result.contains("10"));
    }
}