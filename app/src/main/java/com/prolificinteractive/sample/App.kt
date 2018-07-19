package com.prolificinteractive.sample

import com.prolificinteractive.birdo.BirdoApp
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.ShakeListener

class App : BirdoApp() {
  override fun onCreate() {
    super.onCreate()

    ShakeListener(this, BirdoInitializer(this, MyBirdoActivity::class.java))
  }
}