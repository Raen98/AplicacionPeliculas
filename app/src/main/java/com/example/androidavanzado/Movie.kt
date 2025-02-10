package com.example.androidavanzado

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("posterURL") val poster: String,
    val imdbId: String
)
