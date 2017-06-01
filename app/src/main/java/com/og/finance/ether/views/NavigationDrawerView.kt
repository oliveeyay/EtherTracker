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
package com.og.finance.ether.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.og.finance.ether.R
import com.og.finance.ether.activities.AbstractDrawerActivity
import com.og.finance.ether.databinding.ViewNavigationDrawerBinding
import com.og.finance.ether.fragments.SettingsFragment

/**
 * Created by oliviergoutay on 3/10/15.
 */
class NavigationDrawerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mCurrentActivity: AbstractDrawerActivity? = null

    /**
     * The layout binding for this fragment
     */
    private val mBinding: ViewNavigationDrawerBinding

    init {
        val inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.view_navigation_drawer, this, true) as ViewNavigationDrawerBinding
        mBinding.drawerLayout = this
    }

    /**
     * Init the [OnClickListener] of the menu items and set the current
     * [AbstractDrawerActivity]

     * @param activity the current [AbstractDrawerActivity]
     */
    fun init(activity: AbstractDrawerActivity) {
        this.mCurrentActivity = activity
    }

    fun onClick(v: View) {
        mCurrentActivity?.clearBackStack(false)
        when (v.id) {
            R.id.navigation_drawer_home -> {
            }
            R.id.navigation_drawer_settings -> mCurrentActivity?.showFragment(SettingsFragment(), true, true, null)
        }
        mCurrentActivity?.hideNavigationDrawer()
    }


}
