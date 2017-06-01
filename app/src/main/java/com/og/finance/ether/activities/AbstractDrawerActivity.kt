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

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.balysv.materialmenu.MaterialMenuDrawable
import com.og.finance.ether.R
import com.og.finance.ether.databinding.AbstractFragmentDrawerActivityBinding
import com.og.finance.ether.fragments.AbstractFragment
import com.og.finance.ether.views.NavigationDrawerView

abstract class AbstractDrawerActivity : AbstractFragmentActivity() {

    /**
     * The action bar [LinearLayout] that we add each action bar view into
     */
    private var mActionBarLayout: LinearLayout? = null

    /**
     * The [MaterialMenuDrawable] used to replace [android.support.v7.widget.Toolbar] icon
     */
    var materialMenuDrawable: MaterialMenuDrawable? = null
        private set

    /**
     * The layout binding for this fragment
     */
    protected var mBinding: AbstractFragmentDrawerActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<AbstractFragmentDrawerActivityBinding>(this, R.layout.abstract_fragment_drawer_activity)
        initNavigationDrawerAndActionBar()
    }

    override fun onBackPressed() {
        if (mBinding?.drawerLayout?.isDrawerOpen(GravityCompat.START) ?: false) {
            hideNavigationDrawer()
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Init the [AbstractFragmentDrawerActivityBinding.navigationDrawer] by inflating and configuring views
     */
    protected fun initNavigationDrawerAndActionBar() {
        //Drawer
        mBinding?.navigationDrawer?.init(this)

        //Toolbar
        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.abstract_fragment_activity_drawer_toolbar, null)
        mBinding?.contentToolbar?.addView(view)
        mActionBarLayout = view.findViewById(R.id.action_bar_custom_layout) as LinearLayout

        materialMenuDrawable = MaterialMenuDrawable(this, ContextCompat.getColor(this, android.R.color.white), MaterialMenuDrawable.Stroke.THIN)
        mBinding?.contentToolbar?.navigationIcon = materialMenuDrawable
        mBinding?.contentToolbar?.navigationContentDescription = NAVIGATION_ICON_CONTENT_DESCRIPTION
        materialMenuDrawable?.iconState = MaterialMenuDrawable.IconState.BURGER
    }

    /**
     * Get the status of the [android.support.v4.widget.DrawerLayout]

     * @return True if open, false otherwise
     */
    val isDrawerOpen: Boolean
        get() {
            if (mBinding?.drawerLayout != null) {
                return mBinding?.drawerLayout?.isDrawerOpen(GravityCompat.START) ?: false
            }
            return false
        }

    /**
     * Show the [android.support.v4.widget.DrawerLayout]
     */
    fun showNavigationDrawer() {
        if (mBinding?.drawerLayout != null) {
            mBinding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    /**
     * Hide the [android.support.v4.widget.DrawerLayout]
     */
    fun hideNavigationDrawer() {
        if (mBinding?.drawerLayout != null) {
            mBinding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }
    }

    /**
     * Set the [android.support.v7.app.ActionBar] layout, just at the right of the
     * [android.support.v4.widget.DrawerLayout] toggle button

     * @param layoutId The layout resource id we want to load
     */
    fun initActionBarLayout(layoutId: Int): View? {
        if (mActionBarLayout != null) {
            mActionBarLayout?.removeAllViews()

            val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(layoutId, null)
            mActionBarLayout?.addView(view)

            return view
        }

        return null
    }

    /**
     * Used to init the [MaterialMenuDrawable] [View.OnClickListener].
     * Calls [.onBackPressed] if arrow icon, handles the menu if burger icon.

     * @param isArrowShown Tells if the current icon is an arrow or a burger menu
     */
    fun initActionBarIconClickListenerAndIcon(isArrowShown: Boolean) {
        //Click listener
        mBinding?.contentToolbar?.setNavigationOnClickListener {
            AbstractFragment.hideSoftwareKeyboard(this@AbstractDrawerActivity, null)

            if (isArrowShown) {
                //Case arrow back icon
                onBackPressed()
            } else {
                //Case burger menu icon
                if (isDrawerOpen) {
                    hideNavigationDrawer()
                } else {
                    showNavigationDrawer()
                }
            }
        }

        //Icon animation
        if (isArrowShown) {
            animateToolbarNavigationIcon(MaterialMenuDrawable.IconState.ARROW)
        } else {
            animateToolbarNavigationIcon(MaterialMenuDrawable.IconState.BURGER)
        }
    }

    /**
     * Animate the [MaterialMenuDrawable] to a new [com.balysv.materialmenu.MaterialMenuDrawable.IconState]

     * @param iconState the new state we want to set
     */
    private fun animateToolbarNavigationIcon(iconState: MaterialMenuDrawable.IconState) {
        if (materialMenuDrawable == null && mBinding?.contentToolbar?.navigationIcon is MaterialMenuDrawable) {
            materialMenuDrawable = mBinding?.contentToolbar?.navigationIcon as MaterialMenuDrawable?
        }
        if (materialMenuDrawable != null) {
            materialMenuDrawable?.animateIconState(iconState)
        }
    }

    val navigationDrawer: NavigationDrawerView?
        get() = mBinding?.navigationDrawer

    val drawerLayout: DrawerLayout?
        get() = mBinding?.drawerLayout

    val toolbar: Toolbar?
        get() = mBinding?.contentToolbar

    companion object {

        /**
         * Content description for test purpose
         */
        val NAVIGATION_ICON_CONTENT_DESCRIPTION = "NAVIGATION_ICON_CONTENT_DESCRIPTION"
    }
}
