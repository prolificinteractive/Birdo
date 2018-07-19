package com.prolificinteractive.sample

import com.prolificinteractive.birdo.BirdoApp
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.ShakerDetector

class App : BirdoApp() {
  override fun onCreate() {
    super.onCreate()

    ShakerDetector(this, BirdoInitializer(this, MyBirdoActivity::class.java))
  }
}