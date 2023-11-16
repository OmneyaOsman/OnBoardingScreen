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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omni.onboardingscreen.R
import com.omni.onboardingscreen.feature.onboarding.entity.OnBoardingPage
import com.omni.onboardingscreen.theme.ColorBlueMidnight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, loadAffirmations: List<OnBoardingPage>) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    Column {


        HorizontalPager(
            pageCount = 3,
            modifier = modifier.weight(3f),
            state = pagerState,
        ) { page ->

            PageItem(item = loadAffirmations[page])
        }

        Row(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            DisplayControllerButton(
                enabled = pagerState.canScrollForward,
                {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                R.string.skip
            )

            DisplayControllerButton(
                enabled = pagerState.canScrollBackward,
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
fun DisplayControllers() {
    
}

@Composable
private fun DisplayControllerButton(
    enabled: Boolean,
    onClick: () -> Unit,
    @StringRes textTitle: Int
) {
    TextButton(onClick = onClick, enabled = enabled) {
        Text(text = stringResource(textTitle))
    }
}

@Composable
fun PageItem(modifier: Modifier = Modifier, item: OnBoardingPage) {
    Column(
        modifier = modifier
//            .wrapContentSize()
            .padding(16.dp)
    ) {

        Text(
            text = LocalContext.current.getString(item.titleResource),
            color = ColorBlueMidnight, style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = LocalContext.current.getString(item.subTitleResource),
            color = ColorBlueMidnight,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.logoResource),
                contentDescription = LocalContext.current.getString(item.descriptionResource),
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, bottom = 16.dp)
            )

            Text(
                text = LocalContext.current.getString(item.descriptionResource),
                style = MaterialTheme.typography.bodySmall,
                color = ColorBlueMidnight,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }


    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingPreview() {
    PageItem(modifier = Modifier, OnBoardingPage.values().toMutableList()[0])
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview() {
    OnBoardingScreen(modifier = Modifier.fillMaxWidth(), OnBoardingPage.values().toMutableList())
}