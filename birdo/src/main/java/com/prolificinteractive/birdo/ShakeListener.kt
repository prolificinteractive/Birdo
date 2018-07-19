package com.prolificinteractive.birdo

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector

/**
 * One of the listener for starting Birdo. It will listen to shakes from the user and start Birdo
 * upon shake. This listener needs to be initialized in the application class of your app.
 */
class ShakeListener(
    private val context: Context,
    private val initializer: BirdoInitializer
) : ShakeDetector.Listener {
  init {
    val sd = ShakeDetector(this)
    sd.start(context.getSystemService(SENSOR_SERVICE) as SensorManager?)
  }

  override fun hearShake() {
    initializer.start(context)
  }
}