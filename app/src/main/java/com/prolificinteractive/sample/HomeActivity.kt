package com.prolificinteractive.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.KeyUpListener
import com.prolificinteractive.birdo.VolumeDownListener
import com.prolificinteractive.birdo.utils.MockMode
import kotlinx.android.synthetic.main.activity_home.delay
import kotlinx.android.synthetic.main.activity_home.mockMode
import kotlinx.android.synthetic.main.activity_home.refresh
import kotlinx.android.synthetic.main.activity_home.start

class HomeActivity : AppCompatActivity() {
  private lateinit var debugKeyUpListener: KeyUpListener
  private lateinit var mockModeFetcher: MockMode

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    debugKeyUpListener = VolumeDownListener(BirdoInitializer(this, MyBirdoActivity::class.java))
    mockModeFetcher = MockMode(this)

    start.setOnClickListener { BirdoInitializer(this, MyBirdoActivity::class.java).start(this) }

    refresh.setOnClickListener {
      delay.text = mockModeFetcher.getNetworkDelay().toString()
      mockMode.text = mockModeFetcher.isMockMode().toString()
    }
  }

  /**
   * This is used to open the debug activity in debug builds: [KeyUpListener]
   */
  override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
    debugKeyUpListener.onKeyUp(this, keyCode, event)
    return super.onKeyUp(keyCode, event)
  }
}
