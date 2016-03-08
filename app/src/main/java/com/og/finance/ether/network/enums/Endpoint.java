package com.og.finance.ether.network.enums;

import com.og.finance.ether.application.EtherApplication;
import com.og.finance.ether.network.apis.Api;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/7/16.
 */
public enum Endpoint {

    COIN_MARKET_CAP(1, "http://coinmarketcap-nexuist.rhcloud.com/", CoinMarketEtherApi.class),
    KRAKEN(2, "https://api.kraken.com/0/public/", KrakenEtherApi.class);

    private int mId;
    private String mUrl;
    private Class<? extends Api> mEtherApiClass;

    Endpoint(int id, String url, Class<? extends Api> etherApiClass) {
        mId = id;
        mUrl = url;
        mEtherApiClass = etherApiClass;
    }

    /**
     * Find an endpoint by it's id
     */
    public static Endpoint getFromId(int id) {
        for (Endpoint endpoint : Endpoint.values()) {
            if (endpoint.mId == id) {
                return endpoint;
            }
        }
        return COIN_MARKET_CAP;
    }

    /**
     * Returns the current selected {@link Endpoint}. By Default it's using {@link com.og.finance.ether.network.services.CoinMarketCapEtherService}
     * @return
     */
    public static Endpoint getCurrentEndpoint() {
        return Endpoint.getFromId(SharedPreferencesUtilities.getIntForKey(EtherApplication.getAppContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));
    }

    public int getId() {
        return mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public Class<? extends Api> getEtherApiClass() {
        return mEtherApiClass;
    }
}
