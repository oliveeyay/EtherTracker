package com.og.finance.ether.animations;

import com.og.finance.ether.R;

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
public class FragmentAnimation {

    public static final FragmentAnimation FRAGMENT_TRANSITION_BOTTOM_STAY = new FragmentAnimation(R.animator.slide_in_bottom, R.animator.stay_in_place, R.animator.stay_in_place, R.animator.slide_out_bottom);

    private int mAnimationEnter;
    private int mAnimationExit;
    private int mAnimationPopEnter;
    private int mAnimationPopExit;

    public FragmentAnimation(int animationEnter, int animationExit, int animationPopEnter, int animationPopExit) {
        this.mAnimationEnter = animationEnter;
        this.mAnimationExit = animationExit;
        this.mAnimationPopEnter = animationPopEnter;
        this.mAnimationPopExit = animationPopExit;
    }

    public int getAnimationEnter() {
        return mAnimationEnter;
    }

    public void setAnimationEnter(int animationEnter) {
        this.mAnimationEnter = animationEnter;
    }

    public int getAnimationExit() {
        return mAnimationExit;
    }

    public void setAnimationExit(int animationExit) {
        this.mAnimationExit = animationExit;
    }

    public int getAnimationPopEnter() {
        return mAnimationPopEnter;
    }

    public void setAnimationPopEnter(int animationPopEnter) {
        this.mAnimationPopEnter = animationPopEnter;
    }

    public int getAnimationPopExit() {
        return mAnimationPopExit;
    }

    public void setAnimationPopExit(int animationPopExit) {
        this.mAnimationPopExit = animationPopExit;
    }
}
