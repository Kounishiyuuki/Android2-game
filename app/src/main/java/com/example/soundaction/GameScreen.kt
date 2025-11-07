package com.example.soundaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun GameScreen() {
    val gradientStart = AppTheme.colors.accentGradientStart
    val gradientEnd = AppTheme.colors.accentGradientEnd

    Box(
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
    ) {
    //レーンの描画
    Line()

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
            ) {

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Black.copy(alpha = 0.2f))
            ) {

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
        //レーン 1
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        //境界線 1
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(dividerWidth)
                .background(dividerColor)
        )

        //レーン 2
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        // 境界線 2
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(dividerWidth)
                .background(dividerColor)
        )

        //レーン 3
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        // 境界線 3
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(dividerWidth)
                .background(dividerColor)
        )

        // レーン 4
        Box(
            Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}