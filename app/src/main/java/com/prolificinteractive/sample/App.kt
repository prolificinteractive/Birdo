package com.prolificinteractive.sample

import com.prolificinteractive.birdo.BirdoApp
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.Initializer
import com.prolificinteractive.birdo.ShakerDetector
import com.prolificinteractive.sample.api.ApiClient

class App : BirdoApp() {
  lateinit var client: ApiClient

  override fun onCreate() {
    super.onCreate()

    client = ApiClient(this)

    // --- Debug ---
    val initializer: Initializer = BirdoInitializer(this, MyBirdoActivity::class.java)
    val detector = ShakerDetector(this, initializer)

    detector.detect()

    // --- Release ---
//    val noOpInitializer: Initializer = NoOpInitializer()
//    val noOpShakerDetector: ShakerDetector = NoOpShakerDetector(this, noOpInitializer)
//
//    noOpShakerDetector.detect()
  }
}