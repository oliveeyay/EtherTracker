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

import com.og.finance.ether.network.apis.EtherApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by olivier.goutay on 2/29/16.
 * http://coinmarketcap-nexuist.rhcloud.com/api/eth
 */
public interface CoinMarketCapService {

    @GET("api/eth")
    Call<EtherApi> getCurrentEthValue();
}
