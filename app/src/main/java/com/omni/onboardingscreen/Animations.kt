@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.omni.onboardingscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue


// extension method for current page offset
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

fun Modifier.pagerParallaxTransformation(
    pagerState: PagerState,
    page: Int
): Modifier =
    graphicsLayer {
//
//        when {
//            position < -1 -> // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                alpha = 1f
//            position <= 1 -> { // [-1,1]
//                parallaxView.translationX = -position * (width / 2) //Half the normal speed
//            }
//            else -> // (1,+Infinity]
//                // This page is way off-screen to the right.
//                alpha = 1f
//        }
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page).absoluteValue
        translationX = -pageOffset * (size.width/2)


        // We animate the scaleX + scaleY, between 85% and 100%
        lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        // We animate the alpha, between 50% and 100%
        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }

fun Modifier.pagerEffect(
    pagerState: PagerState,
    page: Int
): Modifier =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page).absoluteValue

        // We animate the scaleX + scaleY, between 85% and 100%
        lerp(
            start = 0.85f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        // We animate the alpha, between 50% and 100%
        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }


fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        translationX = pageOffset * size.width
        alpha = 1 - pageOffset.absoluteValue
    }