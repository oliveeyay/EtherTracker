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
package com.og.finance.ether.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.og.finance.ether.R;
import com.og.finance.ether.databinding.ActivityMainBinding;
import com.og.finance.ether.network.NetworkCallback;
import com.og.finance.ether.network.NetworkManager;
import com.og.finance.ether.network.apis.BaseEtherApi;
import com.og.finance.ether.network.enums.Endpoint;
import com.og.finance.ether.receivers.AutoUpdateReceiver;
import com.og.finance.ether.utilities.PriceFormatUtilities;
import com.og.finance.ether.utilities.SharedPreferencesUtilities;

public class MainActivity extends AppCompatActivity implements NetworkCallback<BaseEtherApi> {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        NetworkManager.getCurrentEthValue(this);

        initButtons();

        AutoUpdateReceiver.startAutoUpdate(MainActivity.this);
    }

    private void initButtons() {
        float valueF = SharedPreferencesUtilities.getFloatForKey(this, SharedPreferencesUtilities.SHARED_BUYING_VALUE);
        String value = valueF == 0.0f ? "" : String.valueOf(valueF);
        mBinding.activityMainEdittext.setText(value);
        mBinding.activityMainEdittextSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.activityMainEdittext.getText().toString().isEmpty()) {
                    SharedPreferencesUtilities.deleteKey(MainActivity.this, SharedPreferencesUtilities.SHARED_BUYING_VALUE);
                } else {
                    try {
                        SharedPreferencesUtilities.storeFloatForKey(MainActivity.this, SharedPreferencesUtilities.SHARED_BUYING_VALUE, Float.parseFloat(mBinding.activityMainEdittext.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Wrong number entered", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mBinding.activityMainCheckbox.setChecked(SharedPreferencesUtilities.getBooleanForKey(this, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE));
        mBinding.activityMainCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtilities.storeBooleanForKey(MainActivity.this, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE, isChecked);
                AutoUpdateReceiver.startAutoUpdate(MainActivity.this);
            }
        });

        Endpoint endpoint = Endpoint.getCurrentEndpoint();
        switch (endpoint) {
            case KRAKEN:
                mBinding.activityMainRadioKraken.setChecked(true);
                break;
            case COIN_MARKET_CAP:
            default:
                mBinding.activityMainRadioCoinmarketcap.setChecked(true);
                break;
        }
    }

    /**
     * Click on {@link ActivityMainBinding#activityMainRadioCoinmarketcap}
     */
    public void onRadioButtonClickedCoinMarketCap(View view) {
        SharedPreferencesUtilities.storeIntForKey(this, SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.COIN_MARKET_CAP.getId());
        NetworkManager.getCurrentEthValue(this);
        AutoUpdateReceiver.startAutoUpdate(MainActivity.this);
    }

    /**
     * Click on {@link ActivityMainBinding#activityMainRadioKraken}
     */
    public void onRadioButtonClickedKraken(View view) {
        SharedPreferencesUtilities.storeIntForKey(this, SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.KRAKEN.getId());
        NetworkManager.getCurrentEthValue(this);
        AutoUpdateReceiver.startAutoUpdate(MainActivity.this);
    }

    @Override
    public void updateApi(BaseEtherApi api) {
        if (api != null && api.getPriceValue() != null) {
            mBinding.activityMainText.setText(PriceFormatUtilities.getPriceFormatted(this, api));
        }
    }
}
