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
package com.og.finance.ether.network;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;
import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.network.services.CoinMarketCapEtherService;
import com.og.finance.ether.network.services.KrakenEtherService;
import com.og.finance.ether.network.services.PolionexEtherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by olivier.goutay on 2/29/16.
 * Uses {@link Retrofit} to call the ether api
 */
public class NetworkManager<T extends AbstractEtherApi> {

    /**
     * The instance of {@link Retrofit}
     */
    private static Retrofit mRetrofit;

    /**
     * Returns the latest {@link CoinMarketEtherApi} through the {@link Retrofit} interface {@link NetworkCallback}
     *
     * @param callback
     */
    public static void getCurrentEthValue(Context context, NetworkCallback<AbstractEtherApi> callback) {
        Endpoint endpoint = Endpoint.getCurrentEndpoint(context);

        Retrofit retrofit = getRetrofit(endpoint);

        switch (endpoint) {
            case KRAKEN:
                KrakenEtherService krakenEtherService = retrofit.create(KrakenEtherService.class);
                new NetworkManager<KrakenEtherApi>().getResponse(krakenEtherService.getCurrentEthValue(), callback);
                break;
            case COIN_MARKET_CAP:
                CoinMarketCapEtherService coinMarketCapEtherService = retrofit.create(CoinMarketCapEtherService.class);
                new NetworkManager<CoinMarketEtherApi>().getResponse(coinMarketCapEtherService.getCurrentEthValue(), callback);
                break;
            case POLONIEX:
            default:
                PolionexEtherService polionexEtherService = retrofit.create(PolionexEtherService.class);
                new NetworkManager<PolionexEtherApi>().getResponse(polionexEtherService.getCurrentEthValue(), callback);
                break;
        }
    }

    /**
     * Do a generic call and returns it to the main {@link Thread} through the {@link NetworkCallback}
     */
    private void getResponse(Call<T> call, final NetworkCallback<AbstractEtherApi> callback) {
        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.updateApi(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                //Logging also with crashlytics to have a volume of failure
                Crashlytics.logException(t);

                callback.updateApi(null);
            }
        });
    }

    /**
     * Get the {@link Retrofit} instance
     */
    private static Retrofit getRetrofit(Endpoint endpoint) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(endpoint.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return mRetrofit;
    }
}
