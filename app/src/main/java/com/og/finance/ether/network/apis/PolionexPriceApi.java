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
 * Created by olivier.goutay on 3/7/16.
 * {"USDT_ETH":{"last":"11.60000000","lowestAsk":"11.87130074","highestBid":"11.60000001"
 * ,"percentChange":"0.18974359","baseVolume":"72674.27355160","quoteVolume":"6544.84663130",
 * "isFrozen":"0","high24hr":"11.90924998","low24hr":"9.65000000"}}
 */
public class PolionexPriceApi extends Api {

    @SerializedName("last")
    private Float mLastPrice;

    @SerializedName("percentChange")
    private Float mChange;

    public PolionexPriceApi(Float lastPrice, Float change) {
        this.mLastPrice = lastPrice;
        this.mChange = change;
    }

    public Float getLastPrice() {
        return mLastPrice;
    }

    public void setLastPrice(Float lastPrice) {
        this.mLastPrice = lastPrice;
    }

    public Float getChange() {
        return mChange;
    }

    public void setChange(Float change) {
        this.mChange = change;
    }
}
