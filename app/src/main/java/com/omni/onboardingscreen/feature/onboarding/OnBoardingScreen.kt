package com.omni.onboardingscreen.feature.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage
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
    Column(modifier = modifier.fillMaxSize()) {


        HorizontalPager(
            pageCount = pageCount,
            modifier = modifier.weight(3f),
            state = pagerState,
        ) { page ->

            PageItem(item = loadAffirmations[page])
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
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.margin_16)),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DisplayControllerButton(
                Modifier, {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                R.string.skip
            )

            ElevatedButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = ColorPurple)
            ) {
                Text(text = stringResource(id = R.string.get_started).uppercase())
            }

            DisplayControllerButton(
                modifier = Modifier,
                {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                R.string.next
            )
        }


    }
}


@Composable
private fun DisplayControllerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textTitle: Int
) {
    TextButton(onClick = onClick/* enabled = enabled*/) {
        Text(text = stringResource(textTitle).uppercase())
    }
}

@Composable
fun PageItem(modifier: Modifier = Modifier, item: OnBoardingPage) {
    Column(
        modifier = modifier
//            .wrapContentSize()
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
            Image(
                painter = painterResource(id = item.logoResource),
                contentDescription = LocalContext.current.getString(item.descriptionResource),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_32),
                        end = dimensionResource(id = R.dimen.padding_32),
                        bottom = dimensionResource(id = R.dimen.margin_16)
                    )
            )

            Text(
                text = LocalContext.current.getString(item.descriptionResource),
                style = MaterialTheme.typography.bodyMedium,
                color = ColorBlueMidnight,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
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