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
package com.og.finance.ether.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;

import com.og.finance.ether.fragments.AbstractFragment;
import com.og.finance.ether.fragments.HomeFragment;
import com.og.finance.ether.fragments.SettingsFragment;

public class MainActivity extends AbstractDrawerActivity {

    /**
     * Static fields for the fragments that this Activity manages
     */
    public static class FRAGS {
        public static final int FRAGMENT_HOME = 0;
        public static final int FRAGMENT_SETTINGS = 1;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Set the current fragment to whatever the intent says
        setIntent(intent);

        clearBackStack(false);
        initLayout(null);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCurrentFragment == null) {
            mCurrentFragment = getBlankFrag(getDefaultFrag());
        }
        showFragment(mCurrentFragment, true, false);
    }

    @Override
    public void onBackPressed() {
        //if Drawer is closed
        if (!mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            FragmentManager fm = getFragmentManager();
            Fragment visibleFragment = getVisibleFrag();
            mCurrentFragment = visibleFragment == null ? mCurrentFragment : visibleFragment;
        }
        mCurrentFragment = getCurrentFrag();
        super.onBackPressed();
    }

    @Override
    protected void initLayout(Bundle savedInstanceState) {
        int frag = getIntent().getIntExtra(AbstractFragmentActivity.FRAGS.FRAGMENT_KEY, getDefaultFrag());

        //Get the fragment
        mCurrentFragment = getBlankFrag(frag);
        //If we passed intent extras, add them
        mCurrentFragment.setArguments(getIntent().getExtras());
    }

    /**
     * Method for generating a blank fragment
     *
     * @param fragCode The {@link MainActivity.FRAGS} key for the desired fragment
     * @return An initialized fragment
     */
    private AbstractFragment getBlankFrag(int fragCode) {
        AbstractFragment frag = null;
        switch (fragCode) {
            case FRAGS.FRAGMENT_HOME:
                frag = new HomeFragment();
                break;
            case FRAGS.FRAGMENT_SETTINGS:
                frag = new SettingsFragment();
                break;
        }

        return frag;
    }

    /**
     * Returns the default fragment for the {@link MainActivity}
     */
    public int getDefaultFrag() {
        return FRAGS.FRAGMENT_HOME;
    }

}
