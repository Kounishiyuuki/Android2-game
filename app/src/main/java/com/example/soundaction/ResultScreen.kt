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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundaction.ui.theme.AppTheme

@Composable
fun  ResultScreen(onNavigateToDetails: () -> Unit) {
    val gradientStart = AppTheme.colors.gradientStart
    val gradientEnd = AppTheme.colors.gradientEnd

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
        Button(
            onClick = onNavigateToDetails,
            modifier = Modifier
                .align(Alignment.BottomEnd).padding(40.dp)
                .size(width = 100.dp, height = 50.dp)
        ) {
            Icon(Icons.Default.ChevronRight, contentDescription = "戻る")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Text(
                "Result",
                fontSize = 60.sp,
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Combo",
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "0",
                    fontSize = 50.sp
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Perfect",
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "0",
                    fontSize = 50.sp
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Lost",
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "0",
                    fontSize = 50.sp
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
        }
    }
}