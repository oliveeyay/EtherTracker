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
