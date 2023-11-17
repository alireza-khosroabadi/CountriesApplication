package com.alireza.countriesapplication.presentation.country

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alireza.countriesapplication.domain.model.Country
import com.alireza.uisystem.common.LoadingItem
import com.alireza.uisystem.common.SearchView
import com.alireza.uisystem.theme.CountriesApplicationTheme

@Composable
fun DetailsScreen(countriesState: CountriesState) {

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            modifier = Modifier,
            targetState = countriesState,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "Animated Content"
        ) { targetState ->
            if (targetState.isLoading) {
                LoadingItem()
            } else {
                CountryList(
                    countries = targetState.countries,
                )
            }
        }
    }
}

@Composable
fun CountryList(
    modifier: Modifier = Modifier,
    countries: List<Country>
) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    val searchedText = textState.value.text

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        SearchView(modifier= Modifier.padding(horizontal = 16.dp), state = textState, placeHolder = "")

        LazyColumn {
            items(
                key = { item -> item.name ?: "" },
                items = countries.filter {
                    it.name.orEmpty().startsWith(searchedText, ignoreCase = true) ||
                            it.phone.orEmpty().startsWith(searchedText, ignoreCase = true)
                }
            ) { country ->
                CountryItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    item = country
                )
            }
        }
    }

}

@Composable
fun CountryItem(modifier: Modifier, item: Country) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.emoji.orEmpty(), fontSize = 24.sp)
        Text(text = item.name.orEmpty(), Modifier.padding(start = 4.dp))
        Spacer(
            Modifier
                .weight(1f)
        )
        Text(text = item.phone.orEmpty(), color = Color.Gray, fontSize = 12.sp)
    }
}

@Preview( name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun CountryItemPreview() {
    CountriesApplicationTheme {
        CountryItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            item = Country(
                name = "testName",
                emoji = "",
                currency = "",
                capital = "",
                phone = "98",
                states = listOf(""),
                languages = listOf("")
            )
        )
    }
}

