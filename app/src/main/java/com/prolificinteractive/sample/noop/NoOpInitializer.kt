package com.prolificinteractive.sample.noop

import android.content.Context
import com.prolificinteractive.birdo.Initializer

class NoOpInitializer : Initializer {
  override fun start(context: Context) {
    // No Op.
  }
}