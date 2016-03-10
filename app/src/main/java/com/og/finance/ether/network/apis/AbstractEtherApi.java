package com.og.finance.ether.network.apis;

/**
 * Created by olivier.goutay on 3/7/16.
 */
public abstract class AbstractEtherApi extends Api {

    /**
     * Get the price value, either from {@link CoinMarketEtherApi} or {@link KrakenEtherApi}
     */
    public abstract Float getPriceValue();

    /**
     * Get the price change, either from {@link CoinMarketEtherApi} or {@link KrakenEtherApi}
     */
    public abstract Float getPriceChange();

}
