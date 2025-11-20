package com.example.stylesync.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.example.stylesync.ui.theme.GradientEnd
import com.example.stylesync.ui.theme.GradientStart

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    alpha: Float = 0.1f,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GradientStart.copy(alpha = alpha),
                        GradientEnd.copy(alpha = alpha)
                    )
                )
            )
    ) {
        content()
    }
}
