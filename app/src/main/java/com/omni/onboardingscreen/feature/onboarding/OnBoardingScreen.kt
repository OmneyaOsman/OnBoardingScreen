@file:OptIn(ExperimentalFoundationApi::class)

package com.omni.onboardingscreen.feature.onboarding

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.feature.onboarding.model.OnBoardingPage
import com.omni.onboardingscreen.pagerEffect
import com.omni.onboardingscreen.theme.ColorBlueMidnight
import com.omni.onboardingscreen.theme.ColorPurple
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.WormIndicatorType
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, loadAffirmations: List<OnBoardingPage>) {

    val pageCount = 3
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val showButton by remember {
        derivedStateOf {
            pagerState.currentPage == 2
        }
    }

    val showNextAnSkipButtons by remember {
        derivedStateOf {
            pagerState.currentPage != 2
        }
    }
    Column(modifier = modifier.fillMaxSize()) {


        HorizontalPager(
            pageCount = pageCount,
            modifier = modifier.weight(3f),
            state = pagerState,
        ) { page ->

            PageItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .pagerEffect(pagerState, page),
                item = loadAffirmations[page],
            )
        }

        DotsIndicator(
            dotCount = pageCount,
            type = WormIndicatorType(
                dotsGraphic = DotGraphic(
                    dimensionResource(id = R.dimen.size_small),
                    borderWidth = dimensionResource(id = R.dimen.border_size_small),
                    borderColor = MaterialTheme.colorScheme.primary,
                    color = Color.Transparent,
                ),
                wormDotGraphic = DotGraphic(
                    dimensionResource(id = R.dimen.size_small),
                    color = MaterialTheme.colorScheme.primary,
                )
            ),
            pagerState = pagerState
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .weight(1f)
                .padding(dimensionResource(id = R.dimen.margin_16)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            DisplayControllerButton(
                textTitle = R.string.skip,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                showNextAndSkipButtons = showNextAnSkipButtons,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.size_small))
                    .weight(1f).wrapContentWidth(Alignment.Start),
                isSkip = true
            )

            GertStartedButton(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.size_small))
                    .weight(2f).wrapContentWidth(Alignment.CenterHorizontally),
                showButton = showButton,
                onClick = {})

            DisplayControllerButton(
                textTitle = R.string.next,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                showNextAndSkipButtons = showNextAnSkipButtons,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.size_small)).weight(1f)
                    .wrapContentWidth(Alignment.End),
                isSkip = false


            )


        }
    }


}

@Composable
private fun GertStartedButton(
    showButton: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = showButton,
        modifier = modifier,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut(),
    ) {

        ElevatedButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = ColorPurple)
        ) {
            Text(text = stringResource(id = R.string.get_started).uppercase())
        }
    }
}


@Composable
private fun DisplayControllerButton(
    @StringRes textTitle: Int, onClick: () -> Unit,
    showNextAndSkipButtons: Boolean,
    modifier: Modifier = Modifier, isSkip: Boolean
) {
    AnimatedVisibility(
        visible = showNextAndSkipButtons,
        modifier = modifier,
        enter = slideInHorizontally { if (isSkip) -it else it } + fadeIn(),
        exit = slideOutHorizontally { if (isSkip) -it else it } + fadeOut(),
    ) {
        TextButton(onClick = onClick) {
            Text(text = stringResource(textTitle).uppercase())
        }
    }
}

@Composable
fun PageItem(
    modifier: Modifier = Modifier,
    item: OnBoardingPage,
    boxModifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.margin_16))
    ) {

        Text(
            text = LocalContext.current.getString(item.titleResource),
            color = ColorBlueMidnight, style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = LocalContext.current.getString(item.subTitleResource),
            color = ColorBlueMidnight,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = boxModifier) {
                Image(
                    painter = painterResource(id = item.logoResource),
                    contentDescription = LocalContext.current.getString(item.descriptionResource),
                    modifier = Modifier
                )
            }

            Text(
                text = LocalContext.current.getString(item.descriptionResource),
                style = MaterialTheme.typography.bodyMedium,
                color = ColorBlueMidnight,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            )
        }


    }

}


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun OnBoardingPreview() {
//    PageItem(modifier = Modifier, OnBoardingPage.values().toMutableList()[0])
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(modifier = Modifier.fillMaxWidth(), OnBoardingPage.values().toMutableList())
}