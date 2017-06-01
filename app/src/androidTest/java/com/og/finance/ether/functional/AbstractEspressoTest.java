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
package com.og.finance.ether.functional;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.view.View;

import com.og.finance.ether.activities.AbstractDrawerActivity;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static org.junit.Assert.assertTrue;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class AbstractEspressoTest {

    @Before
    public void setUp() throws Exception {
        //Just deleting the SharedPreferences, to be sure we start fresh
        SharedPreferencesUtilities.deleteKey(getInstrumentation().getTargetContext(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        SharedPreferencesUtilities.deleteKey(getInstrumentation().getTargetContext(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID);
        SharedPreferencesUtilities.deleteKey(getInstrumentation().getTargetContext(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE);
    }

    /**
     * Returns the {@link Context} from the {@link android.app.Instrumentation}
     */
    protected Context getContext() {
        return getInstrumentation().getContext();
    }

    /**
     * Allows to click on a view by its id
     */
    protected void clickOnView(int viewId) {
        onView(withId(viewId)).perform(click());
        getInstrumentation().waitForIdleSync();
    }

    /**
     * Click on the {@link android.support.v7.widget.Toolbar} navigation icon.
     */
    protected void clickOnToolbarNavigationIcon() {
        onView(withContentDescription(AbstractDrawerActivity.Companion.getNAVIGATION_ICON_CONTENT_DESCRIPTION())).perform(click());
        getInstrumentation().waitForIdleSync();
    }

    /**
     * Replaces a text in a view id
     *
     * @param id   The id of the {@link android.widget.EditText}
     * @param text The text we want to enter
     */
    protected void replaceText(int id, String text) {
        onView(withId(id)).perform(ViewActions.replaceText(text), closeSoftKeyboard());
        getInstrumentation().waitForIdleSync();
    }

    /**
     * Wait for the specific time using {@link Thread#sleep(long)}
     *
     * @param milliseconds The time we want to wait for in millis
     */
    protected void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    /**
     * Wait for a condition to be true
     *
     * @param condition The condition we want to be true
     * @parwaitForIdleSyncam time      The time to wait for
     */
    protected void waitForCondition(Condition condition, int time) {
        int timeWaited = 0;
        while (timeWaited < time) {
            if (condition.isSatisfied()) {
                return;
            }
            waitFor(200);
            timeWaited += 200;
        }
    }

    /**
     * Execute the pending bindings
     */
    protected void executePendingBindings(final ViewDataBinding viewDataBinding) {
        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override public void run() {
                viewDataBinding.executePendingBindings();
            }
        });
    }

    /**
     * Allows to retrieve a {@link View} for the {@link #getCurrentActivity()}
     *
     * @param viewId The id we want to retrieve
     */
    protected View getView(int viewId) {
        return getCurrentActivity().findViewById(viewId);
    }

    /**
     * Method searches through the fragments in the {@link android.app.FragmentManager}
     * for the fragment that is currently visible and returns that fragment
     *
     * @return The fragment that is currently visible
     */
    protected Fragment getCurrentFrag() {
        List<Fragment> fragments = getListFrag();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    return fragment;
                }
            }
        }
        return null;
    }

    /**
     * Get the list of {@link android.support.v4.app.Fragment} currently in the backstack of the current {@link android.app.Activity}
     */
    protected List<Fragment> getListFrag() {
        List<Fragment> fragments = new ArrayList<>();
        FragmentManager fragmentManager = getCurrentActivity().getFragmentManager();

        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            String tag = fragmentManager.getBackStackEntryAt(i).getName();
            fragments.add(fragmentManager.findFragmentByTag(tag));
        }

        return fragments;
    }

    /**
     * Returns the current {@link Activity}
     */
    protected Activity getCurrentActivity() {
        getInstrumentation().waitForIdleSync();
        final Activity[] currentActivity = {null};
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    currentActivity[0] = (Activity) resumedActivities.iterator().next();
                }
            }
        });
        return currentActivity[0];
    }
}