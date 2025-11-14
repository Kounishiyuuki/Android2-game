package com.example.soundaction

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val _tiles = mutableStateListOf<TileState>()
    val tiles: List<TileState> = _tiles

    private val scoreData = loadScore()
    private var startTime = 0L

    fun startGame() {
        startTime = System.currentTimeMillis()
        viewModelScope.launch {
            for (note in scoreData) {
                val delayTime = note.timeMillis - (System.currentTimeMillis() - startTime)
                if (delayTime > 0) delay(delayTime)
                _tiles.add(TileState(note.lane, 0.dp, true))
            }
        }
    }

    fun updateTileY(index: Int, newY: Dp) {
        _tiles[index] = _tiles[index].copy(y = newY)
    }

    fun removeTile(index: Int) {
        _tiles.removeAt(index)
    }
}

