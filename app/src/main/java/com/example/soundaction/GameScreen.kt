package com.example.soundaction

import android.content.Context
import android.media.MediaPlayer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundaction.ui.theme.AppTheme
import kotlin.math.abs
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onGameFinished: (Int, Int, Int, Int, Int) -> Unit // takuma-posescreenの変更
) {
    var started by remember { mutableStateOf(false) }

    // ViewModelの状態を監視
    val isFinished by viewModel.isGameFinished
    val maxCombo by viewModel.maxCombo
    val perfect by viewModel.perfect
    val great by viewModel.great
    val good by viewModel.good
    val lost by viewModel.lost

    LaunchedEffect(isFinished) {
        if (isFinished) {
            onGameFinished(maxCombo, perfect, great, good, lost)
        }
    }

    val gradientStart = AppTheme.colors.accentGradientStart
    val gradientEnd = AppTheme.colors.accentGradientEnd

    val context = LocalContext.current 

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
            .clickable {
                if (!started) {
                    started = true
                }
            }
    ) {
        val maxHeight = this.maxHeight
        val hitLineY = maxHeight * 4 / 5
        val hitWindow = maxHeight / 10

        // レーンの描画
        Line()

        if (!started) {
            Text("タップしてスタート", modifier = Modifier.align(Alignment.Center))
        }

        TileAnimationLayer(tiles = viewModel.tiles)

        val bgmPlayer = remember {
            MediaPlayer.create(context, R.raw.thefatrat_unity).apply {
                setVolume(0.5f, 0.5f)
                isLooping = false

                setOnCompletionListener {
                    release()
                }
            }
        }

        LaunchedEffect(started) {
            if (started) {

                val score = loadScore(context)
                viewModel.setScore(score)

                viewModel.startGame(maxHeight)
               
                delay(2250)

                bgmPlayer.start()
            }
        }

        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
                .background(Color.White.copy(alpha = 0.6f), shape = CircleShape)
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
                // context引数を追加
                ActionButtons(hitLineY = hitLineY, context) { lanePressed ->
                    viewModel.tiles.forEachIndexed { index, tile ->
                        // 叩かれたやつ(isHit) や 非表示(isActive=false) は判定しない
                        if (!tile.isActive || tile.isHit) return@forEachIndexed

                        val checkHitResult = checkHit(
                            tileY = tile.y,
                            tileLane = tile.lane,
                            pressedLane = lanePressed,
                            hitLineY = hitLineY,
                            hitWindow = hitWindow
                        )

                        if (checkHitResult > 0) {
                            Log.d("checkHit", "checkHit=$checkHitResult")
                            viewModel.recordHit(index, checkHitResult)
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
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(4) { index ->
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )

            if (index < 3) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(dividerWidth)
                        .background(dividerColor)
                )
            }
        }
    }
}

@Composable
fun ActionButtons(hitLineY: Dp, context: Context, onPress: (Int) -> Unit) {

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

fun checkHit(tileY: Dp, tileLane: Int, pressedLane: Int, hitLineY: Dp, hitWindow: Dp): Int {
    val isLaneMatch = tileLane == pressedLane
    val absVal = abs((tileY - hitLineY).value)
    val isYInRange = absVal <= hitWindow.value

    if (isLaneMatch && (absVal < (hitWindow.value / 4))) {
        return 3 // Perfect
    } else if (isLaneMatch && (absVal < (hitWindow.value / 2))) {
        return 2 // Great
    } else if (isLaneMatch && isYInRange) {
        return 1 // Good
    } else {
        return 0 // Miss or No Hit
    }
}