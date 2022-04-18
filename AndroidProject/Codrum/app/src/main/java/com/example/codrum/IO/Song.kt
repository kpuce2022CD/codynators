package com.example.codrum.IO

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("username") val userName : String,
    @SerializedName("filename") val fileName: String
)
