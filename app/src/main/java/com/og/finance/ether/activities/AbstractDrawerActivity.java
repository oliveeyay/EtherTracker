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

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.og.finance.ether.R;
import com.og.finance.ether.databinding.AbstractFragmentDrawerActivityBinding;
import com.og.finance.ether.fragments.AbstractFragment;
import com.og.finance.ether.views.NavigationDrawerView;

public abstract class AbstractDrawerActivity extends AbstractFragmentActivity {
    /**
     * Tag for logging this class
     */
    private static final String TAG = "AbstractDrawerActivity";

    /**
     * The action bar {@link LinearLayout} that we add each action bar view into
     */
    private LinearLayout mActionBarLayout;

    /**
     * The {@link MaterialMenuDrawable} used to replace {@link android.support.v7.widget.Toolbar} icon
     */
    private MaterialMenuDrawable mMaterialMenuDrawable;

    /**
     * The layout binding for this fragment
     */
    protected AbstractFragmentDrawerActivityBinding mBinding;

    /**
     * Content description for test purpose
     */
    public static final String NAVIGATION_ICON_CONTENT_DESCRIPTION = "NAVIGATION_ICON_CONTENT_DESCRIPTION";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.abstract_fragment_drawer_activity);
        initNavigationDrawerAndActionBar();
    }

    @Override
    public void onBackPressed() {
        if (!mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            super.onBackPressed();
        } else {
            hideNavigationDrawer();
        }
    }

    /**
     * Init the {@link AbstractFragmentDrawerActivityBinding#navigationDrawer} by inflating and configuring views
     */
    protected void initNavigationDrawerAndActionBar() {
        //Drawer
        mBinding.navigationDrawer.init(this);

        //Toolbar
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.abstract_fragment_activity_drawer_toolbar, null);
        mBinding.contentToolbar.addView(view);
        mActionBarLayout = (LinearLayout) view.findViewById(R.id.action_bar_custom_layout);

        mMaterialMenuDrawable = new MaterialMenuDrawable(this, ContextCompat.getColor(this, android.R.color.white), MaterialMenuDrawable.Stroke.THIN);
        mBinding.contentToolbar.setNavigationIcon(mMaterialMenuDrawable);
        mBinding.contentToolbar.setNavigationContentDescription(NAVIGATION_ICON_CONTENT_DESCRIPTION);
        mMaterialMenuDrawable.setIconState(MaterialMenuDrawable.IconState.BURGER);
    }

    /**
     * Get the status of the {@link android.support.v4.widget.DrawerLayout}
     *
     * @return True if open, false otherwise
     */
    public boolean isDrawerOpen() {
        if (mBinding != null && mBinding.drawerLayout != null) {
            return mBinding.drawerLayout.isDrawerOpen(GravityCompat.START);
        }
        return false;
    }

    /**
     * Show the {@link android.support.v4.widget.DrawerLayout}
     */
    public void showNavigationDrawer() {
        if (mBinding != null && mBinding.drawerLayout != null) {
            mBinding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    /**
     * Hide the {@link android.support.v4.widget.DrawerLayout}
     */
    public void hideNavigationDrawer() {
        if (mBinding != null && mBinding.drawerLayout != null) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Set the {@link android.support.v7.app.ActionBar} layout, just at the right of the
     * {@link android.support.v4.widget.DrawerLayout} toggle button
     *
     * @param layoutId The layout resource id we want to load
     */
    public View initActionBarLayout(int layoutId) {
        if (mActionBarLayout != null) {
            mActionBarLayout.removeAllViews();

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layoutId, null);
            mActionBarLayout.addView(view);

            return view;
        }

        return null;
    }

    /**
     * Used to init the {@link MaterialMenuDrawable} {@link View.OnClickListener}.
     * Calls {@link #onBackPressed()} if arrow icon, handles the menu if burger icon.
     *
     * @param isArrowShown Tells if the current icon is an arrow or a burger menu
     */
    public void initActionBarIconClickListenerAndIcon(final boolean isArrowShown) {
        //Click listener
        mBinding.contentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbstractFragment.hideSoftwareKeyboard(AbstractDrawerActivity.this, null);

                if (isArrowShown) {
                    //Case arrow back icon
                    onBackPressed();
                } else {
                    //Case burger menu icon
                    if (isDrawerOpen()) {
                        hideNavigationDrawer();
                    } else {
                        showNavigationDrawer();
                    }
                }
            }
        });

        //Icon animation
        if (isArrowShown) {
            animateToolbarNavigationIcon(MaterialMenuDrawable.IconState.ARROW);
        } else {
            animateToolbarNavigationIcon(MaterialMenuDrawable.IconState.BURGER);
        }
    }

    /**
     * Animate the {@link MaterialMenuDrawable} to a new {@link com.balysv.materialmenu.MaterialMenuDrawable.IconState}
     *
     * @param iconState the new state we want to set
     */
    private void animateToolbarNavigationIcon(MaterialMenuDrawable.IconState iconState) {
        if (mMaterialMenuDrawable == null && mBinding.contentToolbar.getNavigationIcon() instanceof MaterialMenuDrawable) {
            mMaterialMenuDrawable = (MaterialMenuDrawable) mBinding.contentToolbar.getNavigationIcon();
        }
        if (mMaterialMenuDrawable != null) {
            mMaterialMenuDrawable.animateIconState(iconState);
        }
    }

    public NavigationDrawerView getNavigationDrawer() {
        return mBinding.navigationDrawer;
    }

    public DrawerLayout getDrawerLayout() {
        return mBinding.drawerLayout;
    }

    public MaterialMenuDrawable getMaterialMenuDrawable() {
        return mMaterialMenuDrawable;
    }

    public Toolbar getToolbar() {
        return mBinding.contentToolbar;
    }
}
