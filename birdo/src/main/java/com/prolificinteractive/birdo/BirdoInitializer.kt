package com.prolificinteractive.birdo

import android.content.Context
import android.content.Intent
import com.prolificinteractive.birdo.utils.IS_BIRDO_ACTIVE
import com.prolificinteractive.birdo.utils.PREFS_FILE_NAME

/**
 * Birdo's starter. Provide your own version or use the default one. You can start Birdo manually
 * or use one of the provided detectors.
 */
class BirdoInitializer(
    context: Context,
    private val name: Class<out BirdoActivity> = BirdoActivity::class.java
) : Initializer {

  init {
    val prefs = context.getSharedPreferences(PREFS_FILE_NAME, 0)
    prefs.edit().putBoolean(IS_BIRDO_ACTIVE, false).apply()
  }

  /**
   * Start Birdo by calling this method. Birdo will only start if it's not currently launched.
   */
  override fun start(context: Context) {
    val prefs = context.getSharedPreferences(PREFS_FILE_NAME, 0)
    if (!prefs.getBoolean(IS_BIRDO_ACTIVE, false)) {
      prefs.edit().putBoolean(IS_BIRDO_ACTIVE, true).apply()
      context.startActivity(Intent(context, name))
    }
  }
}