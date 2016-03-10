package com.og.finance.ether.unit;

import android.test.AndroidTestCase;

import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/9/16.
 */
public class AbstractUnitTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //Just deleting the SharedPreferences, to be sure we start fresh
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID);
        SharedPreferencesUtilities.deleteKey(getContext(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE);
    }

}
