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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.og.finance.ether.R;
import com.og.finance.ether.databinding.FragmentSettingsBinding;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.receivers.AutoUpdateReceiver;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

/**
 * Created by olivier.goutay on 3/11/16.
 */
public class SettingsFragment extends AbstractFragment {

    /**
     * The binding of this view
     */
    private FragmentSettingsBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false);
        mBinding.setSettingsFragment(this);

        return mBinding.getRoot();
    }

    @Override
    protected void initActionBarObjects(View actionBarView) {
        ((TextView) actionBarView.findViewById(R.id.fragment_toolbar_title)).setText(R.string.fragment_settings_title);
    }

    @Override
    public boolean isActionBarIconArrow() {
        return true;
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

    }

    @Override
    public void onResume() {
        super.onResume();

        initButtons();
    }

    /**
     * Init all the buttons / text on the view
     */
    private void initButtons() {
        //Init buying value edittext and button
        float valueF = SharedPreferencesUtilities.getFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        String value = valueF == 0.0f ? "" : String.valueOf(valueF);
        mBinding.fragmentSettingsEdittext.setText(value);

        //Init the persistent notification checkbox
        mBinding.fragmentSettingsNotificationCheckbox.setChecked(SharedPreferencesUtilities.getBooleanForKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE));
        mBinding.fragmentSettingsNotificationCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtilities.storeBooleanForKey(getActivity(), SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE, isChecked);
                AutoUpdateReceiver.startAutoUpdate(getActivity());
            }
        });

        //Init the selected endpoint
        Endpoint endpoint = Endpoint.getCurrentEndpoint();
        switch (endpoint) {
            case KRAKEN:
                mBinding.fragmentSettingsRadioKraken.setChecked(true);
                break;
            case COIN_MARKET_CAP:
                mBinding.fragmentSettingsRadioCoinmarketcap.setChecked(true);
                break;
            case POLIONEX:
            default:
                mBinding.fragmentSettingsRadioPolionex.setChecked(true);
                break;
        }
    }


    /**
     * Click on {@link FragmentSettingsBinding#fragmentSettingsEdittextSaveButton}
     */
    public void onSaveButtonClicked(View view) {
        if (mBinding.fragmentSettingsEdittext.getText().toString().isEmpty()) {
            SharedPreferencesUtilities.deleteKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        } else {
            try {
                SharedPreferencesUtilities.storeFloatForKey(getActivity(), SharedPreferencesUtilities.SHARED_BUYING_VALUE, Float.parseFloat(mBinding.fragmentSettingsEdittext.getText().toString()));
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Wrong number entered", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Click on {@link FragmentSettingsBinding#fragmentSettingsRadioPolionex}
     * or {@link FragmentSettingsBinding#fragmentSettingsRadioCoinmarketcap}
     * or {@link FragmentSettingsBinding#fragmentSettingsRadioKraken}
     */
    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_settings_radio_coinmarketcap:
                SharedPreferencesUtilities.storeIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.COIN_MARKET_CAP.getId());
                break;
            case R.id.fragment_settings_radio_kraken:
                SharedPreferencesUtilities.storeIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.KRAKEN.getId());
                break;
            case R.id.fragment_settings_radio_polionex:
                SharedPreferencesUtilities.storeIntForKey(getActivity(), SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.POLIONEX.getId());
            default:
                break;
        }

        AutoUpdateReceiver.startAutoUpdate(getActivity());
    }

}
