package com.example.soundaction

data class Note(val timeMillis: Long, val lane: Int)

fun loadScore(): List<Note> = listOf(
    Note(500, 0),
    Note(1000, 2),
    Note(1500, 1),
    Note(2000, 3),
    // 必要に応じて追加
)
