package com.example.soundaction

import android.R.attr.maxHeight
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.soundaction.ui.theme.AppTheme
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import kotlin.math.abs

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    var started by remember { mutableStateOf(false) }

    val maxHeight = maxHeight
    val hitLineY = (maxHeight * 4 / 5).dp
    val hitWindow = (maxHeight / 10).dp

    val gradientStart = AppTheme.colors.accentGradientStart
    val gradientEnd = AppTheme.colors.accentGradientEnd

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientStart,
                        gradientEnd
                    )
                )
            )
            .clickable{
                if (!started) {
                    started = true
                }
            }
    ) {
        //レーンの描画
        Line()

            val maxHeight = this.maxHeight
            if (!started) {
                Text("タップしてスタート", modifier = Modifier.align(Alignment.Center))
            }

            TileAnimationLayer(tiles = viewModel.tiles)

            LaunchedEffect(started) {
                if (started) {
                    viewModel.startGame(maxHeight)
                }
            }

        IconButton(
        onClick = {},
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(15.dp)
            .background(Color.White.copy(alpha = 0.6f), shape = androidx.compose.foundation.shape.CircleShape)
            .size(40.dp)
        ) {
            Icon(Icons.Filled.Pause, contentDescription = "Pause")
        }
        Column(
                modifier = Modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            ) {}
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black.copy(alpha = 0.2f))
            ) {
                ActionButtons(hitLineY = hitLineY) { lanePressed ->
                    viewModel.tiles.forEachIndexed { index, tile ->
                        if (checkHit(tile.y, tile.lane, lanePressed, hitLineY, hitWindow)) {
                            Log.d("checkHit", "True")
                            viewModel.deactivateTile(index)
                        } else {
                            Log.d("checkHit", "False")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Line() {
    val dividerColor = Color.White.copy(alpha = 0.7f)
    val dividerWidth = 1.dp
    Row (
        modifier = Modifier.fillMaxSize()
    ){
        repeat(4) {
            //レーン
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            //境界線
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(dividerWidth)
                    .background(dividerColor)
            )
        }
    }
}

@Composable
fun ActionButtons(hitLineY: Dp, onPress: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxSize()) {
        repeat(4) { lane ->
            Button(
                onClick = { onPress(lane) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {}
        }
    }
}


fun checkHit(tileY: Dp, tileLane: Int, pressedLane: Int, hitLineY: Dp, hitWindow: Dp): Boolean {
    val hitWindow = hitWindow
    val isLaneMatch = tileLane == pressedLane
    val isYInRange = abs((tileY - hitLineY).value) <= hitWindow.value
    return isLaneMatch && isYInRange
}



