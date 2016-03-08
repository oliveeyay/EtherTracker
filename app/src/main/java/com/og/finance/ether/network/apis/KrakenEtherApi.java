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
package com.og.finance.ether.network.apis;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olivier.goutay on 2/29/16.
 * {"error":[],"result":
 * {"XETHZUSD":
 * {"a":["9.49999","48","48.000"],
 * "b":["9.40008","109","109.000"],
 * "c":["9.40000","0.10786090"],
 * "v":["57320.65433615","61086.50682343"],
 * "p":["10.06483","10.10108"],"t":[2197,2462],
 * "l":["9.05500","9.05500"],
 * "h":["11.46999","11.46999"],
 * "o":"11.18745"}}}
 */
public class KrakenEtherApi extends BaseEtherApi {

    @SerializedName("result")
    private KrakenResultApi mResultApi;

    public KrakenEtherApi(KrakenResultApi priceApi) {
        this.mResultApi = priceApi;
    }

    @Override
    public Float getPriceValue() {
        if (mResultApi != null && mResultApi.getPrice() != null && mResultApi.getPrice().getLastPrice() != null && !mResultApi.getPrice().getLastPrice().isEmpty()) {
            return mResultApi.getPrice().getLastPrice().get(0);
        }

        return null;
    }

    @Override
    public Float getPriceChange() {
        if (mResultApi != null && mResultApi.getPrice() != null && mResultApi.getPrice().getLastPrice() != null && !mResultApi.getPrice().getLastPrice().isEmpty() && mResultApi.getPrice().getTodayPrice() != null) {
            float now = mResultApi.getPrice().getLastPrice().get(0);
            return ((now / mResultApi.getPrice().getTodayPrice()) - 1) * 100;
        }

        return null;
    }

    public KrakenResultApi getPrice() {
        return mResultApi;
    }

    public void setPrice(KrakenResultApi priceApi) {
        this.mResultApi = priceApi;
    }
}
