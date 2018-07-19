package com.prolificinteractive.birdo

import android.app.Application
import com.jakewharton.processphoenix.ProcessPhoenix
import io.palaima.debugdrawer.timber.data.LumberYard
import timber.log.Timber

open class BirdoApp : Application() {
  override fun onCreate() {
    super.onCreate()

    if (ProcessPhoenix.isPhoenixProcess(this)) {
      return
    }

    val lumberYard = LumberYard.getInstance(this)
    lumberYard.cleanUp()

    Timber.plant(lumberYard.tree())
    Timber.plant(Timber.DebugTree())
  }
}