package com.og.finance.ether.network.apis;

import com.google.gson.annotations.SerializedName;

/**
 * Created by olivier.goutay on 3/7/16.
 */
public class KrakenResultApi extends Api {

    @SerializedName("XETHZUSD")
    private KrakenPriceApi mPriceApi;

    public KrakenResultApi(KrakenPriceApi priceApi) {
        this.mPriceApi = priceApi;
    }

    public KrakenPriceApi getPrice() {
        return mPriceApi;
    }

    public void setPrice(KrakenPriceApi priceApi) {
        this.mPriceApi = priceApi;
    }
}
