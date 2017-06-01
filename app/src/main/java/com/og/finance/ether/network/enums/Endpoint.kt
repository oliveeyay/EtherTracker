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
package com.og.finance.ether.network.enums

import android.content.Context

import com.og.finance.ether.network.apis.Api
import com.og.finance.ether.network.apis.CoinMarketEtherApi
import com.og.finance.ether.network.apis.KrakenEtherApi
import com.og.finance.ether.network.apis.PolionexEtherApi
import com.og.finance.ether.utilities.SharedPreferencesUtilities

/**
 * Created by olivier.goutay on 3/7/16.
 */
enum class Endpoint constructor(val id: Int, val url: String, val etherApiClass: Class<out Api>, val endpointName: String) {

    POLONIEX(1, "https://poloniex.com/", PolionexEtherApi::class.java, "Poloniex"),
    COIN_MARKET_CAP(2, "http://coinmarketcap-nexuist.rhcloud.com/", CoinMarketEtherApi::class.java, "CoinMarketApi"),
    KRAKEN(3, "https://api.kraken.com/0/public/", KrakenEtherApi::class.java, "Kraken");


    companion object {

        /**
         * Find an endpoint by it's id
         */
        fun getFromId(id: Int): Endpoint {
            return Endpoint.values().firstOrNull { it.id == id }
                    ?: POLONIEX
        }

        /**
         * Returns the current selected [Endpoint]. By Default it's using [com.og.finance.ether.network.services.CoinMarketCapEtherService]
         * @return
         */
        fun getCurrentEndpoint(context: Context): Endpoint {
            return Endpoint.getFromId(SharedPreferencesUtilities.getIntForKey(context, SharedPreferencesUtilities.SHARED_ENDPOINT_ID))
        }
    }
}
