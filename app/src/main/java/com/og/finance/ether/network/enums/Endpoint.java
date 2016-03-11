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
package com.og.finance.ether.network.enums;

import com.og.finance.ether.application.EtherApplication;
import com.og.finance.ether.network.apis.Api;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/7/16.
 */
public enum Endpoint {

    POLIONEX(1, "https://poloniex.com/", PolionexEtherApi.class),
    COIN_MARKET_CAP(2, "http://coinmarketcap-nexuist.rhcloud.com/", CoinMarketEtherApi.class),
    KRAKEN(3, "https://api.kraken.com/0/public/", KrakenEtherApi.class) ;

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
        return POLIONEX;
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
