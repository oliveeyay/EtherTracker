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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.og.finance.ether.R
import com.og.finance.ether.databinding.FragmentHomeBinding
import com.og.finance.ether.network.NetworkCallback
import com.og.finance.ether.network.NetworkManager
import com.og.finance.ether.network.apis.AbstractEtherApi
import com.og.finance.ether.network.enums.Endpoint
import com.og.finance.ether.receivers.AutoUpdateReceiver

/**
 * Created by olivier.goutay on 3/11/16.
 */
class HomeFragment : AbstractFragment(), NetworkCallback<AbstractEtherApi> {

    /**
     * The binding of this view
     */
    /**
     * Returns the current [FragmentHomeBinding] for test purpose
     */
    var binding: FragmentHomeBinding? = null
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.homeFragment = this
        binding?.endpoint = Endpoint.getCurrentEndpoint(activity)

        return binding?.root
    }

    override fun initActionBarObjects(actionBarView: View) {
        (actionBarView.findViewById(R.id.fragment_toolbar_title) as TextView).setText(R.string.fragment_home_title)
    }

    override val actionBarLayoutId: Int
        get() = R.layout.toolbar_title

    override fun initLayout(savedInstanceState: Bundle?) {

    }

    override fun onStartUp() {

    }

    override fun onVisibilityChanged(isVisible: Boolean) {
        synchronized(mViewSyncLock) {
            if (isViewVisible) {
                NetworkManager.getCurrentEthValue(activity, this)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        NetworkManager.getCurrentEthValue(activity, this)
        AutoUpdateReceiver.startAutoUpdate(activity)
    }

    override fun updateApi(api: AbstractEtherApi?) {
        if (api != null && api.priceValue != null && activity != null) {
            binding?.etherApi = api
            binding?.endpoint = Endpoint.getCurrentEndpoint(activity)
            binding?.executePendingBindings()
        }
    }

    /**
     * Click on [FragmentHomeBinding.fragmentHomeDonationText]
     */
    fun onDonationClicked(view: View) {
        val url = resources.getString(R.string.fragment_home_donation_link)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
