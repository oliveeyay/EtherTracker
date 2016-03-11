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
package com.og.finance.ether.views;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.og.finance.ether.R;
import com.og.finance.ether.activities.AbstractDrawerActivity;
import com.og.finance.ether.databinding.ViewNavigationDrawerBinding;
import com.og.finance.ether.fragments.SettingsFragment;

/**
 * Created by oliviergoutay on 3/10/15.
 */
public class NavigationDrawerView extends RelativeLayout {
    /**
     * TAG for logging
     */
    private static final String TAG = "NavigationDrawer";

    private AbstractDrawerActivity mCurrentActivity;

    /**
     * The layout binding for this fragment
     */
    private ViewNavigationDrawerBinding mBinding;

    public NavigationDrawerView(Context context) {
        this(context, null);
    }

    public NavigationDrawerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationDrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.view_navigation_drawer, this, true);
        mBinding.setDrawerLayout(this);
    }

    /**
     * Init the {@link OnClickListener} of the menu items and set the current
     * {@link AbstractDrawerActivity}
     *
     * @param activity the current {@link AbstractDrawerActivity}
     */
    public void init(AbstractDrawerActivity activity) {
        this.mCurrentActivity = activity;
    }

    public void onClick(View v) {
        mCurrentActivity.clearBackStack(false);
        switch (v.getId()) {
            case R.id.navigation_drawer_home:
                break;
            case R.id.navigation_drawer_settings:
                mCurrentActivity.showFragment(new SettingsFragment(), true, true, null);
                break;
        }
        mCurrentActivity.hideNavigationDrawer();
    }


}
