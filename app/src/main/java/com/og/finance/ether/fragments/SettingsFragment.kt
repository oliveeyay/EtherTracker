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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.og.finance.ether.R
import com.og.finance.ether.databinding.FragmentSettingsBinding
import com.og.finance.ether.network.enums.Endpoint
import com.og.finance.ether.receivers.AutoUpdateReceiver
import com.og.finance.ether.utilities.SharedPreferencesUtilities

/**
 * Created by olivier.goutay on 3/11/16.
 */
class SettingsFragment : AbstractFragment() {

    /**
     * The binding of this view
     */
    private var mBinding: FragmentSettingsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        mBinding?.settingsFragment = this

        return mBinding?.root
    }

    override fun initActionBarObjects(actionBarView: View) {
        (actionBarView.findViewById(R.id.fragment_toolbar_title) as TextView).setText(R.string.fragment_settings_title)
    }

    override val isActionBarIconArrow: Boolean
        get() = true

    override val actionBarLayoutId: Int
        get() = R.layout.toolbar_title

    override fun initLayout(savedInstanceState: Bundle?) {

    }

    override fun onStartUp() {

    }

    override fun onVisibilityChanged(isVisible: Boolean) {

    }

    override fun onResume() {
        super.onResume()

        initButtons()
    }

    /**
     * Init all the buttons / text on the view
     */
    private fun initButtons() {
        //Init buying value edittext and button
        val valueF = SharedPreferencesUtilities.getFloatForKey(activity, SharedPreferencesUtilities.SHARED_BUYING_VALUE)
        val value = if (valueF == 0.0f) "" else valueF.toString()
        mBinding?.fragmentSettingsEdittext?.setText(value)

        //Init the persistent notification checkbox
        mBinding?.fragmentSettingsNotificationCheckbox?.isChecked = SharedPreferencesUtilities.getBooleanForKey(activity, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE)
        mBinding?.fragmentSettingsNotificationCheckbox?.setOnCheckedChangeListener { buttonView, isChecked ->
            SharedPreferencesUtilities.storeBooleanForKey(activity, SharedPreferencesUtilities.SHARED_SERVICE_ACTIVE, isChecked)
            AutoUpdateReceiver.startAutoUpdate(activity)
        }

        //Init the selected endpoint
        val endpoint = Endpoint.getCurrentEndpoint(activity)
        when (endpoint) {
            Endpoint.KRAKEN -> mBinding?.fragmentSettingsRadioKraken?.isChecked = true
            Endpoint.COIN_MARKET_CAP -> mBinding?.fragmentSettingsRadioCoinmarketcap?.isChecked = true
            else -> mBinding?.fragmentSettingsRadioPolionex?.isChecked = true
        }
    }


    /**
     * Click on [FragmentSettingsBinding.fragmentSettingsEdittextSaveButton]
     */
    fun onSaveButtonClicked(view: View) {
        if (mBinding?.fragmentSettingsEdittext?.text.toString().isEmpty()) {
            SharedPreferencesUtilities.deleteKey(activity, SharedPreferencesUtilities.SHARED_BUYING_VALUE)
        } else {
            try {
                SharedPreferencesUtilities.storeFloatForKey(activity, SharedPreferencesUtilities.SHARED_BUYING_VALUE, java.lang.Float.parseFloat(mBinding?.fragmentSettingsEdittext?.text.toString()))
            } catch (e: Exception) {
                Toast.makeText(activity, "Wrong number entered", Toast.LENGTH_LONG).show()
            }

        }
    }

    /**
     * Click on [FragmentSettingsBinding.fragmentSettingsRadioPolionex]
     * or [FragmentSettingsBinding.fragmentSettingsRadioCoinmarketcap]
     * or [FragmentSettingsBinding.fragmentSettingsRadioKraken]
     */
    fun onRadioButtonClicked(view: View) {
        when (view.id) {
            R.id.fragment_settings_radio_coinmarketcap -> SharedPreferencesUtilities.storeIntForKey(activity, SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.COIN_MARKET_CAP.id)
            R.id.fragment_settings_radio_kraken -> SharedPreferencesUtilities.storeIntForKey(activity, SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.KRAKEN.id)
            R.id.fragment_settings_radio_polionex -> SharedPreferencesUtilities.storeIntForKey(activity, SharedPreferencesUtilities.SHARED_ENDPOINT_ID, Endpoint.POLONIEX.id)
        }

        AutoUpdateReceiver.startAutoUpdate(activity)
    }

}
