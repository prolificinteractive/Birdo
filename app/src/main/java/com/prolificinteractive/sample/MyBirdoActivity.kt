package com.prolificinteractive.sample

import android.content.Intent
import android.net.Uri
import com.prolificinteractive.birdo.BirdoActivity
import com.squareup.picasso.Picasso
import io.palaima.debugdrawer.actions.Action
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.ButtonAction
import io.palaima.debugdrawer.base.DebugModule
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit.SECONDS

class MyBirdoActivity : BirdoActivity() {

  override fun getOkHttp(): OkHttpClient? {
    // Install an HTTP cache in the application cache directory.
    val cacheDir = File(cacheDir, "http")
    val cache = Cache(cacheDir, DISK_CACHE_SIZE.toLong())

    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT.toLong(), SECONDS)
        .readTimeout(TIMEOUT.toLong(), SECONDS)
        .writeTimeout(TIMEOUT.toLong(), SECONDS)
        .cache(cache)
        .build()
  }

  override fun getPicasso(): Picasso? {
    return null
  }

  override fun getExtraModules(): List<DebugModule>? {
    return arrayListOf(ActionsModule(getMyCustomModule()))
  }

  private fun getMyCustomModule(): Action {
    return ButtonAction("My Web Site", ButtonAction.Listener {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MY_WEB_SITE)))
    })
  }
}