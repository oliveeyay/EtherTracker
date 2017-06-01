/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.activities

import android.content.Intent
import android.os.Bundle
import com.og.finance.ether.fragments.AbstractFragment
import com.og.finance.ether.fragments.HomeFragment
import com.og.finance.ether.fragments.SettingsFragment

class MainActivity : AbstractDrawerActivity() {

    /**
     * Static fields for the fragments that this Activity manages
     */
    object FRAGS {
        val FRAGMENT_HOME = 0
        val FRAGMENT_SETTINGS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //Set the current fragment to whatever the intent says
        setIntent(intent)
        clearBackStack(false)
        initLayout(null)
    }

    override fun onResume() {
        super.onResume()

        if (mCurrentFragment == null) {
            mCurrentFragment = getBlankFrag(defaultFrag)
        }
        showFragment(mCurrentFragment!!, true, false)
    }

    override fun onBackPressed() {
        //if Drawer is closed
        if (!isDrawerOpen) {
            val visibleFragment = visibleFrag
            mCurrentFragment = visibleFragment ?: mCurrentFragment
        }
        mCurrentFragment = currentFrag
        super.onBackPressed()
    }

    override fun initLayout(savedInstanceState: Bundle?) {
        val frag = intent.getIntExtra(AbstractFragmentActivity.FRAGS.FRAGMENT_KEY, defaultFrag)

        //Get the fragment
        mCurrentFragment = getBlankFrag(frag)
        //If we passed intent extras, add them
        mCurrentFragment?.arguments = intent.extras
    }

    /**
     * Method for generating a blank fragment

     * @param fragCode The [MainActivity.FRAGS] key for the desired fragment
     * *
     * @return An initialized fragment
     */
    private fun getBlankFrag(fragCode: Int): AbstractFragment {
        var frag: AbstractFragment? = null
        when (fragCode) {
            FRAGS.FRAGMENT_HOME -> frag = HomeFragment()
            FRAGS.FRAGMENT_SETTINGS -> frag = SettingsFragment()
        }

        return frag as AbstractFragment
    }

    /**
     * Returns the default fragment for the [MainActivity]
     */
    val defaultFrag: Int
        get() = FRAGS.FRAGMENT_HOME

}
