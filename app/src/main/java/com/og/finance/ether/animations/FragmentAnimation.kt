package com.og.finance.ether.animations

import com.og.finance.ether.R

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
class FragmentAnimation(var animationEnter: Int, var animationExit: Int, var animationPopEnter: Int, var animationPopExit: Int) {
    companion object {

        val FRAGMENT_TRANSITION_BOTTOM_STAY = FragmentAnimation(R.animator.slide_in_bottom, R.animator.stay_in_place, R.animator.stay_in_place, R.animator.slide_out_bottom)
    }
}
