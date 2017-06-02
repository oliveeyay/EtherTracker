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
package com.og.finance.ether.utilities

import android.content.Context
import com.og.finance.ether.R
import com.og.finance.ether.network.apis.AbstractEtherApi
import com.og.finance.ether.network.apis.CoinMarketEtherApi
import java.util.*

/**
 * Created by olivier.goutay on 3/2/16.
 */
object PriceFormatUtilities {

    /**
     * Returns the formated string containing the ether value in USD, the ether change from today,
     * and the ether change from the buying price.

     * @param etherApi The latest [CoinMarketEtherApi]
     * *
     * @return The formated string used in [NotificationUtilities]
     */
    fun getPriceFormatted(context: Context, etherApi: AbstractEtherApi?): String {
        if (etherApi == null || etherApi.getPriceValue() == null || etherApi.getPriceChange() == null) {
            return context.resources.getString(R.string.network_error)
        }

        var text = "$" + formatTwoDecimals(etherApi.getPriceValue())
        text += " || Chg: " + formatTwoDecimals(etherApi.getPriceChange()) + "%"

        val priceFromBuying = getPriceFromBuying(context, etherApi)
        text += " || Chg from buying: " + priceFromBuying

        return text
    }

    /**
     * Returns the string indicating the price change since buying
     * @param etherApi The current [AbstractEtherApi]
     */
    fun getPriceFromBuying(context: Context, etherApi: AbstractEtherApi?): String {
        val buyingValue = SharedPreferencesUtilities.getFloatForKey(context, SharedPreferencesUtilities.SHARED_BUYING_VALUE)
        if (buyingValue != 0.0f && etherApi != null && etherApi.getPriceValue() != null) {
            val percentChange = (etherApi.getPriceValue()!! / buyingValue - 1) * 100
            return formatTwoDecimals(percentChange) + "%"
        }

        return ""
    }

    /**
     * Format a float to two decimals (10.02)
     */
    fun formatTwoDecimals(value: Float?): String {
        if (value != null) {
            return String.format(Locale.getDefault(), "%.2f", value)
        }

        return ""
    }
}
