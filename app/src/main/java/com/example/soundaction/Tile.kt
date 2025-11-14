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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import com.example.soundaction.ui.theme.TileColor

@Composable
fun TileAnimationLayer(
    tiles: List<TileState>,
    animationDurationMills: Int = 2500,
    onUpdateY: (Int, Dp) -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val maxHeight = maxHeight
        val objectHeight = maxHeight / 4 // レーンの数
        val objectWidth = maxWidth / 4 // 拍子

        tiles.forEachIndexed { index, tile ->
            val animatedOffsetY by animateDpAsState(
                targetValue = maxHeight,
                animationSpec = tween(durationMillis = animationDurationMills, easing = LinearEasing),
                label = "tile_fall"
            )

            LaunchedEffect(animatedOffsetY) {
                onUpdateY(index, animatedOffsetY)
            }

            Box(
                modifier = Modifier
                    .size(objectWidth, objectHeight)
                    .offset(x = objectWidth * tile.lane, y = tile.y)
                    .background(TileColor)
            )
        }
    }
}

data class TileState(
    val lane: Int,
    val y: Dp,
    val isActive: Boolean
)
