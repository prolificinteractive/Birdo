package com.prolificinteractive.sample

import android.content.Intent
import android.net.Uri
import com.prolificinteractive.birdo.BirdoActivity
import com.squareup.picasso.Picasso
import io.palaima.debugdrawer.actions.Action
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.ButtonAction
import io.palaima.debugdrawer.base.DebugModule
import okhttp3.OkHttpClient

class MyBirdoActivity : BirdoActivity() {

  override fun getOkHttp(): OkHttpClient? {
    // Pass your client here.
    return (applicationContext as App).client.client
  }

  override fun getPicasso(): Picasso? {
    // Pass your picasso instance here.
    return Picasso.with(this)
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