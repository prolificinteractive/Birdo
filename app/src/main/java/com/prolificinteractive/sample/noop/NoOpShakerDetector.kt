package com.prolificinteractive.sample.noop

import android.content.Context
import com.prolificinteractive.birdo.Initializer
import com.prolificinteractive.birdo.ShakerDetector

/**
 * One of the detector for starting Birdo.
 *
 * It will listen to shakes from the user and start Birdo upon shake. This listener needs to be
 * initialized in the application class of your app.
 */
class NoOpShakerDetector(
    context: Context,
    initializer: Initializer
) : ShakerDetector(context, initializer) {

  /**
   * Start the detector.
   *
   */
  override fun detect() {
    // No Op
  }
}