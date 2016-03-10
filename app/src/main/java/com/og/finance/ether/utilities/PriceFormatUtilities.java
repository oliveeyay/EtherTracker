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

        String text = String.format(Locale.getDefault(), "%.2f", etherApi.getPriceValue()) + "$";
        text += " || Chg: " + String.format(Locale.getDefault(), "%.2f", etherApi.getPriceChange()) + "%";
        float buyingValue = SharedPreferencesUtilities.getFloatForKey(context, SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        if (buyingValue != 0.0f) {
            float percentChange = ((etherApi.getPriceValue() / buyingValue) - 1) * 100;
            text += " || Chg from buying: " + String.format(Locale.getDefault(), "%.2f", percentChange) + "%";
        }

        return text;
    }
}
