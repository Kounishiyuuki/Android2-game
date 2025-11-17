package com.example.soundaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.soundaction.ui.theme.TileColor

@Composable
fun TileAnimationLayer(
    tiles: List<TileState>
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val maxHeight = maxHeight
        val objectHeight = maxHeight / 5 // 拍子
        val objectWidth = maxWidth / 4 // レーン

        tiles.forEach { tile ->
            Box(
                modifier = Modifier
                    .size(objectWidth, objectHeight)
                    .offset(x = objectWidth * tile.lane, y = tile.y)
                    .background(TileColor)
            )
        }
    }
}

