package com.example.codrum.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    val username: String,
    val filename: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
