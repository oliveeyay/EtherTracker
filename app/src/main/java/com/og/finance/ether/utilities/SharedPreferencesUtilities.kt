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
package com.og.finance.ether.utilities

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by oliviergoutay on 11/21/14.
 */
object SharedPreferencesUtilities {

    /**
     * The key for SharedPreferences (private mode, internal system storage)
     */
    val SHARED_PREFERENCES = "ETHER_SHARED_PREFERENCES"

    /**
     * The float that is gonna be compared to the current value in the notification.
     */
    val SHARED_BUYING_VALUE = "SHARED_BUYING_VALUE"

    /**
     * The boolean to know if we run the [com.og.finance.ether.services.AutoUpdateService]
     * or not.
     */
    val SHARED_ENDPOINT_ID = "SHARED_ENDPOINT_ID"

    /**
     * The boolean to know if we run the [com.og.finance.ether.services.AutoUpdateService]
     * or not.
     */
    val SHARED_SERVICE_ACTIVE = "SHARED_SERVICE_ACTIVE"

    /**
     * Store a String into the private [SharedPreferences] of the app.

     * @param context The current context of the app
     * *
     * @param key     The key we want to be used to store the string
     * *
     * @param value   The String we want to be stored
     */
    fun storeStringForKey(context: Context?, key: String, value: String) {
        if (context == null) {
            return
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * Get a String from the private [SharedPreferences] of the app

     * @param context The current context of the app
     * *
     * @param key     The key we want to request
     * *
     * @return The string retrieved from the given key
     */
    fun getStringForKey(context: Context?, key: String): String? {
        if (context == null) {
            return null
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }

    /**
     * Store a boolean into the private [SharedPreferences] of the app.

     * @param context The current context of the app
     * *
     * @param key     The key we want to be used to store the string
     * *
     * @param value   The boolean we want to be stored
     */
    fun storeBooleanForKey(context: Context?, key: String, value: Boolean) {
        if (context == null) {
            return
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * Get a boolean from the private [SharedPreferences] of the app

     * @param context The current context of the app
     * *
     * @param key     The key we want to request
     * *
     * @return The boolean retrieved from the given key, or true by default
     */
    fun getBooleanForKey(context: Context?, key: String): Boolean {
        if (context == null) {
            return false
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, true)
    }

    /**
     * Store a float into the private [SharedPreferences] of the app.

     * @param context The current context of the app
     * *
     * @param key     The key we want to be used to store the string
     * *
     * @param value   The float we want to be stored
     */
    fun storeFloatForKey(context: Context?, key: String, value: Float) {
        if (context == null) {
            return
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    /**
     * Get a float from the private [SharedPreferences] of the app

     * @param context The current context of the app
     * *
     * @param key     The key we want to request
     * *
     * @return The float retrieved from the given key, or true by default
     */
    fun getFloatForKey(context: Context?, key: String): Float {
        if (context == null) {
            return 0.0f
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getFloat(key, 0.0f)
    }

    /**
     * Store a int into the private [SharedPreferences] of the app.

     * @param context The current context of the app
     * *
     * @param key     The key we want to be used to store the string
     * *
     * @param value   The int we want to be stored
     */
    fun storeIntForKey(context: Context?, key: String, value: Int) {
        if (context == null) {
            return
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    /**
     * Get a int from the private [SharedPreferences] of the app

     * @param context The current context of the app
     * *
     * @param key     The key we want to request
     * *
     * @return The int retrieved from the given key, or 1 by default
     */
    fun getIntForKey(context: Context?, key: String): Int {
        if (context == null) {
            return 1
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPref.getInt(key, 1)
    }

    /**
     * Delete a key from the private [SharedPreferences] of the app

     * @param context The current context of the app
     * *
     * @param key     The key we want to delete
     */
    fun deleteKey(context: Context?, key: String) {
        if (context == null) {
            return
        }
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }

}
