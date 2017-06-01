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

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.crashlytics.android.Crashlytics
import com.og.finance.ether.R
import com.og.finance.ether.animations.FragmentAnimation
import com.og.finance.ether.fragments.AbstractFragment
import com.og.finance.ether.fragments.HomeFragment

abstract class AbstractFragmentActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    /**
     * Reference to the currently displaying fragment
     */
    protected var mCurrentFragment: Fragment? = null

    object FRAGS {
        val FRAGMENT_KEY = "launch_fragment"
        val FRAGMENT_NAVIGATION_KEY = "navigation_key"
    }

    override fun onResume() {
        super.onResume()

        mCurrentFragment = currentFrag

        //Listen for backstack changes to get the current AbstractFragment displayed
        fragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        mCurrentFragment = visibleFrag
    }

    override fun onBackPressed() {
        try {
            val fm = fragmentManager
            if (fm != null) {
                val entries = fm.backStackEntryCount
                if (entries <= 1) {
                    finish()
                } else {
                    fm.popBackStackImmediate()
                }
            }
        } catch (e: RuntimeException) {
            Crashlytics.logException(e)
        }

    }

    override fun onPause() {
        super.onPause()
        mCurrentFragment = visibleFrag
        //Remove listener of backstack so we don't listen to other activities
        fragmentManager.removeOnBackStackChangedListener(this)
    }

    protected abstract fun initLayout(savedInstanceState: Bundle?)

    /**
     * Shows the fragment in the content_frame of the main layout. Shows fragment
     * by using [android.support.v4.app.FragmentTransaction.replace]

     * @param frag                The fragment to show
     * *
     * @param addToBackStack      Whether the backstack should hold this new fragment
     * *
     * @param animate             Whether the transaction should be animated
     * *
     * @param transitionAnimation The [com.og.finance.ether.animations.FragmentAnimation] to animate the Fragment transition. Uses [FragmentAnimation.FRAGMENT_TRANSITION_BOTTOM_STAY] by default if null.
     */
    @JvmOverloads fun showFragment(frag: Fragment, addToBackStack: Boolean, animate: Boolean, transitionAnimation: FragmentAnimation? = FragmentAnimation.FRAGMENT_TRANSITION_BOTTOM_STAY) {
        var animation = transitionAnimation
        hideKeyboard()

        val visibleFragment = visibleFrag
        if (visibleFragment != null && mCurrentFragment != null && (visibleFragment as Any).javaClass == (frag as Any).javaClass) {
            return
        }

        val fm = fragmentManager
        val transaction = fm.beginTransaction()

        if (addToBackStack) {
            transaction.addToBackStack(frag.javaClass.name)
        }

        if (animate) {
            if (animation != null) {
                transaction.setCustomAnimations(animation.animationEnter, animation.animationExit, animation.animationPopEnter, animation.animationPopExit)
            } else {
                animation = FragmentAnimation.FRAGMENT_TRANSITION_BOTTOM_STAY
                transaction.setCustomAnimations(animation.animationEnter, animation.animationExit, animation.animationPopEnter, animation.animationPopExit)
            }
            if (visibleFragment != null) {
                transaction.hide(visibleFragment)
            }
            if (!isFragmentInStack(frag.javaClass)) {
                transaction.add(R.id.content_frame_container, frag, frag.javaClass.name)
            }
            if (!frag.isAdded) {
                transaction.show(frag)
            }
        } else {
            if (isFragmentInStack(frag.javaClass)) {
                //Remove it if already in the stack.
                transaction.remove(frag)
            }
            transaction.replace(R.id.content_frame_container, frag, frag.javaClass.name)
        }
        mCurrentFragment = frag
        transaction.commitAllowingStateLoss()
    }

    /**
     * Calls [.showFragment] with
     * animate set to true

     * @param frag           The fragment to show
     * *
     * @param addToBackStack Whether the backstack should hold this new fragment
     */
    fun showFragment(frag: Fragment, addToBackStack: Boolean) {
        showFragment(frag, addToBackStack, true)
    }

    /**
     * Method searches through the fragments in the [android.support.v4.app.FragmentManager]
     * for the fragment that is currently visible and returns that fragment

     * @return The fragment that is currently visible
     */
    //If it has already been set, but is not yet visible
    val currentFrag: Fragment
        get() {
            if (mCurrentFragment != null) {
                return mCurrentFragment as Fragment
            }

            return visibleFrag as Fragment
        }

    protected val visibleFrag: Fragment?
        get() {
            val fragmentManager = fragmentManager
            return (0..fragmentManager.backStackEntryCount - 1)
                    .map { fragmentManager.getBackStackEntryAt(it).name }
                    .map { fragmentManager.findFragmentByTag(it) }
                    .firstOrNull { it is AbstractFragment && it.isVisible() }
        }

    /**
     * Allows to check if a fragment is present in the back stack or not

     * @param fragmentClass The [Class] of the fragment we want to check for
     * *
     * @return True if present in the back stack, false otherwise
     */
    fun isFragmentInStack(fragmentClass: Class<*>): Boolean {
        val fragmentManager = fragmentManager
        return (0..fragmentManager.backStackEntryCount - 1)
                .map { fragmentManager.getBackStackEntryAt(it).name }
                .map { fragmentManager.findFragmentByTag(it) }
                .any { it != null && it.javaClass == fragmentClass }
    }

    /**
     * Hide the keyboard for the specified activity for the specified view
     */
    fun hideKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    /**
     * Clear the fragment back stack by calling [FragmentManager.popBackStackImmediate]

     * @param includeDashboardFragment If you want to clear DashboardFragment or not
     */
    fun clearBackStack(includeDashboardFragment: Boolean) {
        try {
            val fm = fragmentManager
            if (fm != null) {
                val count = fm.backStackEntryCount
                for (i in 0..count - 1) {
                    val notSpecialFrag = currentFrag !is HomeFragment
                    if ((notSpecialFrag || includeDashboardFragment) && fm.backStackEntryCount > 1) {
                        if (visibleFrag != null) {
                            fm.popBackStackImmediate()
                        }
                    }
                }
                mCurrentFragment = null
            }
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }

    }

}
