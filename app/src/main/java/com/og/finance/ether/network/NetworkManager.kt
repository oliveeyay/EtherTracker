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
package com.og.finance.ether.network

import android.content.Context

import com.crashlytics.android.Crashlytics
import com.og.finance.ether.network.apis.AbstractEtherApi
import com.og.finance.ether.network.apis.CoinMarketEtherApi
import com.og.finance.ether.network.apis.KrakenEtherApi
import com.og.finance.ether.network.apis.PolionexEtherApi
import com.og.finance.ether.network.enums.Endpoint
import com.og.finance.ether.network.services.CoinMarketCapEtherService
import com.og.finance.ether.network.services.KrakenEtherService
import com.og.finance.ether.network.services.PolionexEtherService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by olivier.goutay on 2/29/16.
 * Uses [Retrofit] to call the ether api
 */
class NetworkManager<T : AbstractEtherApi> {

    /**
     * Do a generic call and returns it to the main [Thread] through the [NetworkCallback]
     */
    private fun getResponse(call: Call<T>, callback: NetworkCallback<AbstractEtherApi>) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.updateApi(response.body())
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                //Logging also with crashlytics to have a volume of failure
                Crashlytics.logException(t)

                callback.updateApi(null)
            }
        })
    }

    companion object {

        /**
         * The instance of [Retrofit]
         */
        private var mRetrofit: Retrofit? = null

        /**
         * Returns the latest [CoinMarketEtherApi] through the [Retrofit] interface [NetworkCallback]

         * @param callback
         */
        fun getCurrentEthValue(context: Context, callback: NetworkCallback<AbstractEtherApi>) {
            val endpoint = Endpoint.getCurrentEndpoint(context)

            val retrofit = getRetrofit(endpoint)

            when (endpoint) {
                Endpoint.KRAKEN -> {
                    val krakenEtherService = retrofit.create(KrakenEtherService::class.java)
                    NetworkManager<KrakenEtherApi>().getResponse(krakenEtherService.currentEthValue, callback)
                }
                Endpoint.COIN_MARKET_CAP -> {
                    val coinMarketCapEtherService = retrofit.create(CoinMarketCapEtherService::class.java)
                    NetworkManager<CoinMarketEtherApi>().getResponse(coinMarketCapEtherService.currentEthValue, callback)
                }
                Endpoint.POLONIEX -> {
                    val polionexEtherService = retrofit.create(PolionexEtherService::class.java)
                    NetworkManager<PolionexEtherApi>().getResponse(polionexEtherService.currentEthValue, callback)
                }
            }
        }

        /**
         * Get the [Retrofit] instance
         */
        private fun getRetrofit(endpoint: Endpoint): Retrofit {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(endpoint.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return mRetrofit as Retrofit
        }
    }
}
