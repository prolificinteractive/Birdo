package com.prolificinteractive.birdo.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Util class for working with mock.
 */
class MockMode(context: Context) {
  private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, 0)

  /**
   * Whether the mock mode is activated.
   */
  fun isMockMode(): Boolean {
    return prefs.getBoolean(IS_MOCK_MODE, false)
  }

  /**
   * The delay set for mock.
   */
  fun getNetworkDelay(): Long {
    return prefs.getLong(NETWORK_DELAY, 0L)
  }

  internal fun setDelay(delay: Long) {
    prefs.edit().putLong(NETWORK_DELAY, delay).apply()
  }

  internal fun switch(value: Boolean) {
    prefs.edit().putBoolean(IS_MOCK_MODE, value).apply()
  }
}