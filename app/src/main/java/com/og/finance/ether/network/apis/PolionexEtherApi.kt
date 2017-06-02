/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.network.apis

import com.google.gson.annotations.SerializedName

/**
 * Created by olivier.goutay on 3/9/16.
 * {"USDT_ETH":{"last":"11.60000000","lowestAsk":"11.87130074","highestBid":"11.60000001"
 * ,"percentChange":"0.18974359","baseVolume":"72674.27355160","quoteVolume":"6544.84663130",
 * "isFrozen":"0","high24hr":"11.90924998","low24hr":"9.65000000"}}
 */
class PolionexEtherApi(@SerializedName("USDT_ETH")
                       var priceApi: PolionexPriceApi?) : AbstractEtherApi() {

    override fun getPriceValue(): Float? {
        return priceApi?.lastPrice
    }

    override fun getPriceChange(): Float? {
        return priceApi?.change?.times(100)
    }
}
