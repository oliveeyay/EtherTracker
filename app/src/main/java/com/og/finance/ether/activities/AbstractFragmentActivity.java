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
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.crashlytics.android.Crashlytics;
import com.og.finance.ether.R;
import com.og.finance.ether.animations.FragmentAnimation;
import com.og.finance.ether.fragments.AbstractFragment;
import com.og.finance.ether.fragments.HomeFragment;

public abstract class AbstractFragmentActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    /**
     * Tag for logging this class
     */
    private static final String TAG = "AbstractFragmentAct";

    /**
     * Reference to the currently displaying fragment
     */
    protected Fragment mCurrentFragment;

    public static class FRAGS {
        public static final String FRAGMENT_KEY = "launch_fragment";
        public static final String FRAGMENT_NAVIGATION_KEY = "navigation_key";
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCurrentFragment = getCurrentFrag();

        //Listen for backstack changes to get the current AbstractFragment displayed
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        mCurrentFragment = getVisibleFrag();
    }

    @Override
    public void onBackPressed() {
        try {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                int entries = fm.getBackStackEntryCount();
                if (entries <= 1) {
                    finish();
                } else {
                    fm.popBackStackImmediate();
                }
            }
        } catch (RuntimeException e) {
            Crashlytics.logException(e);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentFragment = getVisibleFrag();
        //Remove listener of backstack so we don't listen to other activities
        getFragmentManager().removeOnBackStackChangedListener(this);
    }

    protected abstract void initLayout(Bundle savedInstanceState);

    /**
     * Shows the fragment in the content_frame of the main layout. Shows fragment
     * by using {@link android.support.v4.app.FragmentTransaction#replace(int, android.support.v4.app.Fragment)}
     *
     * @param frag                The fragment to show
     * @param addToBackStack      Whether the backstack should hold this new fragment
     * @param animate             Whether the transaction should be animated
     * @param transitionAnimation The {@link com.og.finance.ether.animations.FragmentAnimation} to animate the Fragment transition. Uses {@link FragmentAnimation#FRAGMENT_TRANSITION_BOTTOM_STAY} by default if null.
     */
    public void showFragment(Fragment frag, boolean addToBackStack, boolean animate, FragmentAnimation transitionAnimation) {
        hideKeyboard();

        Fragment visibleFragment = getVisibleFrag();
        if (visibleFragment != null && mCurrentFragment != null && ((Object) visibleFragment).getClass() == ((Object) frag).getClass()) {
            return;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(frag.getClass().getName());
        }

        if (animate) {
            if (transitionAnimation != null) {
                transaction.setCustomAnimations(transitionAnimation.getAnimationEnter(), transitionAnimation.getAnimationExit(), transitionAnimation.getAnimationPopEnter(), transitionAnimation.getAnimationPopExit());
            } else {
                transitionAnimation = FragmentAnimation.FRAGMENT_TRANSITION_BOTTOM_STAY;
                transaction.setCustomAnimations(transitionAnimation.getAnimationEnter(), transitionAnimation.getAnimationExit(), transitionAnimation.getAnimationPopEnter(), transitionAnimation.getAnimationPopExit());
            }
            if (visibleFragment != null) {
                transaction.hide(visibleFragment);
            }
            if (!isFragmentInStack(frag.getClass())) {
                transaction.add(R.id.content_frame_container, frag, frag.getClass().getName());
            }
            if (!frag.isAdded()) {
                transaction.show(frag);
            }
        } else {
            if (isFragmentInStack(frag.getClass())) {
                //Remove it if already in the stack.
                transaction.remove(frag);
            }
            transaction.replace(R.id.content_frame_container, frag, frag.getClass().getName());
        }
        mCurrentFragment = frag;
        transaction.commitAllowingStateLoss();
    }

    /**
     * Shows the fragment in the content_frame of the main layout. Shows fragment
     * by using {@link android.support.v4.app.FragmentTransaction#replace(int, android.support.v4.app.Fragment)}.
     * Uses {@link FragmentAnimation#FRAGMENT_TRANSITION_BOTTOM_STAY} as the default custom animation for Fragment transition.
     *
     * @param frag           The fragment to show
     * @param addToBackStack Whether the backstack should hold this new fragment
     * @param animate        Whether the transaction should be animated
     */
    public void showFragment(Fragment frag, boolean addToBackStack, boolean animate) {
        showFragment(frag, addToBackStack, animate, FragmentAnimation.FRAGMENT_TRANSITION_BOTTOM_STAY);
    }

    /**
     * Calls {@link #showFragment(Fragment, boolean, boolean)} with
     * animate set to true
     *
     * @param frag           The fragment to show
     * @param addToBackStack Whether the backstack should hold this new fragment
     */
    protected void showFragment(Fragment frag, boolean addToBackStack) {
        showFragment(frag, addToBackStack, true);
    }

    /**
     * Method searches through the fragments in the {@link android.support.v4.app.FragmentManager}
     * for the fragment that is currently visible and returns that fragment
     *
     * @return The fragment that is currently visible
     */
    public Fragment getCurrentFrag() {
        //If it has already been set, but is not yet visible
        if (mCurrentFragment != null) {
            return mCurrentFragment;
        }

        return getVisibleFrag();
    }

    protected Fragment getVisibleFrag() {
        FragmentManager fragmentManager = getFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            String tag = fragmentManager.getBackStackEntryAt(i).getName();
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment instanceof AbstractFragment && fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }

    /**
     * Allows to check if a fragment is present in the back stack or not
     *
     * @param fragmentClass The {@link Class} of the fragment we want to check for
     * @return True if present in the back stack, false otherwise
     */
    public boolean isFragmentInStack(Class fragmentClass) {
        FragmentManager fragmentManager = getFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            String tag = fragmentManager.getBackStackEntryAt(i).getName();
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null && fragment.getClass() == fragmentClass) {
                return true;
            }
        }
        return false;
    }

    /**
     * Hide the keyboard for the specified activity for the specified view
     */
    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Clear the fragment back stack by calling {@link FragmentManager#popBackStackImmediate()}
     *
     * @param includeDashboardFragment If you want to clear DashboardFragment or not
     */
    public void clearBackStack(boolean includeDashboardFragment) {
        try {
            FragmentManager fm = getFragmentManager();
            if (fm != null) {
                int count = fm.getBackStackEntryCount();
                for (int i = 0; i < count; i++) {
                    boolean notSpecialFrag = (!(getCurrentFrag() instanceof HomeFragment));
                    if ((notSpecialFrag || includeDashboardFragment) && fm.getBackStackEntryCount() > 1) {
                        if (getVisibleFrag() != null) {
                            fm.popBackStackImmediate();
                        }
                    }
                }
                mCurrentFragment = null;
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

}
