package com.prolificinteractive.birdo

import android.app.Activity
import android.view.KeyEvent

/**
 * One of the detector for starting Birdo.
 *
 * It will listen to 3 times {@link KeyEvent.KEYCODE_VOLUME_DOWN} from the user and start Birdo.
 * This listener needs to be initialized in your base activity to be accessible from anywhere.
 */
class VolumeDownDetector(private val initializer: BirdoInitializer) : KeyUpDetector {

  private var index: Int = 0

  override fun onKeyUp(activity: Activity, keyCode: Int, event: KeyEvent) {
    if (keyCode == SEQUENCE[index] && index == SEQUENCE.size - 1) {
      index = 0
      initializer.start(activity)
    } else if (keyCode == SEQUENCE[index]) {
      index++
    } else {
      index = 0
    }
  }

  companion object {
    private val SEQUENCE = intArrayOf(
        KeyEvent.KEYCODE_VOLUME_DOWN,
        KeyEvent.KEYCODE_VOLUME_DOWN,
        KeyEvent.KEYCODE_VOLUME_DOWN)
  }
}