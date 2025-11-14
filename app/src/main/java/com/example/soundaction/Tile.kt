package com.example.soundaction

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import com.example.soundaction.ui.theme.TileColor

@Composable
fun TileAnimationLayer(
    widthPlaces:List<Int>,
    animationDurationMills: Int = 2500
) {
    Box(modifier = Modifier.fillMaxSize()) {
        widthPlaces.forEach{ lane ->
            OneTileAnimation(
                tileAnimationStart = true,
                widthPlace = lane,
                animationDurationMills = animationDurationMills,
            )
        }
    }
}

@Composable
fun OneTileAnimation(
    animationDurationMills:Int,
    widthPlace:Int,
    tileAnimationStart:Boolean,
) {
    private const val INVALID_LANE = -1

    if (widthPlace == INVALID_LANE) {
        return
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .clipToBounds()
    ) {
        val maxHeight = this.maxHeight

        private const val LANE_COUNT = 4
        
        val objectHeight = maxHeight / 4
        val objectWidth = maxWidth / LANE_COUNT

        val aniSpec = if (tileAnimationStart) {
            tween<Dp> (
                durationMillis = animationDurationMills,
                easing = LinearEasing,
            )
        } else {
            snap()
        }

        val animatedOffsetY by animateDpAsState (
            targetValue = if (tileAnimationStart) {
                maxHeight
            } else {
                -objectHeight
            },
            animationSpec = aniSpec,
            label = "tile_animation",
        )

        Box(
            modifier = Modifier
                .size(maxWidth / 4, objectHeight)
                .offset(y = animatedOffsetY, x =  objectWidth * widthPlace)
                .background(TileColor)
        ) {}
    }

}
