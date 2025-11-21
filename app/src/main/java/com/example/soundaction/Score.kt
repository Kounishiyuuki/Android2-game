package com.example.soundaction

import android.content.Context

data class Note(val order: Int, val lane: Int)

fun loadScore(context: Context): List<Note> {
    val notes = mutableListOf<Note>()
    val inputStream = context.assets.open("score.txt")
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 2) {
                val order = parts[0].trim().toIntOrNull()
                val lane = parts[1].trim().toIntOrNull()
                if (order != null && lane != null) {
                    notes.add(Note(order, lane))
                }
            }
        }
    }
    return notes
}
