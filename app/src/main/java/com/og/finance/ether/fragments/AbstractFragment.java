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
package com.og.finance.ether.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.og.finance.ether.R;
import com.og.finance.ether.activities.AbstractDrawerActivity;
import com.og.finance.ether.activities.AbstractFragmentActivity;

public abstract class AbstractFragment extends Fragment {
    /**
     * Tag for logging this class
     */
    private static final String TAG = "AccountManager";

    /**
     * A lock for controlling UI changes
     */
    final protected Object mViewSyncLock = new Object();

    /**
     * Boolean controlling the interaction with the UI views synchronized by {@link #mViewSyncLock}
     */
    private boolean mViewVisible;

    /**
     * Boolean indicating the animation state of the view.
     */
    private boolean mIsAnimating;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViewVisible(true);

        initLayout(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        setViewVisible(true);
        onStartUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewVisible(true);
        initActionBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        setViewVisible(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initActionBar();
        }
        setIsAnimating(!hidden);
        setViewVisible(!hidden);
        onVisibilityChanged(!hidden);
    }

    /**
     * Fragments have a different view lifecycle than activities. When injecting a fragment in onCreateView,
     * set the views to null in onDestroyView. Butter Knife has a reset method to do this automatically.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Allows to launch {@link #onVisibilityChanged(boolean)} using {@link View#post(Runnable)}
     * to be able to launch actions at view visible.
     */
    protected void launchOnVisibilityChangedOnPostView() {
        View view = getView();
        if (view != null) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    if (isViewVisible() && isAdded()) {
                        onVisibilityChanged(true);
                    }
                }
            });
        }
    }

    /**
     * Init the ActionBar, if needed
     */
    private void initActionBar() {
        if (getActivity() instanceof AbstractDrawerActivity) {
            View view = ((AbstractDrawerActivity) getActivity()).initActionBarLayout(getActionBarLayoutId());
            if (view != null) {
                initActionBarObjects(view);
            }

            //Set the click listener
            ((AbstractDrawerActivity) getActivity()).initActionBarIconClickListenerAndIcon(isActionBarIconArrow());
            setToolbarColor(R.color.blue_grey_900);
        }
    }

    /**
     * Sets the {@link android.support.v7.widget.Toolbar} color for the fragment
     *
     * @param resColorId
     */
    protected void setToolbarColor(int resColorId) {
        if (getActivity() instanceof AbstractDrawerActivity) {
            ((AbstractDrawerActivity) getActivity()).getToolbar().setBackgroundColor(ContextCompat.getColor(getActivity(), resColorId));
        }
    }

    protected abstract void initActionBarObjects(View actionBarView);

    public abstract int getActionBarLayoutId();

    /**
     * Initiate the layout, automatically called in {@link #onViewCreated(View, Bundle)}.
     * Don't use it to init objects that are in the {@link android.support.v7.widget.Toolbar}.
     */
    protected abstract void initLayout(Bundle savedInstanceState);

    /**
     * Notifies when the fragment starts in {@link #onStart()}
     */
    protected abstract void onStartUp();

    /**
     * Notifies when the fragment's visibility changes in {@link #onHiddenChanged(boolean)}
     *
     * @param isVisible True if fragment is visible
     */
    protected abstract void onVisibilityChanged(boolean isVisible);

    /**
     * Setter method for {@link #mViewVisible} that is synchronized by the
     * {@link #mViewSyncLock}. Controlled in {@link #onViewCreated(View, Bundle)}
     * and {@link #onPause()}
     *
     * @param visible True if view can be modified, false otherwise
     */
    private void setViewVisible(boolean visible) {
        synchronized (mViewSyncLock) {
            mViewVisible = visible;
        }
    }

    /**
     * Returns if the current {@link AbstractFragment} is visible or not by checking
     * the value of {@link #mViewVisible} && {@link #isAdded()}
     *
     * @return True if it is visible and added to the activity
     */
    protected boolean isViewVisible() {
        return mViewVisible && isAdded();
    }

    /**
     * Setter method for {@link #mIsAnimating} that is synchronized by the
     * {@link #onTransactionAnimationEnded()} and {@link #onTransactionAnimationStarted()}.
     *
     * @param animating True if view is animating, false otherwise
     */
    private void setIsAnimating(boolean animating) {
        synchronized (mViewSyncLock) {
            mIsAnimating = animating;
        }
    }

    /**
     * Getter method for the {@link #mIsAnimating} boolean. Use {@link #onTransactionAnimationEnded()}
     * and {@link #onTransactionAnimationStarted()}
     *
     * @return True if it is animating
     */
    protected boolean isAnimating() {
        return mIsAnimating;
    }

    /**
     * Hide the software keyboard (static method)
     *
     * @param activity the current activity to have access to the input service
     * @param editText the current focused {@link EditText}
     */
    public static void hideSoftwareKeyboard(Activity activity, EditText editText) {
        if (activity == null) {
            return;
        }

        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } else {
            // Check if no view has focus:
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * Method called when the end of the transaction animation is reached. The animation is set up on
     * {@link AbstractFragmentActivity#showFragment(Fragment, boolean)}
     * Override it if you want to use it.
     */
    protected void onTransactionAnimationEnded() {
        setIsAnimating(false);
    }

    /**
     * Method called when the end of the transaction animation is reached. The animation is set up on
     * {@link AbstractFragmentActivity#showFragment(Fragment, boolean)}
     * Override it if you want to use it.
     */
    protected void onTransactionAnimationStarted() {
        setIsAnimating(true);
    }

    /**
     * Override the {@link android.support.v4.app.Fragment#onCreateAnimation(int, boolean, int)} to
     * add a listener to the fragment transaction animation. Call {@link #onTransactionAnimationEnded()} at the end
     * of it.
     */
    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        //Check if the superclass already created the animation
        Animator anim = super.onCreateAnimator(transit, enter, nextAnim);

        //If not, and an animation is defined, load it now
        if (anim == null && nextAnim != 0) {
            anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
        }

        //If there is an animation for this fragment, add a listener.
        if (anim != null) {
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    onTransactionAnimationStarted();
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    onTransactionAnimationEnded();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        return anim;
    }

    /**
     * Used by {@link #onResume()} and {@link #onStop()} to know if the {@link MaterialMenuDrawable}
     * should be animated in a new state or not.
     *
     * @return false by default. Need to be overridden to change.
     */
    public boolean isActionBarIconArrow() {
        //Override this method if you want to animate the MaterialMenuDrawable
        return false;
    }
}
