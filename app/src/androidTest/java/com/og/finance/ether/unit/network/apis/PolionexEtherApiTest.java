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
package com.og.finance.ether.unit.network.apis;

import com.og.finance.ether.network.apis.PolionexEtherApi;
import com.og.finance.ether.network.apis.PolionexPriceApi;
import com.og.finance.ether.unit.AbstractUnitTest;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class PolionexEtherApiTest extends AbstractUnitTest {

    /**
     * Test for {@link PolionexEtherApi#getPriceValue()}
     */
    public void testGetPriceValue() {
        //Test null
        PolionexEtherApi polionexEtherApi = new PolionexEtherApi(null);
        assertNull(polionexEtherApi.getPriceValue());

        //Test null 2
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(null, null));
        assertNull(polionexEtherApi.getPriceValue());

        //Test null 3
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(null, 10.0f));
        assertNull(polionexEtherApi.getPriceValue());

        //Test value set
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(11.0f, 10.0f));
        assertEquals(11.0f, polionexEtherApi.getPriceValue());
    }

    /**
     * Test for {@link PolionexEtherApi#getPriceChange()}
     */
    public void testGetPriceChange() {
        //Test null
        PolionexEtherApi polionexEtherApi = new PolionexEtherApi(null);
        assertNull(polionexEtherApi.getPriceChange());

        //Test null 2
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(null, null));
        assertNull(polionexEtherApi.getPriceChange());

        //Test null 3
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(10.0f, null));
        assertNull(polionexEtherApi.getPriceChange());

        //Test value set
        polionexEtherApi = new PolionexEtherApi(new PolionexPriceApi(10.0f, 0.11f));
        assertEquals(11.0f, polionexEtherApi.getPriceChange());
    }

}
