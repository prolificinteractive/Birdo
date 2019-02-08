package com.prolificinteractive.sample.api

import com.prolificinteractive.sample.api.responses.Characters
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyApi {
  @GET("character/") fun getCharacters(): Call<Characters>
}