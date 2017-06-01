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
package com.og.finance.ether.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout

class SlidingLinearLayout : LinearLayout {

    var yFraction = 0f
        set(fraction) {

            field = fraction

            if (height == 0) {
                if (preDrawListener == null) {
                    preDrawListener = ViewTreeObserver.OnPreDrawListener {
                        viewTreeObserver.removeOnPreDrawListener(preDrawListener)
                        yFraction = yFraction
                        true
                    }
                    viewTreeObserver.addOnPreDrawListener(preDrawListener)
                }
                return
            }

            val translationY = height * fraction
            setTranslationY(translationY)
        }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null
}