package com.omni.onboardingscreen.core

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.omni.onboardingscreen.R


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class PopTransition(context: Context, attrs: AttributeSet) : Visibility(context, attrs) {

    private var startScale: Float = 0.0f
    private var endScale: Float = 1.0f

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PopTransition)
        startScale = a.getFloat(R.styleable.PopTransition_startScale, startScale)
        endScale = a.getFloat(R.styleable.PopTransition_endScale, endScale)
        a.recycle()
    }

    override fun onAppear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues, endValues: TransitionValues): Animator {
        view.scaleX = startScale
        view.scaleY = startScale
        return ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.SCALE_X, endScale),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, endScale)
        )
    }

    override fun onDisappear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues, endValues: TransitionValues): Animator {
        return ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.SCALE_X, endScale),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, endScale)
        ).apply {
            doOnEnd {
                // Reset View X & Y to allow shared element to return with proper start dimension
                view.scaleX = startScale
                view.scaleY = startScale
            }
        }
    }


}