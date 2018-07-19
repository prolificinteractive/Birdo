package com.prolificinteractive.birdo

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jakewharton.processphoenix.ProcessPhoenix
import com.prolificinteractive.birdo.utils.IS_BIRDO_ACTIVE
import com.prolificinteractive.birdo.utils.MockMode
import com.prolificinteractive.birdo.utils.PREFS_FILE_NAME
import com.squareup.picasso.Picasso
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.ButtonAction
import io.palaima.debugdrawer.actions.SpinnerAction
import io.palaima.debugdrawer.actions.SwitchAction
import io.palaima.debugdrawer.base.DebugModule
import io.palaima.debugdrawer.commons.BuildModule
import io.palaima.debugdrawer.commons.DeviceModule
import io.palaima.debugdrawer.commons.NetworkModule
import io.palaima.debugdrawer.commons.SettingsModule
import io.palaima.debugdrawer.location.LocationModule
import io.palaima.debugdrawer.logs.LogsModule
import io.palaima.debugdrawer.okhttp3.OkHttp3Module
import io.palaima.debugdrawer.picasso.PicassoModule
import io.palaima.debugdrawer.timber.TimberModule
import kotlinx.android.synthetic.main.activity_birdo.birdo_view
import kotlinx.android.synthetic.main.activity_birdo.version
import okhttp3.OkHttpClient

/**
 * The Debug Activity containing the {@link DebugView}. You have the option to use this directly or
 * extend it and pass custom extra modules.
 */
open class BirdoActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_birdo)

    try {
      val pInfo = packageManager.getPackageInfo(packageName, 0)

      version.text = getString(R.string.version, pInfo.versionName, pInfo.versionCode.toString())
      version.visibility = View.VISIBLE

    } catch (e: PackageManager.NameNotFoundException) {
      e.printStackTrace()
      version.visibility = View.GONE
    }

    var modules: Array<DebugModule> =
        arrayOf(
            utilsModules(),
            getMockModule(MockMode(this)),
            LocationModule(),
            TimberModule(),
            DeviceModule(),
            LogsModule(),
            BuildModule(),
            NetworkModule(),
            SettingsModule()
        ).plus(getExtraModules().orEmpty())


    if (getOkHttp() != null) {
      modules = modules.plus(OkHttp3Module(getOkHttp()!!))
    }
    if (getPicasso() != null) {
      modules = modules.plus(PicassoModule(getPicasso()!!))
    }


    birdo_view.modules(*modules)
  }

  /**
   * Provide your OkHttp instance used by your api.
   */
  protected open fun getOkHttp(): OkHttpClient? {
    return null
  }

  /**
   * Provide your picasso instance used in your app.
   */
  protected open fun getPicasso(): Picasso? {
    return null
  }

  /**
   * Any extra modules you would like the
   */
  protected open fun getExtraModules(): List<DebugModule>? {
    return null
  }

  /**
   * Mock mode related debug drawer module.
   */
  private fun getMockModule(mockMode: MockMode): ActionsModule {
    // TODO Extract and make it customizable maybe?
    val titles = arrayOf("0 seconds",
        "1 seconds",
        "4 seconds",
        "10 seconds",
        "16 seconds",
        "30 seconds")
    val delays = arrayOf(0L, 1L, 4L, 10L, 16L, 30L)

    val mockModeSwitcher = SwitchAction("Mock Mode Switcher") { value -> mockMode.switch(value) }

    val mockDelayButton = SpinnerAction<Long>(
        arrayListOf(*titles),
        arrayListOf(*delays),
        SpinnerAction.OnItemSelectedListener { mockMode.setDelay(it) },
        delays.indexOf(mockMode.getNetworkDelay())
    )

    return ActionsModule("Mock Mode Settings", mockModeSwitcher, mockDelayButton)
  }

  /**
   * A bunch of utility modules.
   */
  private fun utilsModules(): ActionsModule {
    return ActionsModule(ButtonAction("Restart Application", ButtonAction.Listener { restart() }))
  }

  override fun onDestroy() {
    super.onDestroy()

    val prefs: SharedPreferences = getSharedPreferences(PREFS_FILE_NAME, 0)
    prefs.edit().putBoolean(IS_BIRDO_ACTIVE, false).apply()
  }

  private fun restart() {
    ProcessPhoenix.triggerRebirth(this)
  }
}
