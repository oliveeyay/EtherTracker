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
package com.og.finance.ether.network.apis;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olivier.goutay on 2/29/16.
 * {
 * "symbol": "eth",
 * "position": "2",
 * "market_cap": {
 * "usd": "488394912.924",
 * "eur": "449048353.55410385",
 * "cny": "3199284600.5490837",
 * "cad": "661215894.836722",
 * "rub": "36814729420.801544",
 * "btc": "1116180.60997"
 * },
 * "price": {
 * "usd": "6.30912",
 * "eur": "5.800838365440001",
 * "cny": "41.3285845632",
 * "cad": "8.5416336576",
 * "rub": "475.57527635328",
 * "btc": "0.0144189"
 * },
 * "supply": "77410940",
 * "volume": {
 * "usd": "7612870",
 * "eur": "6999554.35419",
 * "cny": "49868942.3507",
 * "cad": "10306722.11385",
 * "rub": "573850672.37453",
 * "btc": "17398.5"
 * },
 * "change": "-2.34",
 * "timestamp": 1456788610.953
 * }
 */
public class EtherApi extends Api {

    @SerializedName("price")
    private PriceApi mPriceApi;

    @SerializedName("change")
    private Float mChange;

    public EtherApi(PriceApi priceApi, Float change) {
        this.mPriceApi = priceApi;
        this.mChange = change;
    }

    public PriceApi getPrice() {
        return mPriceApi;
    }

    public void setPrice(PriceApi priceApi) {
        this.mPriceApi = priceApi;
    }

    public Float getChange() {
        return mChange;
    }

    public void setChange(Float change) {
        this.mChange = change;
    }
}
