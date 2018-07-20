package com.prolificinteractive.sample.api

import android.content.Context
import com.prolificinteractive.sample.DISK_CACHE_SIZE
import com.prolificinteractive.sample.TIMEOUT
import com.prolificinteractive.sample.api.responses.Characters
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit.SECONDS

class ApiClient(context: Context) {
  val client: OkHttpClient
  private val api: RickAndMortyApi

  init {
    // Install an HTTP cache in the application cache directory.
    val cacheDir = File(context.cacheDir, "http")
    val cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())

    client = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT.toLong(), SECONDS)
        .readTimeout(TIMEOUT.toLong(), SECONDS)
        .writeTimeout(TIMEOUT.toLong(), SECONDS)
        .cache(cache)
        .build()

    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    api = retrofit.create(RickAndMortyApi::class.java)
  }

  fun getCharacters(): Call<Characters> {
    return api.getCharacters()
  }
}