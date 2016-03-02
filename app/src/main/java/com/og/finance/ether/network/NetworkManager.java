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

import com.og.finance.ether.network.apis.Api;
import com.og.finance.ether.network.apis.EtherApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by olivier.goutay on 2/29/16.
 * Uses {@link Retrofit} to call the ether api
 */
public class NetworkManager<T extends Api> {

    /**
     * The base URL of coinmarketcap
     */
    static final String URL = "http://coinmarketcap-nexuist.rhcloud.com";

    /**
     * The instance of {@link Retrofit}
     */
    private static Retrofit mRetrofit;

    /**
     * Returns the latest {@link EtherApi} through the {@link Retrofit} interface {@link NetworkCallback}
     *
     * @param callback
     */
    public static void getCurrentEthValue(NetworkCallback<EtherApi> callback) {
        Retrofit retrofit = getRetrofit();

        CoinMarketCapService service = retrofit.create(CoinMarketCapService.class);
        new NetworkManager<EtherApi>().getResponse(service.getCurrentEthValue(), callback);
    }

    /**
     * Do a generic call and returns it to the main {@link Thread} through the {@link NetworkCallback}
     */
    private void getResponse(Call<T> call, final NetworkCallback<T> callback) {
        call.enqueue(new Callback<T>() {

            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                callback.updateApi(response.body());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                //TODO error handling
            }
        });
    }

    /**
     * Get the {@link Retrofit} instance
     */
    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
