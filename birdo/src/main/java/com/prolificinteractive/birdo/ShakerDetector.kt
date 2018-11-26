package com.prolificinteractive.birdo

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.squareup.seismic.ShakeDetector

/**
 * One of the detector for starting Birdo.
 *
 * It will listen to shakes from the user and start Birdo upon shake. This listener needs to be
 * initialized in the application class of your app.
 */
open class ShakerDetector(
    private val context: Context,
    initializer: Initializer
) : Detector(initializer), ShakeDetector.Listener {

  /**
   * Start the detector.
   */
  open fun detect() {
    ShakeDetector(this).start(context.getSystemService(SENSOR_SERVICE) as SensorManager?)
  }

  override fun hearShake() {
    initializer.start(context)
  }
}