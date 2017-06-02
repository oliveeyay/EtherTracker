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
class CoinMarketPriceApi(@SerializedName("usd")
                         var usd: Float?, @SerializedName("btc")
                         var btc: Float?) : Api()
