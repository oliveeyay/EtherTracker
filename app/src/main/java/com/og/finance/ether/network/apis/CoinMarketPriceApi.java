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
 * "price": {
 * "usd": "6.30912",
 * "eur": "5.800838365440001",
 * "cny": "41.3285845632",
 * "cad": "8.5416336576",
 * "rub": "475.57527635328",
 * "btc": "0.0144189"
 * },
 */
public class CoinMarketPriceApi extends Api {

    @SerializedName("usd")
    private Float mUsd;

    @SerializedName("btc")
    private Float mBtc;

    public CoinMarketPriceApi(Float usd, Float btc) {
        this.mUsd = usd;
        this.mBtc = btc;
    }

    public Float getUsd() {
        return mUsd;
    }

    public void setUsd(Float usd) {
        this.mUsd = usd;
    }

    public Float getBtc() {
        return mBtc;
    }

    public void setBtc(Float btc) {
        this.mBtc = btc;
    }
}
