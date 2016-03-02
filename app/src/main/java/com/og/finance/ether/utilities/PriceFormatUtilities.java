package com.og.finance.ether.utilities;

import android.content.Context;

import com.og.finance.ether.network.apis.EtherApi;

import java.util.Locale;

/**
 * Created by olivier.goutay on 3/2/16.
 */
public class PriceFormatUtilities {

    /**
     * Returns the formated string containing the ether value in USD, the ether change from today,
     * and the ether change from the buying price.
     *
     * @param context  The current context of the app
     * @param etherApi The latest {@link EtherApi}
     * @return The formated string used in {@link NotificationUtilities}
     */
    public static String getPriceFormated(Context context, EtherApi etherApi) {
        String text = String.format(Locale.getDefault(), "%.2f", etherApi.getPrice().getUsd()) + "$";
        text += " || Chg: " + String.format(Locale.getDefault(), "%.2f", etherApi.getChange()) + "%";
        float buyingValue = SharedPreferencesUtilities.getFloatForKey(context, SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        if (buyingValue != 0.0f) {
            float percentChange = ((etherApi.getPrice().getUsd() / buyingValue) - 1) * 100;
            text += " || Chg from buying: " + String.format(Locale.getDefault(), "%.2f", percentChange) + "%";
        }

        return text;
    }
}
