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

import java.util.List;

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
public class KrakenPriceApi extends Api {

    @SerializedName("c")
    private List<Float> mLastPrice;

    @SerializedName("o")
    private Float mTodayPrice;

    public KrakenPriceApi(List<Float> mLastPrice, Float mTodayPrice) {
        this.mLastPrice = mLastPrice;
        this.mTodayPrice = mTodayPrice;
    }

    public List<Float> getLastPrice() {
        return mLastPrice;
    }

    public void setLastPrice(List<Float> lastPrice) {
        this.mLastPrice = lastPrice;
    }

    public Float getTodayPrice() {
        return mTodayPrice;
    }

    public void setTodayPrice(Float todayPrice) {
        this.mTodayPrice = todayPrice;
    }
}
