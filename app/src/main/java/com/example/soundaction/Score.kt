package com.example.soundaction

data class Note(val order: Int, val lane: Int)

fun loadScore(): List<Note> = listOf(
    Note(1, 0),
    Note(2, 1),
    Note(3, 2),
    Note(4, 3),
)

