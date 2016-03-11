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
package com.og.finance.ether.utilities;

import android.content.Context;

import com.og.finance.ether.R;
import com.og.finance.ether.application.EtherApplication;
import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.apis.CoinMarketEtherApi;

import java.util.Locale;

/**
 * Created by olivier.goutay on 3/2/16.
 */
public class PriceFormatUtilities {

    /**
     * Returns the formated string containing the ether value in USD, the ether change from today,
     * and the ether change from the buying price.
     *
     * @param etherApi The latest {@link CoinMarketEtherApi}
     * @return The formated string used in {@link NotificationUtilities}
     */
    public static String getPriceFormatted(AbstractEtherApi etherApi) {
        Context context = EtherApplication.getAppContext();
        if (etherApi == null || etherApi.getPriceValue() == null || etherApi.getPriceChange() == null) {
            return context.getResources().getString(R.string.network_error);
        }

        String text = "$" + formatTwoDecimals(etherApi.getPriceValue());
        text += " || Chg: " + formatTwoDecimals(etherApi.getPriceChange()) + "%";

        String priceFromBuying = getPriceFromBuying(etherApi);
        if (priceFromBuying != null) {
            text += " || Chg from buying: " + priceFromBuying;
        }

        return text;
    }

    /**
     * Returns the string indicating the price change since buying
     *
     * @param etherApi The current {@link AbstractEtherApi}
     */
    public static String getPriceFromBuying(AbstractEtherApi etherApi) {
        Context context = EtherApplication.getAppContext();

        float buyingValue = SharedPreferencesUtilities.getFloatForKey(context, SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        if (buyingValue != 0.0f && etherApi != null && etherApi.getPriceValue() != null) {
            float percentChange = ((etherApi.getPriceValue() / buyingValue) - 1) * 100;
            return formatTwoDecimals(percentChange) + "%";
        }

        return "";
    }

    /**
     * Format a float to two decimals (10.02)
     */
    public static String formatTwoDecimals(Float value) {
        if (value != null) {
            return String.format(Locale.getDefault(), "%.2f", value);
        }

        return "";
    }
}
