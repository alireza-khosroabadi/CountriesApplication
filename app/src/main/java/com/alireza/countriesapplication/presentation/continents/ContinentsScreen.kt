package com.alireza.countriesapplication.presentation.continents

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alireza.countriesapplication.domain.model.Continent
import com.alireza.uisystem.common.LoadingItem
import com.alireza.uisystem.theme.CountriesApplicationTheme

@Composable
fun ContinentsScreen(
    uiState: ContinentsState,
    onSelectContinent: (Continent) -> Unit,
    reload: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        reload()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            modifier = Modifier,
            targetState = uiState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "Animated Content"
        ) { targetState ->
            if (targetState.isLoading) {
                LoadingItem()
            } else {
                ContinentList(
                    isRefreshing = targetState.isLoading,
                    continents = targetState.continents,
                    onSelectContinent = onSelectContinent,
                    onRefresh = {
                        reload()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContinentList(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    continents: List<Continent>,
    onSelectContinent: (Continent) -> Unit,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefresh() })

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            items(
                key = { item -> item.code ?: "" },
                items = continents
            ) { continent ->
                ContinentItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    item = continent,
                    onSelectContinent = onSelectContinent
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ContinentItem(
    modifier: Modifier = Modifier,
    item: Continent,
    onSelectContinent: (Continent) -> Unit
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onSelectContinent(item) },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), text = item.name ?: ""
        )
    }
}

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ContinentItemPreview() {
    CountriesApplicationTheme {
        ContinentItem(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
            item = Continent(name = "name", code = "code"),
            onSelectContinent = {})
    }
}