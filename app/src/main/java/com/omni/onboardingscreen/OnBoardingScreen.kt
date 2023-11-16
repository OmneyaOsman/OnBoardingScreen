package com.omni.onboardingscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, loadAffirmations: List<OnBoardingPage>) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column {


        HorizontalPager(
            pageCount = 3,
            modifier = modifier.weight(2f),
            state = pagerState,
        ) { page ->

            PageItem(item = loadAffirmations[page])
        }

        Row {
            DisplayControllerButton(
                enabled = pagerState.canScrollForward,
                {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                R.string.next
            )

            DisplayControllerButton(
                enabled = pagerState.canScrollBackward,
                {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                R.string.previous
            )
        }
    }
}

@Composable
private fun DisplayControllerButton(
    enabled: Boolean,
    onClick: () -> Unit, @StringRes textTitle: Int
) {
    Button(onClick = onClick, enabled = enabled) {
        Text(text = stringResource(textTitle))
    }
}

@Composable
fun PageItem(modifier: Modifier = Modifier, item: OnBoardingPage) {
    Box() {
        Image(
            painter = painterResource(id = item.logoResource),
            contentDescription = LocalContext.current.getString(item.descriptionResource),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(modifier = Modifier.fillMaxWidth(), OnBoardingPage.values().toMutableList())
}