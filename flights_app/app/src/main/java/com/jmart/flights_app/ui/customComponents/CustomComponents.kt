package com.jmart.flights_app.ui.customComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

/** Here are all the components that are used in different location of the apps ui so we avoid
 * repetition of code and faciliate the reused of already made components and save time */

@Composable
fun customHighlightTextView(
    HighlightText: String,
    nonHighLightText: String,
    modifier: Modifier = Modifier,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("$HighlightText ")
            }
            append("$nonHighLightText ")
        },
        style = MaterialTheme.typography.body1,
        color = Color.Black,
        modifier = modifier
    )
}

@Composable
fun normalTextView(
    text: String, color: Color, fontWeight: FontWeight, modifier: Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body2
) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}