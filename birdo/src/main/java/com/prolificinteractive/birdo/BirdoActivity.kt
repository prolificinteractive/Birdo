package com.prolificinteractive.birdo

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import com.prolificinteractive.birdo.modules.CustomSwitchAction
import com.prolificinteractive.birdo.modules.CustomSwitchAction.Listener
import com.prolificinteractive.birdo.utils.IS_BIRDO_ACTIVE
import com.prolificinteractive.birdo.utils.MockMode
import com.prolificinteractive.birdo.utils.PREFS_FILE_NAME
import com.squareup.picasso.Picasso
import io.palaima.debugdrawer.actions.ActionsModule
import io.palaima.debugdrawer.actions.ButtonAction
import io.palaima.debugdrawer.actions.SpinnerAction
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
import timber.log.Timber

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

    var modules: List<DebugModule> =
        getExtraModules().orEmpty()
            .plus(arrayOf(
                utilsModules(),
                getMockModule(MockMode(this)),
                LocationModule(),
                TimberModule(),
                DeviceModule(),
                LogsModule(),
                BuildModule(),
                NetworkModule(),
                SettingsModule()
            ))


    if (getOkHttp() != null) {
      modules = modules.plus(OkHttp3Module(getOkHttp()!!))
    }
    if (getPicasso() != null) {
      modules = modules.plus(PicassoModule(getPicasso()!!))
    }


    birdo_view.modules(*modules.toTypedArray())
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

  protected open fun getMockModeOptions(): LinkedHashMap<String, Long> {
    return linkedMapOf(
        Pair("0 seconds", 0L),
        Pair("1 seconds", 1L),
        Pair("4 seconds", 4L),
        Pair("10 seconds", 10L),
        Pair("16 seconds", 16L),
        Pair("30 seconds", 30L)
    )
  }

  /**
   * Mock mode related debug drawer module.
   */
  private fun getMockModule(mockMode: MockMode): ActionsModule {
    val listener = object : Listener {
      override fun onCheckedChanged(value: Boolean) {
        Timber.e("Yolo: $value")
        mockMode.switch(value)
      }
    }
    Timber.e("Mock: ${mockMode.isMockMode()}")
    val mockModeSwitcher = CustomSwitchAction("Mock Mode Switcher", listener, mockMode.isMockMode())

    val options = getMockModeOptions()

    val names = options.keys.toMutableList()
    val delays = options.values.toMutableList()

    var position = delays.indexOf(mockMode.getNetworkDelay())
    if (position < 0 || position >= delays.size) {
      position = 0
    }
    val mockDelayButton = SpinnerAction<Long>(
        names,
        delays,
        SpinnerAction.OnItemSelectedListener { mockMode.setDelay(it) },
        position
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
