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

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.og.finance.ether.network.apis.KrakenEtherApi;
import com.og.finance.ether.network.apis.KrakenPriceApi;
import com.og.finance.ether.network.apis.KrakenResultApi;
import com.og.finance.ether.unit.AbstractUnitTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by olivier.goutay on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class KrakenEtherApiTest extends AbstractUnitTest {

    /**
     * Test for {@link KrakenEtherApi#getPriceValue()}
     */
    @Test
    public void testGetPriceValue() {
        //Test null
        KrakenEtherApi krakenEtherApi = new KrakenEtherApi(null);
        assertNull(krakenEtherApi.getPriceValue());

        //Test null 2
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(null));
        assertNull(krakenEtherApi.getPriceValue());

        //Test null 3
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, null)));
        assertNull(krakenEtherApi.getPriceValue());

        //Test list empty
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(new ArrayList<Float>(), null)));
        assertNull(krakenEtherApi.getPriceValue());

        //Test value set
        ArrayList<Float> arrayList = new ArrayList<>();
        arrayList.add(10.0f);
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertEquals(10.0f, krakenEtherApi.getPriceValue());
    }

    /**
     * Test for {@link KrakenEtherApi#getPriceChange()}
     */
    @Test
    public void testGetPriceChange() {
        //Test null
        KrakenEtherApi krakenEtherApi = new KrakenEtherApi(null);
        assertNull(krakenEtherApi.getPriceChange());

        //Test null 2
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(null));
        assertNull(krakenEtherApi.getPriceChange());

        //Test null 3
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, null)));
        assertNull(krakenEtherApi.getPriceChange());

        //Test list empty
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(new ArrayList<Float>(), null)));
        assertNull(krakenEtherApi.getPriceChange());

        //Test list set but price empty
        ArrayList<Float> arrayList = new ArrayList<>();
        arrayList.add(11.0f);
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, null)));
        assertNull(krakenEtherApi.getPriceChange());

        //Test price but no list
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(null, 11.0f)));
        assertNull(krakenEtherApi.getPriceChange());

        //Test price but list empty
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(new ArrayList<Float>(), 11.0f)));
        assertNull(krakenEtherApi.getPriceChange());

        //Test set
        krakenEtherApi = new KrakenEtherApi(new KrakenResultApi(new KrakenPriceApi(arrayList, 10.0f)));
        assertTrue(10.0f - krakenEtherApi.getPriceChange() < 0.01f);
    }

}
