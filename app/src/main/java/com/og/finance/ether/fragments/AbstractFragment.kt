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
package com.og.finance.ether.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import com.balysv.materialmenu.MaterialMenuDrawable
import com.og.finance.ether.R
import com.og.finance.ether.activities.AbstractDrawerActivity
import com.og.finance.ether.activities.AbstractFragmentActivity

abstract class AbstractFragment : Fragment() {

    /**
     * A lock for controlling UI changes
     */
    protected val mViewSyncLock = Any()

    /**
     * Boolean controlling the interaction with the UI views synchronized by [.mViewSyncLock]
     */
    private var mViewVisible: Boolean = false

    /**
     * Boolean indicating the animation state of the view.
     */
    /**
     * Getter method for the [.mIsAnimating] boolean. Use [.onTransactionAnimationEnded]
     * and [.onTransactionAnimationStarted]

     * @return True if it is animating
     */
    /**
     * Setter method for [.mIsAnimating] that is synchronized by the
     * [.onTransactionAnimationEnded] and [.onTransactionAnimationStarted].

     * @param animating True if view is animating, false otherwise
     */
    protected var isAnimating: Boolean = false
        private set(animating) = synchronized(mViewSyncLock) {
            field = animating
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewVisible = true

        initLayout(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        isViewVisible = true
        onStartUp()
    }

    override fun onResume() {
        super.onResume()
        isViewVisible = true
        initActionBar()
    }

    override fun onPause() {
        super.onPause()
        isViewVisible = false
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initActionBar()
        }
        isAnimating = !hidden
        isViewVisible = !hidden
        onVisibilityChanged(!hidden)
    }

    /**
     * Fragments have a different view lifecycle than activities. When injecting a fragment in onCreateView,
     * set the views to null in onDestroyView. Butter Knife has a reset method to do this automatically.
     */
    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * Init the ActionBar, if needed
     */
    private fun initActionBar() {
        if (activity is AbstractDrawerActivity) {
            val view = (activity as AbstractDrawerActivity).initActionBarLayout(actionBarLayoutId)
            if (view != null) {
                initActionBarObjects(view)
            }

            //Set the click listener
            (activity as AbstractDrawerActivity).initActionBarIconClickListenerAndIcon(isActionBarIconArrow)
            setToolbarColor(R.color.blue_grey_900)
        }
    }

    /**
     * Sets the [android.support.v7.widget.Toolbar] color for the fragment

     * @param resColorId
     */
    protected fun setToolbarColor(resColorId: Int) {
        if (activity is AbstractDrawerActivity) {
            (activity as AbstractDrawerActivity).toolbar?.setBackgroundColor(ContextCompat.getColor(activity, resColorId))
        }
    }

    protected abstract fun initActionBarObjects(actionBarView: View)

    abstract val actionBarLayoutId: Int

    /**
     * Initiate the layout, automatically called in [.onViewCreated].
     * Don't use it to init objects that are in the [android.support.v7.widget.Toolbar].
     */
    protected abstract fun initLayout(savedInstanceState: Bundle?)

    /**
     * Notifies when the fragment starts in [.onStart]
     */
    protected abstract fun onStartUp()

    /**
     * Notifies when the fragment's visibility changes in [.onHiddenChanged]

     * @param isVisible True if fragment is visible
     */
    protected abstract fun onVisibilityChanged(isVisible: Boolean)

    /**
     * Returns if the current [AbstractFragment] is visible or not by checking
     * the value of [.mViewVisible] && [.isAdded]

     * @return True if it is visible and added to the activity
     */
    /**
     * Setter method for [.mViewVisible] that is synchronized by the
     * [.mViewSyncLock]. Controlled in [.onViewCreated]
     * and [.onPause]

     * @param visible True if view can be modified, false otherwise
     */
    protected var isViewVisible: Boolean
        get() = mViewVisible && isAdded
        private set(visible) = synchronized(mViewSyncLock) {
            mViewVisible = visible
        }

    /**
     * Method called when the end of the transaction animation is reached. The animation is set up on
     * [AbstractFragmentActivity.showFragment]
     * Override it if you want to use it.
     */
    protected fun onTransactionAnimationEnded() {
        isAnimating = false
    }

    /**
     * Method called when the end of the transaction animation is reached. The animation is set up on
     * [AbstractFragmentActivity.showFragment]
     * Override it if you want to use it.
     */
    protected fun onTransactionAnimationStarted() {
        isAnimating = true
    }

    /**
     * Override the [android.support.v4.app.Fragment.onCreateAnimation] to
     * add a listener to the fragment transaction animation. Call [.onTransactionAnimationEnded] at the end
     * of it.
     */
    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        //Check if the superclass already created the animation
        var anim: Animator? = super.onCreateAnimator(transit, enter, nextAnim)

        //If not, and an animation is defined, load it now
        if (anim == null && nextAnim != 0) {
            anim = AnimatorInflater.loadAnimator(activity, nextAnim)
        }

        //If there is an animation for this fragment, add a listener.
        if (anim != null) {
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    onTransactionAnimationStarted()
                }

                override fun onAnimationEnd(animator: Animator) {
                    onTransactionAnimationEnded()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
        }

        return anim
    }

    /**
     * Used by [.onResume] and [.onStop] to know if the [MaterialMenuDrawable]
     * should be animated in a new state or not.

     * @return false by default. Need to be overridden to change.
     */
    //Override this method if you want to animate the MaterialMenuDrawable
    open val isActionBarIconArrow: Boolean
        get() = false

    companion object {
        /**
         * Tag for logging this class
         */
        private val TAG = "AccountManager"

        /**
         * Hide the software keyboard (static method)

         * @param activity the current activity to have access to the input service
         * *
         * @param editText the current focused [EditText]
         */
        fun hideSoftwareKeyboard(activity: Activity?, editText: EditText?) {
            if (activity == null) {
                return
            }

            if (editText != null) {
                val imm = activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            } else {
                // Check if no view has focus:
                val view = activity.currentFocus
                if (view != null) {
                    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
    }
}
