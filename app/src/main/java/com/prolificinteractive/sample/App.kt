package com.prolificinteractive.sample

import com.prolificinteractive.birdo.BirdoApp
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.ShakerDetector
import com.prolificinteractive.sample.api.ApiClient

class App : BirdoApp() {
  lateinit var client: ApiClient

  override fun onCreate() {
    super.onCreate()


    client = ApiClient(this)


    ShakerDetector(this, BirdoInitializer(this, MyBirdoActivity::class.java))
  }
}