/**
 * Copyright 2013 Olivier Goutay (olivierg13)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.og.finance.ether.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.og.finance.ether.R;
import com.og.finance.ether.databinding.FragmentHomeBinding;
import com.og.finance.ether.network.NetworkCallback;
import com.og.finance.ether.network.NetworkManager;
import com.og.finance.ether.network.apis.AbstractEtherApi;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.receivers.AutoUpdateReceiver;

/**
 * Created by olivier.goutay on 3/11/16.
 */
public class HomeFragment extends AbstractFragment implements NetworkCallback<AbstractEtherApi> {

    /**
     * The binding of this view
     */
    private FragmentHomeBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mBinding.setHomeFragment(this);
        mBinding.setEndpoint(Endpoint.getCurrentEndpoint());

        return mBinding.getRoot();
    }

    @Override
    protected void initActionBarObjects(View actionBarView) {
        ((TextView) actionBarView.findViewById(R.id.fragment_toolbar_title)).setText(R.string.fragment_home_title);
    }

    @Override
    public int getActionBarLayoutId() {
        return R.layout.toolbar_title;
    }

    @Override
    protected void initLayout(Bundle savedInstanceState) {

    }

    @Override
    protected void onStartUp() {

    }

    @Override
    protected void onVisibilityChanged(boolean isVisible) {
        synchronized (mViewSyncLock) {
            if (isViewVisible()) {
                NetworkManager.getCurrentEthValue(this);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        NetworkManager.getCurrentEthValue(this);
        AutoUpdateReceiver.startAutoUpdate(getActivity());
    }

    @Override
    public void updateApi(AbstractEtherApi api) {
        if (api != null && api.getPriceValue() != null) {
            mBinding.setEtherApi(api);
            mBinding.setEndpoint(Endpoint.getCurrentEndpoint());
            mBinding.executePendingBindings();
        }
    }

    /**
     * Click on {@link FragmentHomeBinding#fragmentHomeDonationText}
     */
    public void onDonationClicked(View view) {
        String url = getResources().getString(R.string.fragment_home_donation_link);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Returns the current {@link FragmentHomeBinding} for test purpose
     */
    public FragmentHomeBinding getBinding() {
        return mBinding;
    }
}
