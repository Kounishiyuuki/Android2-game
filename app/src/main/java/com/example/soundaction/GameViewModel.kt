package com.example.soundaction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import android.util.Log
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
    private val _great = mutableStateOf(0)
    val great: State<Int> = _great

    private val _good = mutableStateOf(0)
    val good: State<Int> = _good
    private val _isGameFinished = mutableStateOf( false )
    val isGameFinished: State<Boolean> = _isGameFinished

    private val _combo = mutableStateOf(0)
    val combo: State<Int> = _combo

    private val _perfect = mutableStateOf(0)
    val perfect: State<Int> = _perfect

    private val _maxCombo = mutableStateOf(0)
    val maxCombo: State<Int> = _maxCombo

    private val _lost = mutableStateOf(0)
    val lost: State<Int> = _lost

    // mainブランチの値を採用
    val stepTimes = 2857
    val noteTime = stepTimes / 5
    private var scoreData:List<Note> = emptyList()
    private var startTime = 0L

    fun setScore(score: List<Note>) {
        scoreData = score
    }
    fun startGame(maxHeight: Dp) {
        startTime = System.currentTimeMillis()
        _tiles.clear()
        
        _isGameFinished.value = false
        _maxCombo.value = 0
        _combo.value = 0
        _perfect.value = 0
        _lost.value = 0
        _great.value = 0
        _good.value = 0

        val lastNoteOrder = scoreData.maxOfOrNull { it.order } ?: 0
        val endTime = (lastNoteOrder * noteTime) + stepTimes + 1000

        
        scoreData.forEach { note ->
            val id = note.order.toLong() 
            _tiles.add(TileState(id, note.lane, 0.dp, false))
        }

        viewModelScope.launch {
            while (true) {
                val currentTime = System.currentTimeMillis() - startTime

                if (currentTime > endTime) {
                    _isGameFinished.value = true
                    break
                }
                _tiles.forEachIndexed { index, tile ->

                    if (tile.isHit) return@forEachIndexed
                    val note = scoreData.getOrNull(index) ?: return@forEachIndexed
                    val noteStart = note.order * noteTime
                    val noteEnd = noteStart + stepTimes

                    if (currentTime in noteStart..noteEnd) {
                        val progress = (currentTime - noteStart).toFloat() / stepTimes
                        val newY = maxHeight * progress
                        updateTile(index, newY, true)
                    } else if (currentTime > noteEnd && tile.isActive) {
                        updateTile(index, 0.dp, false)
                        _lost.value += 1
                        _combo.value = 0
                    }
                }

                delay(16) // 約60fps
            }
        }
    }
    fun recordHit(index: Int, hitType: Int) {

        markAsHit(index)

        if (hitType > 0) {
            _combo.value += 1
            if (_combo.value > _maxCombo.value) {
                _maxCombo.value = _combo.value
            }
            when (hitType) {
                3 -> _perfect.value += 1  // Perfect
                2 -> _great.value += 1    // Great
                1 -> _good.value += 1    // Good
            }
        } else {
             _combo.value = 0
        }
    }

    private fun markAsHit(index: Int) {
        if (index in _tiles.indices) {
            val current = _tiles[index]
            _tiles[index] = current.copy(isActive = false, isHit = true)
        }
    }
    private fun updateTile(index: Int, newY: Dp, active: Boolean) {
        val current = _tiles[index]
        if (current.y != newY || current.isActive != active) {
            _tiles[index] = current.copy(y = newY, isActive = active)
        }
    }

    fun deactivateTile(index: Int) {
        markAsHit(index)
    }

    fun resetGame() {
        _isGameFinished.value = false
        _tiles.clear()
    }
}

data class TileState(
    val id: Long,
    val lane: Int,
    val y: Dp,
    val isActive: Boolean,
    val isHit: Boolean = false
)