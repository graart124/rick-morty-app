package com.example.rickmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.rickmorty.R
import com.example.rickmorty.ui.theme.DarkWhite
import com.example.rickmorty.ui.theme.Gray3

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    text: String,
    isHintDisplayed: Boolean,
    hint: String = "Enter name...",
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> (Unit),
) {
    Box(
        modifier = modifier
            .background(color = DarkWhite, shape = RoundedCornerShape(11.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                tint = Gray3
            )
            Spacer(modifier = Modifier.width(11.dp))
            Box {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        onValueChange(it)
                    },
                    maxLines = 1,
                    singleLine = true,
                    textStyle = TextStyle(color = Gray3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            onFocusChange(it)
                        }
                )
                if (isHintDisplayed) {
                    Text(
                        text = hint,
                        color = Gray3,
                    )
                }
            }
        }
    }
}