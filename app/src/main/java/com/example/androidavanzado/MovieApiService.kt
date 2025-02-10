package com.example.androidavanzado

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("{genre}")
    suspend fun getMoviesByGenre(@Path("genre") genre: String): Response<List<Movie>>
}

