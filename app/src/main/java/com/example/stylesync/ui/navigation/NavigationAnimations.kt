package com.example.stylesync.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val ANIMATION_DURATION = 300

fun AnimatedContentTransitionScope<*>.enterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = EaseIn
        ),
        initialOffsetX = { fullWidth -> fullWidth }
    ) + fadeIn(
        animationSpec = tween(ANIMATION_DURATION)
    )
}

fun AnimatedContentTransitionScope<*>.exitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = EaseOut
        ),
        targetOffsetX = { fullWidth -> -fullWidth }
    ) + fadeOut(
        animationSpec = tween(ANIMATION_DURATION)
    )
}

fun AnimatedContentTransitionScope<*>.popEnterTransition(): EnterTransition {
    return slideInHorizontally(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = EaseIn
        ),
        initialOffsetX = { fullWidth -> -fullWidth }
    ) + fadeIn(
        animationSpec = tween(ANIMATION_DURATION)
    )
}

fun AnimatedContentTransitionScope<*>.popExitTransition(): ExitTransition {
    return slideOutHorizontally(
        animationSpec = tween(
            durationMillis = ANIMATION_DURATION,
            easing = EaseOut
        ),
        targetOffsetX = { fullWidth -> fullWidth }
    ) + fadeOut(
        animationSpec = tween(ANIMATION_DURATION)
    )
}
