package com.example.features.main.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Song(
    val mode: Int,
    val filename: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val PRACTICE = 1
        const val CUSTOM = 2
    }
}

data class File(
    val filename: String
)

fun Song.toUploadFile(): File {
    return File(
        filename = this.filename
    )
}

fun Song.toFile(): String {
    return filename + ".png"
}