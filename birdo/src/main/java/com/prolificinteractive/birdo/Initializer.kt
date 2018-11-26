package com.prolificinteractive.birdo

import android.content.Context

/**
 * Interface for initializer providing start method, implement this when creating your own.
 */
interface Initializer {
  /**
   * Start method for your debug view.
   */
  fun start(context: Context)
}