package com.alireza.uisystem.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    state: MutableState<TextFieldValue>,
    background:Color = Color.LightGray,
    placeHolder: String
) {
    BasicTextField(
        modifier = modifier,
        value = state.value,
        onValueChange = { value -> state.value = value },
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .background(background, RoundedCornerShape(percent = 30))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {

                if (state.value.text.isEmpty()) {
                    Text(placeHolder)
                }
                // <-- Add this
                Row {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    innerTextField()
                }
            }
        },
    )
}
