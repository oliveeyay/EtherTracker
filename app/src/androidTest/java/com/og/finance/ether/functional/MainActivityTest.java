package com.og.finance.ether.functional;

import com.og.finance.ether.R;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/9/16.
 * Test of {@link com.og.finance.ether.activities.MainActivity}
 */
public class MainActivityTest extends AbstractFunctionalTest {

    /**
     * Test the {@link com.og.finance.ether.databinding.ActivityMainBinding#activityMainRadioKraken} and others
     */
    public void testRadioButtonChangeEndpoint() {
        //Default
        assertEquals(1, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on coinmarketcap
        clickOnView(R.id.activity_main_radio_coinmarketcap);
        mSolo.sleep(500);
        assertEquals(2, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on Kraken
        clickOnView(R.id.activity_main_radio_kraken);
        mSolo.sleep(500);
        assertEquals(3, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));

        //Click on Polionex again
        clickOnView(R.id.activity_main_radio_polionex);
        mSolo.sleep(500);
        assertEquals(1, SharedPreferencesUtilities.getIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID));
    }
}
