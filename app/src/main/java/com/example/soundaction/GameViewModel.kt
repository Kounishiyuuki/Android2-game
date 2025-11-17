package com.example.soundaction

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameViewModel : ViewModel() {
    private val _tiles = mutableStateListOf<TileState>()
    val tiles: List<TileState> = _tiles
    val stepTimes = 2500

    val noteTime = stepTimes / 5
    private val scoreData = loadScore()
    private var startTime = 0L

    fun startGame(maxHeight: Dp) {
        startTime = System.currentTimeMillis()
        viewModelScope.launch {
            for (note in scoreData) {
                val delayTime = note.order * noteTime - (System.currentTimeMillis() - startTime)
                if (delayTime > 0) delay(delayTime)

                val tileId = System.currentTimeMillis()
                _tiles.add(TileState(tileId, note.lane, 0.dp, true))

                launch {
                    val noteInterval = stepTimes
                    val steps = (noteInterval.toFloat() / 44).toInt().coerceAtLeast(10)
                    val delayPerStep = stepTimes / steps
                    val stepSize = maxHeight / steps

                    repeat(steps) {
                        delay(delayPerStep.toLong())
                        withContext(Dispatchers.Main) {
                            val tileIndex = _tiles.indexOfFirst { it.id == tileId }
                            if (tileIndex != -1) {
                                val currentY = _tiles[tileIndex].y
                                updateTileY(tileIndex, currentY + stepSize)
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        val tileIndex = _tiles.indexOfFirst { it.id == tileId }
                        if (tileIndex != -1) {
                            removeTile(tileIndex)
                        }
                    }
                }

            }
        }
    }

    fun updateTileY(index: Int, newY: Dp) {
        _tiles[index] = _tiles[index].copy(y = newY)
    }

    fun removeTile(index: Int) {
        _tiles.removeAt(index)
    }

    fun deactivateTile(index: Int) {
        if (index in _tiles.indices) {
            _tiles[index] = _tiles[index].copy(isActive = false)
        }
    }
}

data class TileState(
    val id: Long,
    val lane: Int,
    val y: Dp,
    val isActive: Boolean
)